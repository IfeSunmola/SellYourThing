package com.example.sellthatthing.services;

import com.example.sellthatthing.datatransferobjects.NewPostRequest;
import com.example.sellthatthing.datatransferobjects.PostReply;
import com.example.sellthatthing.emailsender.EmailSenderService;
import com.example.sellthatthing.models.Post;
import com.example.sellthatthing.datatransferobjects.UpdatePostRequest;
import com.example.sellthatthing.exceptions.EmptyResourceException;
import com.example.sellthatthing.exceptions.ResourceNotFoundException;
import com.example.sellthatthing.models.Account;
import com.example.sellthatthing.models.Category;
import com.example.sellthatthing.repositories.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.sellthatthing.emailsender.MailBody.POST_REPLY_HTML;

@AllArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    private final CategoryService categoryService;
    private final AccountService accountService;
    private final LocationService locationService;
    private final EmailSenderService emailSenderService;

    public List<Post> findAll() {
        List<Post> listOfPosts = postRepository.findAll();
        if (listOfPosts.isEmpty()) {
            throw new EmptyResourceException("No posts found");
        }
        return listOfPosts;
    }

    public void savePost(Post post) {
        int randomYear = (int) Math.floor(Math.random() * (2022 - 2018 + 1) + 2018);
        int randomMonth = (int) Math.floor(Math.random() * (12 - 1 + 1) + 1);
        int randomDay = (int) Math.floor(Math.random() * (27 - 1 + 1) + 1);
        int randomHour = (int) Math.floor(Math.random() * (23 - 1 + 1) + 1);
        int randomMinute = (int) Math.floor(Math.random() * (59 - 1 + 1) + 1);
        post.setCreatedAt(LocalDateTime.of(randomYear, randomMonth, randomDay, randomHour, randomMinute));
        postRepository.save(post);
    }

    @Transactional
    public void createNewPost(NewPostRequest newPostRequest, Authentication auth) {
        Account account = accountService.findByEmail(auth.getName());
        newPostRequest.setPosterAccountId(account.getAccountId());

        postRepository.save(
                new Post(
                        newPostRequest.getTitle(),
                        newPostRequest.getBody(),
                        LocalDateTime.now(),
                        newPostRequest.getPrice(),
                        //newPostRequest.getImageUrl(),
                        locationService.findByLocationId(newPostRequest.getLocationId()),
                        categoryService.findByCategoryId(newPostRequest.getCategoryId()),
                        accountService.findByAccountId(newPostRequest.getPosterAccountId())
                )
        );
    }

    public Post update(UpdatePostRequest updateInfo, Long postId) {
        Post postToUpdate = postRepository.findById(postId).orElseThrow(()
                -> new ResourceNotFoundException("post id '" + postId + "' was not found"));

        postToUpdate.setTitle(updateInfo.getTitle());
        postToUpdate.setBody(updateInfo.getBody());
        postToUpdate.setPostCategory(categoryService.findByCategoryName(updateInfo.getCategoryName()));
        postToUpdate.setUpdatedAt(LocalDateTime.now());

        return postRepository.save(postToUpdate);
    }

    public void delete(Long postId) {
        Post postToDelete = postRepository.findById(postId).orElseThrow(()
                -> new ResourceNotFoundException("Post id '" + postId + "' was not found"));
        postRepository.delete(postToDelete);
    }

    public List<Post> findAllAccountPosts(Long accountId) {
        Account account = accountService.findByAccountId(accountId);
        return postRepository.findPostsByPosterAccount(account);
    }

    public List<Post> findByPostCategory(String categoryName) {
        Category category = categoryService.findByCategoryName(categoryName);
        return postRepository.findByPostCategory(category);
    }

    public Post findByPostId(Long postId) {
        return postRepository.findById(postId).orElseThrow(()
                -> new ResourceNotFoundException("Account id '" + postId + "' was not found"));
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
}
