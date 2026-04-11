package org.sopt.controller;

import org.sopt.dto.Request.CreatePostRequest;
import org.sopt.dto.Request.UpdatePostRequestDto;
import org.sopt.dto.Response.commonResponse;
import org.sopt.dto.Response.CreatePostResponse;
import org.sopt.dto.Response.ReadPostResponseDto;
import org.sopt.exception.PostNotFoundException;
import org.sopt.service.PostService;

import java.util.List;

public class PostController {
    private final PostService postService = new PostService();

    // POST /posts
    public CreatePostResponse createPost(CreatePostRequest request) {
        try {
            return postService.createPost(request);
        } catch (IllegalArgumentException e) {
            return new CreatePostResponse(null, "🚫 " + e.getMessage());
        }
    }

    // GET /posts 📝 과제
    // TODO: postService.getAllPosts() 호출해서 반환
    public commonResponse<List<ReadPostResponseDto>> getAllPosts() {
        List<ReadPostResponseDto> posts = postService.readAllPosts();

        if (posts.isEmpty()) {
            return commonResponse.success("등록된 게시글이 없습니다.", posts);
        }

        return commonResponse.success("전체 게시글 조회 완료!", posts);
    }

    // GET /posts/{id} 📝 과제
    // TODO: postService.getPost(id) 호출, 예외 발생 시 null 반환
    public commonResponse<ReadPostResponseDto> getPost(Long id) {
        try {
            return commonResponse.success("게시글 조회 완료!", postService.readPost(id));
        } catch (PostNotFoundException | IllegalArgumentException e) {
            return commonResponse.fail(e.getMessage());
        }
    }

    // PUT /posts/{id} 📝 과제
    // TODO: postService.updatePost() 호출, 예외 발생 시 에러 메시지 출력
    public commonResponse<Void> updatePost(UpdatePostRequestDto request) {
        try {
            String message = postService.updatePost(request);
            return commonResponse.success(message, null);
        } catch (PostNotFoundException | IllegalArgumentException e) {
            return commonResponse.fail(e.getMessage());
        }
    }

    // DELETE /posts/{id} 📝 과제
    // TODO: postService.deletePost() 호출, 예외 발생 시 에러 메시지 출력
    public commonResponse<Void> deletePost(Long id) {
        try {
            String message = postService.deletePost(id);
            return commonResponse.success(message, null);
        } catch (PostNotFoundException | IllegalArgumentException e) {
            return commonResponse.fail(e.getMessage());
        }
    }
}
