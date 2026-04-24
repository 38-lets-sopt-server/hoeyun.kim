package org.sopt.dto.Response;

// 게시글 작성 응답 (서버 → 클라이언트)
public class CreatePostResponse {
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
