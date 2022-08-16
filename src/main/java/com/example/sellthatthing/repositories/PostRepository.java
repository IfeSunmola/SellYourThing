package com.example.sellthatthing.repositories;

import com.example.sellthatthing.models.Account;
import com.example.sellthatthing.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findPostsByPosterAccount(Account account);
}
