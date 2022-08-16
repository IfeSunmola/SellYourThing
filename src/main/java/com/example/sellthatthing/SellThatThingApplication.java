package com.example.sellthatthing;

import com.example.sellthatthing.DTOs.NewAccountRequest;
import com.example.sellthatthing.DTOs.NewPostRequest;
import com.example.sellthatthing.models.Account;
import com.example.sellthatthing.models.Category;
import com.example.sellthatthing.models.Post;
import com.example.sellthatthing.services.AccountService;
import com.example.sellthatthing.services.CategoryService;
import com.example.sellthatthing.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
@AllArgsConstructor
public class SellThatThingApplication implements CommandLineRunner {
    private final CategoryService categoryService;
    private final PostService postService;
    private final AccountService accountService;

    public static void main(String[] args) {
        SpringApplication.run(SellThatThingApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Category category1 = new Category();
        category1.setCategoryName("Books");
        category1.setDateCreated(LocalDateTime.of(2021, 11, 24, 12, 55));

        Category category2 = new Category();
        category2.setCategoryName("Vehicles");
        category2.setDateCreated(LocalDateTime.of(2022, 1, 11, 20, 3));

        Category category3 = new Category();
        category3.setCategoryName("Computers");
        category3.setDateCreated(LocalDateTime.of(2019, 5, 21, 19, 22));

        Post post1 = new Post();
        post1.setTitle("Title of Post 1");
        post1.setBody("Body of Post 1");
        post1.setPostCategory(category1);
        post1.setPosterAccount(new Account(
                "Ife",
                "Sunmola",
                "sunmolaife@gmail.com",
                "password"
        ));

        Post post2 = new Post();
        post2.setTitle("Title of Post 2");
        post2.setBody("Body of Post 2");
        post2.setPostCategory(category2);
        post2.setPosterAccount(new Account(
                "jane",
                "foster",
                "janefoster@gmail.com",
                "password"
        ));

        Post post3 = new Post();
        post3.setTitle("Title of Post 3");
        post3.setBody("Body of Post 3");
        post3.setPostCategory(category3);
        post3.setPosterAccount(new Account(
                "thor",
                "ragnarok",
                "thorragnarok@gmail.com",
                "password"
        ));

        postService.savePost(post1);
        postService.savePost(post2);
        postService.savePost(post3);
    }
}
