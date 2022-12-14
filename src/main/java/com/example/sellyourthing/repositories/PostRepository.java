package com.example.sellyourthing.repositories;

import com.example.sellyourthing.models.Account;
import com.example.sellyourthing.models.Category;
import com.example.sellyourthing.models.Post;
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
                WHERE p.postCity.name LIKE %:cityName%
                AND p.postCategory.name LIKE %:categoryName%
                AND upper(p.body)  LIKE %:searchText%
            """)
    List<Post> findAllWithDate(@Param("cityName") String cityName,
                               @Param("categoryName") String categoryName,
                               @Param("searchText") String searchText,
                               Sort sort);

    @Query("""
                SELECT p from Post p
                WHERE p.postCity.name LIKE %:cityName%
                AND p.postCategory.name LIKE %:categoryName%
                AND upper(p.body)  LIKE %:searchText%
            """)
    List<Post> findAllWithPrice(@Param("cityName") String cityName,
                                @Param("categoryName") String categoryName,
                                @Param("searchText") String searchText,
                                Sort sort);

    @Query("""
                SELECT p from Post p
                WHERE p.postCity.name LIKE %:cityName%
                AND p.postCategory.name LIKE %:categoryName%
                AND upper(p.body)  LIKE %:searchText%
                AND p.posterAccount.accountId = :accountId
            """)
    List<Post> findAllWithDateAccount(@Param("cityName") String cityName,
                                      @Param("categoryName") String categoryName,
                                      @Param("searchText") String searchText,
                                      @Param("accountId") Long accountId,
                                      Sort sort);

    @Query("""
                SELECT p from Post p
                WHERE p.postCity.name LIKE %:cityName%
                AND p.postCategory.name LIKE %:categoryName%
                AND upper(p.body)  LIKE %:searchText%
                AND p.posterAccount.accountId = :accountId
            """)
    List<Post> findAllWithPriceAccount(@Param("cityName") String cityName,
                                       @Param("categoryName") String categoryName,
                                       @Param("searchText") String searchText,
                                       @Param("accountId") Long accountId,
                                       Sort sort);
}
