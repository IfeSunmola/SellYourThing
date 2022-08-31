package com.example.sellthatthing.repositories;

import com.example.sellthatthing.models.Account;
import com.example.sellthatthing.models.Category;
import com.example.sellthatthing.models.Post;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findPostsByPosterAccount(Account account);

    List<Post> findByPostCategory(Category category);

    @Query("""
                SELECT p from Post p
                WHERE p.postCity.cityName LIKE %:cityName%
                AND p.postCategory.categoryName LIKE %:categoryName%
                AND upper(p.body)  LIKE :searchText%
            """)
    List<Post> findAllWithDate(@Param("cityName") String cityName,
                               @Param("categoryName") String categoryName,
                               @Param("searchText") String searchText,
                               Sort sort);

    @Query("""
                SELECT p from Post p
                WHERE p.postCity.cityName LIKE %:cityName%
                AND p.postCategory.categoryName LIKE %:categoryName%
                AND upper(p.body)  LIKE :searchText%
            """)
    List<Post> findAllWithPrice(@Param("cityName") String cityName,
                                @Param("categoryName") String categoryName,
                                @Param("searchText") String searchText,
                                Sort sort);
}
