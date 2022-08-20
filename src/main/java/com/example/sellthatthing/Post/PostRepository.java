package com.example.sellthatthing.Post;

import com.example.sellthatthing.Account.Account;
import com.example.sellthatthing.Category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findPostsByPosterAccount(Account account);
    List<Post> findByPostCategory(Category category);
}
