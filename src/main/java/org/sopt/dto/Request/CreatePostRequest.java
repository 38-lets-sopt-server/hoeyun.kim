package org.sopt.dto.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import org.sopt.domain.BoardType;

// 게시글 작성 요청 (클라이언트 → 서버)
@Schema(description = "게시글 작성 요청")
public class CreatePostRequest {
    @Schema(description = "게시글 제목", example = "첫 번째 게시글")
    private String title;

    @Schema(description = "게시글 내용", example = "게시글 내용입니다.")
    private String content;

    @Schema(description = "작성자 사용자 ID", example = "1")
    private Long userId;

    @Schema(description = "게시판 타입", example = "FREE")
    private BoardType boardType;

    public CreatePostRequest() {
    }

    public CreatePostRequest(String title, String content, Long userId, BoardType boardType) {
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.boardType = boardType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BoardType getBoardType() {
        return boardType;
    }

    public void setBoardType(BoardType boardType) {
        this.boardType = boardType;
    }
}
