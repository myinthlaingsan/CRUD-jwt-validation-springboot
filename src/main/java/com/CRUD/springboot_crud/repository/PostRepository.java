package com.CRUD.springboot_crud.repository;

import com.CRUD.springboot_crud.pojo.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;
@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    @Query("SELECT p FROM Post p WHERE p.user.userId = :userId")
    List<Post> findByUserId(Integer userId);

    Page<Post> findAll(Pageable pageable);
}
