package com.project.moflis.post.controller;

import com.project.moflis.post.dto.AddPostRequest;
import com.project.moflis.post.dto.PostResponse;
import com.project.moflis.post.dto.PostSliceResponse;
import com.project.moflis.post.dto.UpdatePostRequest;
import com.project.moflis.post.enums.PostSort;
import com.project.moflis.post.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }


    //어떤 기준으로 검색, 정렬할수 있게 해야함
    //카테고리, 텍스트 검색,
    //Like Search & Full-Text Index & 그 외 방식
    @GetMapping
    public ResponseEntity<PostSliceResponse> getPost(
            @RequestParam(required = false) String cursor,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "DATE") PostSort sortBy
    ) {
        PostSliceResponse response = postService.getPostList(sortBy, cursor, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long postId) {
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
    public ResponseEntity<PostResponse> deletePost(@PathVariable Long postId) {
        PostResponse post = postService.deletePost(postId);
        return ResponseEntity.ok(post);
    }


}
