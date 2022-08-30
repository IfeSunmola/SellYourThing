package com.example.sellthatthing;

import com.example.sellthatthing.exceptions.EmptyResourceException;
import com.example.sellthatthing.models.Account;
import com.example.sellthatthing.models.Category;
import com.example.sellthatthing.models.City;
import com.example.sellthatthing.models.Post;
import com.example.sellthatthing.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
@AllArgsConstructor
public class SellThatThingApplication implements CommandLineRunner {
    private final PostService postService;

    public static void main(String[] args) {
        SpringApplication.run(SellThatThingApplication.class, args);
    }

    @Override
    public void run(String... args) {
        try {
            postService.findAll();
        }
        catch (EmptyResourceException e) {
            // no posts , add temporary data
            addSeedData();
        }
    }

    private void addSeedData() {
        Category category1 = new Category();
        category1.setCategoryName("Books");
        category1.setDateCreated(LocalDateTime.of(2021, 11, 24, 12, 55));

        Category category2 = new Category();
        category2.setCategoryName("Vehicles");
        category2.setDateCreated(LocalDateTime.of(2022, 1, 11, 20, 3));

        Category category3 = new Category();
        category3.setCategoryName("Computers");
        category3.setDateCreated(LocalDateTime.of(2019, 5, 21, 19, 22));
        ///////////////////////////////////////////////////////////////////////////////////////////////
        City city1 = new City();
        city1.setCityName("Winnipeg");
        city1.setDateCreated(LocalDateTime.of(2021, 11, 24, 12, 55));

        City city2 = new City();
        city2.setCityName("Edmonton");
        city2.setDateCreated(LocalDateTime.of(2022, 1, 11, 20, 3));

        City city3 = new City();
        city3.setCityName("Calgary");
        city3.setDateCreated(LocalDateTime.of(2019, 5, 21, 19, 22));

        Post post1 = new Post();
        post1.setTitle("Selling my green frog");
        post1.setBody("I am selling my green frog because the frog is too green. Green frogs are great. I am selling my green frog because the frog is" +
                " too green. Green frogs are great. I am selling my green frog because the frog is too green. Green frogs are great. I am selling " +
                "my green frog because the frog is too green. Green frogs are great. I am selling my green frog because the frog is too green. " +
                "Green frogs are great. I am selling my green frog because the frog is too green. Green frogs are great. " +
                "I am selling my green frog because the frog is too green. Green frogs are great. I am selling my green frog because the frog is too green." +
                " Green frogs are great. I am selling my green frog because the frog is too green. Green frogs are great. I am selling my green frog " +
                "because the frog is too green. Green frogs are great. I am selling my green frog because the frog is too green. Green frogs are great. " +
                "I am selling my green frog because the frog is too green. Green frogs are great. I am selling my green frog because the frog is too green. " +
                "Green frogs are great.");
        post1.setPostCategory(category1);
        post1.setPosterAccount(new Account(
                "Ife",
                "Sunmola",
                "sunmolaife@gmail.com",
                LocalDate.of(2000, 11, 24),
                "USER",
                "$2a$10$eiJzFSAUeDX2Xo29rPAs0.1ho4i0LyjItvbczuliI8mrSF6sBEWBS",
                true
        ));
        post1.setPrice(new BigDecimal("69.12"));
        post1.setImageUrl("images/frog/green-frog.jpg");
        post1.setPostCity(city1);

        Post post2 = new Post();
        post2.setTitle("London building for sale");
        post2.setBody("Was built in 1207 by Jesse Lingard. Comes furnished with all the shit you need. This building is made of smaller buildings. " +
                "Was built in 1207 by Jesse Lingard. Comes furnished with all the shit you need. This building is made of smaller buildings. Was " +
                "built in 1207 by Jesse Lingard. Comes furnished with all the shit you need. This building is made of smaller buildings. " +
                "Was built in 1207 by Jesse Lingard. Comes furnished with all the shit you need. This building is made of smaller buildings. " +
                "Was built in 1207 by Jesse Lingard. Comes furnished with all the shit you need. This building is made of smaller buildings. " +
                "Was built in 1207 by Jesse Lingard. Comes furnished with all the shit you need. This building is made of smaller buildings. " +
                "Was built in 1207 by Jesse Lingard. Comes furnished with all the shit you need. This building is made of smaller buildings. " +
                "Was built in 1207 by Jesse Lingard. Comes furnished with all the shit you need. This building is made of smaller buildings");
        post2.setPostCategory(category2);
        post2.setPosterAccount(new Account(
                "jane",
                "foster",
                "janefoster@gmail.com",
                LocalDate.of(1992, 9, 11),
                "USER",
                "$2a$10$eiJzFSAUeDX2Xo29rPAs0.1ho4i0LyjItvbczuliI8mrSF6sBEWBS",
                true
        ));
        post2.setPrice(new BigDecimal("4200.21"));
        post2.setImageUrl("images/london/london.jpg");
        post2.setPostCity(city2);

        Post post3 = new Post();
        post3.setTitle("Unused strawberry for sale");
        post3.setBody("Some conspiracy theorists believe strawberries were created by aliens because they are so damn good. I also think cats are " +
                "created by aliens. Some conspiracy theorists believe strawberries were created by aliens because they are so damn good. Some conspiracy " +
                "theorists believe strawberries were created by aliens because they are so damn good. I also think cats are created by aliens. " +
                "Some conspiracy theorists believe strawberries were created by aliens because they are so damn good. Some conspiracy theorists b" +
                "elieve strawberries were created by aliens because they are so damn good. I also think cats are created by aliens. Some conspiracy " +
                "theorists believe strawberries were created by aliens because they are so damn good. Some conspiracy theorists believe strawberries " +
                "were created by aliens because they are so damn good. I also think cats are created by aliens. Some conspiracy theorists believe " +
                "strawberries were created by aliens because they are so damn good.");
        post3.setPostCategory(category3);
        post3.setPosterAccount(new Account(
                "thor",
                "ragnarok",
                "thorragnarok+admin@gmail.com",
                LocalDate.of(1758, 5, 17),
                "ADMIN",
                "$2a$10$eiJzFSAUeDX2Xo29rPAs0.1ho4i0LyjItvbczuliI8mrSF6sBEWBS",
                true
        ));
        post3.setPrice(new BigDecimal("30.21"));
        post3.setImageUrl("images/strawberry/strawberries.jpg");
        post3.setPostCity(city3);

        postService.savePost(post1);
        postService.savePost(post2);
        postService.savePost(post3);
    }
}
