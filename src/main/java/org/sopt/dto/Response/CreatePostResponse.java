package org.sopt.dto.Response;

import io.swagger.v3.oas.annotations.media.Schema;

// 게시글 작성 응답 (서버 → 클라이언트)
@Schema(description = "게시글 작성 응답")
public class CreatePostResponse {
    @Schema(description = "생성된 게시글 ID", example = "1")
    private Long id;

    public CreatePostResponse() {
    }

    public CreatePostResponse(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
