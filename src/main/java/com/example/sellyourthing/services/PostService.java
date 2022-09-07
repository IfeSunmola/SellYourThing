package com.example.sellyourthing.services;

import com.example.sellyourthing.datatransferobjects.NewPostRequest;
import com.example.sellyourthing.datatransferobjects.PostReply;
import com.example.sellyourthing.datatransferobjects.UpdatePostRequest;
import com.example.sellyourthing.emailsender.EmailSenderService;
import com.example.sellyourthing.exceptions.ResourceNotFoundException;
import com.example.sellyourthing.models.Account;
import com.example.sellyourthing.models.AccountDetails;
import com.example.sellyourthing.models.Post;
import com.example.sellyourthing.repositories.PostRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static com.example.sellyourthing.emailsender.MailBody.POST_REPLY_HTML;

@AllArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    private final CategoryService categoryService;
    private final AccountService accountService;
    private final CityService cityService;
    private final EmailSenderService emailSenderService;

    private final Logger logger = LoggerFactory.getLogger(PostService.class);


    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public List<Post> findAllWithSorting(String cityName, String categoryName, String order, String searchText) {
        if (searchText == null) {
            searchText = "";
        }
        searchText = searchText.toUpperCase(Locale.ROOT).strip();

        List<Post> result;

        if (order == null) {
            result = postRepository.findAllWithDate(cityName, categoryName, searchText,
                    Sort.by(Sort.Direction.DESC, "createdAt"));
        }
        else if (order.equals("old")) {
            result = postRepository.findAllWithDate(cityName, categoryName, searchText,
                    Sort.by(Sort.Direction.ASC, "createdAt"));
        }
        else if (order.equals("ascPrice")) {
            result = postRepository.findAllWithPrice(cityName, categoryName, searchText,
                    Sort.by(Sort.Direction.ASC, "price"));
        }
        else if (order.equals("descPrice")) {
            result = postRepository.findAllWithPrice(cityName, categoryName, searchText,
                    Sort.by(Sort.Direction.DESC, "price"));
        }
        else {
            throw new IllegalStateException("Hehe what are you doing");
        }
        return result;
    }

    public void savePost(Post post) {
        postRepository.save(post);
    }

    public Post update(UpdatePostRequest updateInfo, Long postId) {
        Post postToUpdate = postRepository.findById(postId).orElseThrow(()
                -> new ResourceNotFoundException("post id '" + postId + "' was not found"));

        postToUpdate.setTitle(updateInfo.getTitle());
        postToUpdate.setBody(updateInfo.getBody());
        postToUpdate.setPostCategory(categoryService.findByName(updateInfo.getCategoryName()));
        postToUpdate.setUpdatedAt(LocalDateTime.now());

        return postRepository.save(postToUpdate);
    }

    public boolean existsById(Long postId) {
        return postRepository.existsById(postId);
    }

    public void delete(Long postId) {
        postRepository.deleteById(postId);
    }

    public List<Post> findAllAccountPosts(Long accountId) {
        Account account = accountService.findByAccountId(accountId);
        return postRepository.findPostsByPosterAccount(account);
    }

    public List<Post> usersPost(Long accountId, String cityName, String categoryName, String order, String searchText) {

        if (searchText == null) {
            searchText = "";
        }
        searchText = searchText.toUpperCase(Locale.ROOT).strip();

        List<Post> result;
        if (order == null) {
            result = postRepository.findAllWithDateAccount(cityName, categoryName, searchText, accountId,
                    Sort.by(Sort.Direction.DESC, "createdAt"));
        }
        else if (order.equals("old")) {
            result = postRepository.findAllWithDateAccount(cityName, categoryName, searchText, accountId,
                    Sort.by(Sort.Direction.ASC, "createdAt"));
        }
        else if (order.equals("ascPrice")) {
            result = postRepository.findAllWithPriceAccount(cityName, categoryName, searchText, accountId,
                    Sort.by(Sort.Direction.ASC, "price"));
        }
        else if (order.equals("descPrice")) {
            result = postRepository.findAllWithPriceAccount(cityName, categoryName, searchText, accountId,
                    Sort.by(Sort.Direction.DESC, "price"));
        }
        else {
            throw new IllegalStateException("Hehe what are you doing");
        }

//        if (order == null) {
//            result = postRepository.findByPostCityCityNameContainingAndPostCategoryCategoryNameContainingAndBodyContainingIgnoreCase(cityName, categoryName, searchText);
//        }
        return result;
    }

    public Post findByPostId(Long postId) {
        return postRepository.findById(postId).orElseThrow(()
                -> new ResourceNotFoundException("Post id '" + postId + "' was not found"));
    }

    public void sendPostReply(PostReply postReply) {
        Account posterAccount = findByPostId(postReply.getPostId()).getPosterAccount();
        emailSenderService.sendMail(
                "SellThatThing: Someone is interested in your listing",
                "donotreply@sellthatthing.com",
                posterAccount.getEmail(),
                postReply.getReplyEmail(),
                generatePostReplyBody(postReply)
        );
    }

    private String generatePostReplyBody(PostReply postReply) {
        Post post = findByPostId(postReply.getPostId());
        Account posterAccount = post.getPosterAccount();
        return POST_REPLY_HTML.replace("$listingTitle", post.getTitle())
                .replace("$firstName", posterAccount.getFirstName())
                .replace("$messageFrom", postReply.getReplyEmail())
                .replace("$messageBody", postReply.getMessage());
    }

    public void adminDelete(Long postId, HashMap<String, String> message) {
        if (!postRepository.existsById(postId)) {
            message.clear();
            message.put("postDeleteStatus", "false");
            message.put("postDeleteMessage", "Error, post ID: <strong>" + postId + "</strong> was not found.");
            return;
        }
        postRepository.deleteById(postId);
        message.clear();
        message.put("postDeleteStatus", "true");
        message.put("postDeleteMessage", "Post Id: <strong>" + postId + "</strong> has been deleted");
    }

    @Transactional
    public void createNewPost(NewPostRequest newPostRequest, Authentication auth) {
        Account account = accountService.findByEmail(auth.getName());
        newPostRequest.setPosterAccountId(account.getAccountId());

        String imageUrl = saveImage(newPostRequest.getTitle(), newPostRequest.getImage(), auth);
        if (imageUrl.equals("")) {
            logger.error("File could not be saved");
            return;
        }

        postRepository.save(
                new Post(
                        newPostRequest.getTitle(),
                        newPostRequest.getBody(),
                        LocalDateTime.now(),
                        newPostRequest.getPrice(),
                        imageUrl,
                        cityService.findByCityName(newPostRequest.getCityName()),
                        categoryService.findByName(newPostRequest.getCategoryName()),
                        accountService.findByAccountId(newPostRequest.getPosterAccountId())
                )
        );
    }

    private String saveImage(String title, MultipartFile image, Authentication auth) {
        // get file extension
        String contentType = Objects.requireNonNull(image.getContentType()); // in form: image/jpeg or image/png
        String fileType = "." + contentType.substring(6);// gets the string after image/ and adds a . to it

        // generate the new file name
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd:hhmmss"));
        String newFileName = title.replaceAll("\\s", "").substring(0, 10) + "-" + currentTime + fileType;
        // image url to save in the db
        String imageUrl = "images/user-" + ((AccountDetails) auth.getPrincipal()).accountId() + "/" + newFileName;
        String uploadDir = "src/main/resources/static/" + imageUrl;

        Path path = Paths.get(uploadDir);
        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException e) {
            imageUrl = "";
        }
        return imageUrl;
    }

}
