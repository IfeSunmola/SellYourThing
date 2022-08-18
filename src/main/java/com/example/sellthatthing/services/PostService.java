package com.example.sellthatthing.services;

import com.example.sellthatthing.DTOs.NewPostRequest;
import com.example.sellthatthing.DTOs.UpdatePostRequest;
import com.example.sellthatthing.exceptions.EmptyResourceException;
import com.example.sellthatthing.exceptions.ResourceNotFoundException;
import com.example.sellthatthing.models.Account;
import com.example.sellthatthing.models.Category;
import com.example.sellthatthing.models.Post;
import com.example.sellthatthing.repositories.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    private final CategoryService categoryService;
    private final AccountService accountService;

    public List<Post> findAll() {
        List<Post> listOfPosts = postRepository.findAll();
        if (listOfPosts.isEmpty()){
            throw new EmptyResourceException("No posts found");
        }
        return listOfPosts;
    }

    public void savePost(Post post) {
        postRepository.save(post);
    }

    public Post createNewPost(NewPostRequest newPostRequest) {
        return postRepository.save(
                new Post(
                        newPostRequest.getTitle(),
                        newPostRequest.getBody(),
                        LocalDateTime.now(),
                        newPostRequest.getPrice(),
                        newPostRequest.getImageUrl(),
                        newPostRequest.getLocation(),
                        categoryService.findByCategoryName(newPostRequest.getCategoryName()),
                        accountService.findById(newPostRequest.getPosterAccountId())
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
        Account account = accountService.findById(accountId);
        return postRepository.findPostsByPosterAccount(account);
    }
    public List<Post> findByPostCategory(String categoryName) {
        Category category = categoryService.findByCategoryName(categoryName);
        return postRepository.findByPostCategory(category);
    }

}
