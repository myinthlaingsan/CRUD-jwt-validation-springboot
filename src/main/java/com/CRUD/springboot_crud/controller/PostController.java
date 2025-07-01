package com.CRUD.springboot_crud.controller;

import com.CRUD.springboot_crud.pojo.Post;
import com.CRUD.springboot_crud.pojo.ResponseMessage;
import com.CRUD.springboot_crud.pojo.dto.PostDto;
import com.CRUD.springboot_crud.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PostService postService;
    @PostMapping("/{userId}")
    public ResponseMessage<Post> add(@PathVariable Integer userId, @RequestBody PostDto postDto){
        Post post = postService.add(userId,postDto);
        return ResponseMessage.success(post);
    }

    @GetMapping
    public ResponseMessage getAllPosts(){
        List<PostDto> posts = postService.getAllPosts();
        return ResponseMessage.success(posts);
    }

    @GetMapping("/{postId}")
    public ResponseMessage getPostById(@PathVariable Integer postId){
        Post post = postService.getPostById(postId);
        return ResponseMessage.success(post);
    }

    @PutMapping("/{postId}")
    public ResponseMessage edit(@PathVariable Integer postId, @RequestBody PostDto postDto){
        Post newPost = postService.updatePost(postId,postDto);
        return ResponseMessage.success(newPost);
    }

    @DeleteMapping("{postId}")
    public ResponseMessage delete(@PathVariable Integer postId){
        postService.delete(postId);
        return ResponseMessage.success();
    }

    @GetMapping("/user/{userId}")
    public ResponseMessage getPostByUserId(@PathVariable Integer userId){
        List<PostDto> posts = postService.getPostByUserId(userId);
        return ResponseMessage.success(posts);
    }

//    test link(http://localhost:8080/post/posts?page=0&size=3&sortBy=title)
    @GetMapping("/posts")
    public ResponseMessage getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "postId") String sortBy) {
        Page<PostDto> paginatedPosts = postService.getPaginatedPosts(page, size, sortBy);
        return ResponseMessage.success(paginatedPosts);
    }
}
