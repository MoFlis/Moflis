package com.project.moflis.post.controller;

import com.project.moflis.post.dto.AddPostRequest;
import com.project.moflis.post.dto.PostResponse;
import com.project.moflis.post.dto.PostSliceResponse;
import com.project.moflis.post.dto.UpdatePostRequest;
import com.project.moflis.post.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/users")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/post")
    public ResponseEntity<PostSliceResponse> getPost(
            @RequestParam(required = false) LocalDateTime cursor,
            @RequestParam(defaultValue = "10") int size
    ) {
        PostSliceResponse response = postService.getPostList(cursor, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Integer postId) {
        PostResponse response = postService.getPost(postId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/post")
    public ResponseEntity<PostResponse> addPost(AddPostRequest addPostRequest) {
        PostResponse addResult = postService.addPost(addPostRequest.toCommand());
        return ResponseEntity.ok(addResult);
    }

    @PatchMapping("/post/{postId}")
    public ResponseEntity<PostResponse> updatePost(
            @PathVariable("postId") int postId, UpdatePostRequest updatePostRequest) {
        PostResponse post = postService.updatePost(postId, updatePostRequest.toCommand());
        return ResponseEntity.ok(post);
    }

    @DeleteMapping("/post/{postId}")
    public ResponseEntity<PostResponse> deletePost(@PathVariable Integer postId) {
        PostResponse post = postService.deletePost(postId);
        return ResponseEntity.ok(post);
    }


}
