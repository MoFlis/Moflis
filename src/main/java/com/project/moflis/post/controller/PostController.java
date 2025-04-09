package com.project.moflis.post.controller;

import com.project.moflis.post.dto.AddPostRequest;
import com.project.moflis.post.dto.PostResponse;
import com.project.moflis.post.dto.PostSliceResponse;
import com.project.moflis.post.dto.UpdatePostRequest;
import com.project.moflis.post.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<PostSliceResponse> getPost(
            @RequestParam(required = false) LocalDateTime cursor,
            @RequestParam(defaultValue = "10") int size
    ) {
        PostSliceResponse response = postService.getPostList(cursor, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Integer postId) {
        PostResponse response = postService.getPost(postId);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<PostResponse> addPost(AddPostRequest addPostRequest) {
        PostResponse addResult = postService.addPost(addPostRequest.toCommand());
        return ResponseEntity.ok(addResult);
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<PostResponse> updatePost(
            @PathVariable("postId") int postId, UpdatePostRequest updatePostRequest) {
        PostResponse post = postService.updatePost(postId, updatePostRequest.toCommand());
        return ResponseEntity.ok(post);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<PostResponse> deletePost(@PathVariable Integer postId) {
        PostResponse post = postService.deletePost(postId);
        return ResponseEntity.ok(post);
    }


}
