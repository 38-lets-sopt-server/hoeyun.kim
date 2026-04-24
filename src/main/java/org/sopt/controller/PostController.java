package org.sopt.controller;

import org.sopt.dto.Request.CreatePostRequest;
import org.sopt.dto.Request.UpdatePostRequestDto;
import org.sopt.dto.Response.ApiResponse;
import org.sopt.dto.Response.CreatePostResponse;
import org.sopt.dto.Response.ReadPostResponseDto;
import org.sopt.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    private static final String COMMON_OK_CODE = "COMMON_200";
    private static final String COMMON_CREATED_CODE = "COMMON_201";
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // POST /posts
    @PostMapping
    public ResponseEntity<ApiResponse<CreatePostResponse>> createPost(@RequestBody CreatePostRequest request) {
        CreatePostResponse response = postService.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(COMMON_CREATED_CODE, "게시글 등록 완료!", response));
    }

    // GET /posts 📝 과제
    @GetMapping
    public ApiResponse<List<ReadPostResponseDto>> getAllPosts() {
        List<ReadPostResponseDto> posts = postService.getAllPosts();

        if (posts.isEmpty()) {
            return ApiResponse.success(COMMON_OK_CODE, "등록된 게시글이 없습니다.", posts);
        }

        return ApiResponse.success(COMMON_OK_CODE, "전체 게시글 조회 완료!", posts);
    }

    // GET /posts/{id} 📝 과제
    @GetMapping("/{id}")
    public ApiResponse<ReadPostResponseDto> getPost(@PathVariable Long id) {
        return ApiResponse.success(COMMON_OK_CODE, "게시글 조회 완료!", postService.readPost(id));
    }

    // PUT /posts/{id} 📝 과제
    @PutMapping("{id}")
    public ApiResponse<Void> updatePost(
            @PathVariable Long id,
            @RequestBody UpdatePostRequestDto request
    ) {
        String message = postService.updatePost(id, request);
        return ApiResponse.success(COMMON_OK_CODE, message, null);
    }

    // DELETE /posts/{id} 📝 과제
    @DeleteMapping("{id}")
    public ApiResponse<Void> deletePost(@PathVariable Long id) {
        String message = postService.deletePost(id);
        return ApiResponse.success(COMMON_OK_CODE, message, null);
    }
}
