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
        category1.setName("Books");
        category1.setDateCreated(LocalDateTime.of(2021, 11, 24, 12, 55));

        Category category2 = new Category();
        category2.setName("Vehicles");
        category2.setDateCreated(LocalDateTime.of(2022, 1, 11, 20, 3));

        Category category3 = new Category();
        category3.setName("Computers");
        category3.setDateCreated(LocalDateTime.of(2019, 5, 21, 19, 22));
        ///////////////////////////////////////////////////////////////////////////////////////////////
        City city1 = new City();
        city1.setName("Winnipeg");
        city1.setDateCreated(LocalDateTime.of(2021, 11, 24, 12, 55));

        City city2 = new City();
        city2.setName("Edmonton");
        city2.setDateCreated(LocalDateTime.of(2022, 1, 11, 20, 3));

        City city3 = new City();
        city3.setName("Calgary");
        city3.setDateCreated(LocalDateTime.of(2019, 5, 21, 19, 22));
        ///////////////////////////////////////////////////////////////////////////////////////////////
        Account account1 = new Account();
        account1.setFirstName("Ife");
        account1.setLastName("Sunmola");
        account1.setEmail("sunmolaife@gmail.com");
        account1.setDateOfBirth(LocalDate.of(2000, 11, 24));
        account1.setRole("USER");
        account1.setPassword("$2a$10$eiJzFSAUeDX2Xo29rPAs0.1ho4i0LyjItvbczuliI8mrSF6sBEWBS");
        account1.setEnabled(true);

        Account account2 = new Account();

        account2.setFirstName("jane");
        account2.setLastName("foster");
        account2.setEmail("janefoster@gmail.com");
        account2.setDateOfBirth(LocalDate.of(1992, 9, 11));
        account2.setRole("USER");
        account2.setPassword("$2a$10$eiJzFSAUeDX2Xo29rPAs0.1ho4i0LyjItvbczuliI8mrSF6sBEWBS");
        account2.setEnabled(true);

        Account account3 = new Account();
        account3.setFirstName("Thor");
        account3.setLastName("Ragnarok");
        account3.setEmail("thorragnarok+admin@gmail.com");
        account3.setDateOfBirth(LocalDate.of(1758, 5, 17));
        account3.setRole("ADMIN");
        account3.setPassword("$2a$10$eiJzFSAUeDX2Xo29rPAs0.1ho4i0LyjItvbczuliI8mrSF6sBEWBS");
        account3.setEnabled(true);

        Post post1 = new Post();
        post1.setTitle("Selling my green book");
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
        post1.setPosterAccount(account1);
        post1.setPrice(new BigDecimal("69.12"));
        post1.setImageUrl("images/frog/green-frog.jpg");
        post1.setPostCity(city1);
        post1.setCreatedAt(LocalDateTime.now().minusWeeks(450));
        ///////////////////////////////////////////////////////////////////////////////////////////////
        Post post2 = new Post();
        post2.setTitle("Vehicle London building for sale");
        post2.setBody("Was built in 1207 by Jesse Lingard. Comes furnished with all the shit you need. This building is made of smaller buildings. " +
                "Was built in 1207 by Jesse Lingard. Comes furnished with all the shit you need. This building is made of smaller buildings. Was " +
                "built in 1207 by Jesse Lingard. Comes furnished with all the shit you need. This building is made of smaller buildings. " +
                "Was built in 1207 by Jesse Lingard. Comes furnished with all the shit you need. This building is made of smaller buildings. " +
                "Was built in 1207 by Jesse Lingard. Comes furnished with all the shit you need. This building is made of smaller buildings. " +
                "Was built in 1207 by Jesse Lingard. Comes furnished with all the shit you need. This building is made of smaller buildings. " +
                "Was built in 1207 by Jesse Lingard. Comes furnished with all the shit you need. This building is made of smaller buildings. " +
                "Was built in 1207 by Jesse Lingard. Comes furnished with all the shit you need. This building is made of smaller buildings");
        post2.setPostCategory(category2);

        post2.setPosterAccount(account2);
        post2.setPrice(new BigDecimal("4200.21"));
        post2.setImageUrl("images/london/london.jpg");
        post2.setPostCity(city2);
        post2.setCreatedAt(LocalDateTime.now().minusMonths(21));
        ///////////////////////////////////////////////////////////////////////////////////////////////
        Post post3 = new Post();
        post3.setTitle("Computer Unused strawberry for sale");
        post3.setBody("Some conspiracy theorists believe strawberries were created by aliens because they are so damn good. I also think cats are " +
                "created by aliens. Some conspiracy theorists believe strawberries were created by aliens because they are so damn good. Some conspiracy " +
                "theorists believe strawberries were created by aliens because they are so damn good. I also think cats are created by aliens. " +
                "Some conspiracy theorists believe strawberries were created by aliens because they are so damn good. Some conspiracy theorists b" +
                "elieve strawberries were created by aliens because they are so damn good. I also think cats are created by aliens. Some conspiracy " +
                "theorists believe strawberries were created by aliens because they are so damn good. Some conspiracy theorists believe strawberries " +
                "were created by aliens because they are so damn good. I also think cats are created by aliens. Some conspiracy theorists believe " +
                "strawberries were created by aliens because they are so damn good.");
        post3.setPostCategory(category3);
        post3.setPosterAccount(account3);
        post3.setPrice(new BigDecimal("30.21"));
        post3.setImageUrl("images/strawberry/strawberries.jpg");
        post3.setPostCity(city3);
        post3.setCreatedAt(LocalDateTime.now().minusMonths(1));
        ///////////////////////////////////////////////////////////////////////////////////////////////
//        Post post4 = new Post();
//        post4.setTitle("Book Big cat for sale");
//        post4.setBody("I got this cat when I was 1/2 years old. I'm much older now and I don't really need it. Anyone intrested? Let me know. " +
//                "I got this cat when I was 1/2 years old. I'm much older now and I don't really need it. Anyone intrested? Let me know. " +
//                "I got this cat when I was 1/2 years old. I'm much older now and I don't really need it. Anyone intrested? Let me know. " +
//                "I got this cat when I was 1/2 years old. I'm much older now and I don't really need it. Anyone intrested? " +
//                "Let me know. I got this cat when I was 1/2 years old. I'm much older now and I don't really need it. Anyone intrested? " +
//                "Let me know. I got this cat when I was 1/2 years old. I'm much older now and I don't really need it. Anyone intrested? " +
//                "Let me know. ");
//        post4.setPostCategory(category1);
//        post4.setPosterAccount(account1);
//        post4.setPrice(new BigDecimal("121.21"));
//        post4.setImageUrl("images/cat.jpg");
//        post4.setPostCity(city1);
//
//        Post post5 = new Post();
//        post5.setTitle("Vehicle for sale");
//        post5.setBody("Barely used, selling so I can raise funds for the avatar to fight the fire lord. Please buy from me and support water tribe. " +
//                "Barely used, selling so I can raise funds for the avatar to fight the fire lord. Please buy from me and support water tribe. " +
//                "Barely used, selling so I can raise funds for the avatar to fight the fire lord. Please buy from me and support water tribe. " +
//                "Barely used, selling so I can raise funds for the avatar to fight the fire lord. Please buy from me and support water tribe. " +
//                "Barely used, selling so I can raise funds for the avatar to fight the fire lord. Please buy from me and support water tribe. " +
//                "Barely used, selling so I can raise funds for the avatar to fight the fire lord. Please buy from me and support water tribe.");
//        post5.setPostCategory(category2);
//        post5.setPosterAccount(account2);
//        post5.setPrice(new BigDecimal("491.21"));
//        post5.setImageUrl("images/vehicle.jpg");
//        post5.setPostCity(city2);


        postService.savePost(post1);
        postService.savePost(post2);
        postService.savePost(post3);
//        postService.savePost(post4);
//        postService.savePost(post5);
    }
}
