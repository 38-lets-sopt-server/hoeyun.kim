package org.sopt.controller;

import org.sopt.dto.Request.CreatePostRequest;
import org.sopt.dto.Request.UpdatePostRequestDto;
import org.sopt.dto.Response.CreatePostResponse;
import org.sopt.dto.Response.ReadPostResponseDto;
import org.sopt.dto.Response.commonResponse;
import org.sopt.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // POST /posts
    @PostMapping
    public ResponseEntity<CreatePostResponse> createPost(@RequestBody CreatePostRequest request) {
        CreatePostResponse response = postService.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET /posts 📝 과제
    @GetMapping
    public commonResponse<List<ReadPostResponseDto>> getAllPosts() {
        List<ReadPostResponseDto> posts = postService.getAllPosts();

        if (posts.isEmpty()) {
            return commonResponse.success("등록된 게시글이 없습니다.", posts);
        }

        return commonResponse.success("전체 게시글 조회 완료!", posts);
    }

    // GET /posts/{id} 📝 과제
    @GetMapping("/{id}")
    public commonResponse<ReadPostResponseDto> getPost(@PathVariable Long id) {
        return commonResponse.success("게시글 조회 완료!", postService.readPost(id));
    }

    // PUT /posts/{id} 📝 과제
    @PutMapping("{id}")
    public commonResponse<Void> updatePost(
            @PathVariable Long id,
            @RequestBody UpdatePostRequestDto request
    ) {
        String message = postService.updatePost(id, request);
        return commonResponse.success(message, null);
    }

    // DELETE /posts/{id} 📝 과제
    @DeleteMapping("{id}")
    public commonResponse<Void> deletePost(@PathVariable Long id) {
        String message = postService.deletePost(id);
        return commonResponse.success(message, null);
    }
}
