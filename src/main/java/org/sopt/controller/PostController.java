package org.sopt.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sopt.domain.BoardType;
import org.sopt.dto.Request.CreatePostRequest;
import org.sopt.dto.Request.UpdatePostRequest;
import org.sopt.dto.Response.ApiResponse;
import org.sopt.dto.Response.CreatePostResponse;
import org.sopt.dto.Response.PostPageResponse;
import org.sopt.dto.Response.ReadPostResponse;
import org.sopt.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@Tag(name = "게시글 API", description = "게시글 작성, 조회, 수정, 삭제와 좋아요 기능을 제공합니다.")
public class PostController {
    private static final String COMMON_OK_CODE = "COMMON_200";
    private static final String COMMON_CREATED_CODE = "COMMON_201";
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // POST /posts
    @PostMapping
    @Operation(summary = "게시글 작성", description = "사용자 ID와 게시판 타입을 포함해 새 게시글을 작성합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "게시글 등록 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "게시글 입력값 또는 게시판 타입 오류", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    public ResponseEntity<ApiResponse<CreatePostResponse>> createPost(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "게시글 작성 요청",
                    required = true
            )
            @RequestBody CreatePostRequest request
    ) {
        CreatePostResponse response = postService.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(COMMON_CREATED_CODE, "게시글 등록 완료!", response));
    }

    // GET /posts 📝 과제
    @GetMapping
    @Operation(summary = "전체 게시글 목록 조회", description = "페이지 번호와 크기를 기준으로 전체 게시글 목록을 조회합니다. 응답에는 각 게시글의 좋아요 수가 포함됩니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "전체 게시글 조회 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "페이지 요청값 오류", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    public ApiResponse<PostPageResponse> getAllPosts(
            @Parameter(description = "페이지 번호, 0부터 시작합니다.", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "한 페이지에 조회할 게시글 수입니다.", example = "10")
            @RequestParam(defaultValue = "10") int size
    ) {
        PostPageResponse response = postService.getAllPosts(page, size);

        if (response.getPosts().isEmpty()) {
            return ApiResponse.success(COMMON_OK_CODE, "등록된 게시글이 없습니다.", response);
        }

        return ApiResponse.success(COMMON_OK_CODE, "전체 게시글 조회 완료!", response);
    }

    @GetMapping("/board-types/{boardType}")
    @Operation(summary = "게시판 타입별 게시글 목록 조회", description = "게시판 타입을 기준으로 게시글 목록을 조회합니다. 응답에는 각 게시글의 좋아요 수가 포함됩니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "게시판 타입별 게시글 조회 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "게시판 타입 또는 페이지 요청값 오류", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    public ApiResponse<PostPageResponse> getPostsByBoardType(
            @Parameter(description = "게시판 타입", example = "FREE")
            @PathVariable BoardType boardType,
            @Parameter(description = "페이지 번호, 0부터 시작합니다.", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "한 페이지에 조회할 게시글 수입니다.", example = "10")
            @RequestParam(defaultValue = "10") int size
    ) {
        PostPageResponse response = postService.getPostsByBoardType(boardType, page, size);

        if (response.getPosts().isEmpty()) {
            return ApiResponse.success(COMMON_OK_CODE, "등록된 게시글이 없습니다.", response);
        }

        return ApiResponse.success(COMMON_OK_CODE, "게시글 조회 완료!", response);
    }

    // GET /posts/{id} 📝 과제
    @GetMapping("/{id}")
    @Operation(summary = "단일 게시글 조회", description = "게시글 ID로 단일 게시글을 조회합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "게시글 조회 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    public ApiResponse<ReadPostResponse> getPost(
            @Parameter(description = "게시글 ID", example = "1")
            @PathVariable Long id
    ) {
        return ApiResponse.success(COMMON_OK_CODE, "게시글 조회 완료!", postService.readPost(id));
    }

    // PUT /posts/{id} 📝 과제
    @PutMapping("{id}")
    @Operation(summary = "게시글 수정", description = "게시글 ID로 제목, 내용, 게시판 타입을 수정합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "게시글 수정 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "게시글 입력값 또는 게시판 타입 오류", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    public ApiResponse<Void> updatePost(
            @Parameter(description = "게시글 ID", example = "1")
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "게시글 수정 요청",
                    required = true
            )
            @RequestBody UpdatePostRequest request
    ) {
        String message = postService.updatePost(id, request);
        return ApiResponse.success(COMMON_OK_CODE, message, null);
    }

    // DELETE /posts/{id} 📝 과제
    @DeleteMapping("{id}")
    @Operation(summary = "게시글 삭제", description = "게시글 ID로 게시글을 삭제합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "게시글 삭제 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    public ApiResponse<Void> deletePost(
            @Parameter(description = "게시글 ID", example = "1")
            @PathVariable Long id
    ) {
        String message = postService.deletePost(id);
        return ApiResponse.success(COMMON_OK_CODE, message, null);
    }

    @PostMapping("/{postId}/likes")
    @Operation(summary = "게시글 좋아요 추가", description = "사용자가 특정 게시글에 좋아요를 추가합니다. 같은 사용자는 같은 게시글에 중복 좋아요를 누를 수 없습니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "좋아요 추가 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "게시글 또는 사용자를 찾을 수 없음", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "이미 좋아요를 누른 게시글", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    public ApiResponse<Void> likePost(
            @Parameter(description = "좋아요를 추가할 게시글 ID", example = "1")
            @PathVariable Long postId,
            @Parameter(description = "좋아요를 누르는 사용자 ID", example = "1")
            @RequestParam Long userId
    ) {
        String message = postService.likePost(postId, userId);
        return ApiResponse.success(COMMON_OK_CODE, message, null);
    }

    @DeleteMapping("/{postId}/likes")
    @Operation(summary = "게시글 좋아요 취소", description = "사용자가 특정 게시글에 누른 좋아요를 취소합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "좋아요 취소 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "게시글, 사용자 또는 좋아요를 찾을 수 없음", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    public ApiResponse<Void> cancelLike(
            @Parameter(description = "좋아요를 취소할 게시글 ID", example = "1")
            @PathVariable Long postId,
            @Parameter(description = "좋아요를 취소하는 사용자 ID", example = "1")
            @RequestParam Long userId
    ) {
        String message = postService.cancelLike(postId, userId);
        return ApiResponse.success(COMMON_OK_CODE, message, null);
    }
}
