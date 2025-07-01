package com.CRUD.springboot_crud.service;

import com.CRUD.springboot_crud.pojo.Post;
import com.CRUD.springboot_crud.pojo.dto.PostDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {
    List<PostDto> getAllPosts();
    Post add(Integer userId, PostDto postDto);
    Post getPostById(Integer postId);
    Post updatePost(Integer postId, PostDto postDto);
    void delete(Integer postId);
    List<PostDto> getPostByUserId(Integer userId);
    Page<PostDto> getPaginatedPosts(int page, int size, String sortBy);
}
