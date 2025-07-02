package com.CRUD.springboot_crud.service.Impl;

import com.CRUD.springboot_crud.exception.ResourceNotFoundException;
import com.CRUD.springboot_crud.pojo.Post;
import com.CRUD.springboot_crud.pojo.Role;
import com.CRUD.springboot_crud.pojo.User;
import com.CRUD.springboot_crud.pojo.dto.PostDto;
import com.CRUD.springboot_crud.pojo.dto.UserDto;
import com.CRUD.springboot_crud.repository.PostRepository;
import com.CRUD.springboot_crud.repository.UserRepositroy;
import com.CRUD.springboot_crud.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepositroy userRepositroy;
    private final ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository, UserRepositroy userRepositroy, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.userRepositroy = userRepositroy;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<PostDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();
//        return posts.stream()
//                .map(post->modelMapper.map(post, PostDto.class))
//                .collect(Collectors.toList());

        return posts.stream().map(post->{
            PostDto postDto = modelMapper.map(post,PostDto.class);
            User user = post.getUser();
            UserDto userDto = modelMapper.map(user,UserDto.class);
            Set<Integer> roleId = user.getRoles().stream()
                    .map(Role::getRoleId)
                    .collect(Collectors.toSet());
            userDto.setRoleId(roleId);
            postDto.setUser(userDto);
            return postDto;
        }).collect(Collectors.toList());
    }

    @Override
    public Post add(Integer userId, PostDto postDto) {
        User user = userRepositroy.findById(userId).orElse(null);
        if(user == null) return null;
        Post post = modelMapper.map(postDto, Post.class);
        post.setUser(user);
        return postRepository.save(post);
    }

    @Override
    public Post getPostById(Integer postId) {
        return postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post not found with Id " + postId));
    }

    @Override
    public Post updatePost(Integer postId, PostDto postDto) {
        Post existingpost = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found with Id" + postId));
        modelMapper.map(postDto, existingpost);
        return postRepository.save(existingpost);
    }

    @Override
    public void delete(Integer postId) {
        postRepository.deleteById(postId);
    }

    @Override
    public List<PostDto> getPostByUserId(Integer userId) {
        List<Post> posts = postRepository.findByUserId(userId);
        return  posts.stream()
                .map(post->modelMapper.map(post,PostDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<PostDto> getPaginatedPosts(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Post> posts = postRepository.findAll(pageable);
        return posts.map(post -> modelMapper.map(post, PostDto.class));
    }
}
