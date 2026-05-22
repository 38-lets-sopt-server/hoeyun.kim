package org.sopt.dto.Response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.sopt.domain.BoardType;
import org.sopt.domain.Post;
import org.sopt.domain.User;

import java.time.LocalDateTime;

@Schema(description = "게시글 조회 응답")
public class ReadPostResponse {
    @Schema(description = "게시글 ID", example = "1")
    private Long id;

    @Schema(description = "게시글 제목", example = "첫 번째 게시글")
    private String title;

    @Schema(description = "게시글 내용", example = "게시글 내용입니다.")
    private String content;

    @Schema(description = "작성자 정보")
    private User user;

    @Schema(description = "게시글 생성 시간", example = "2026-05-01T18:00:00")
    private LocalDateTime createdAt;

    @Schema(description = "게시글 수정 시간", example = "2026-05-01T18:30:00")
    private LocalDateTime updatedAt;

    @Schema(description = "게시판 타입", example = "FREE")
    private BoardType boardType;

    @Schema(description = "게시글 좋아요 수", example = "3")
    private int likeCount;

    public ReadPostResponse() {
    }

    public ReadPostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.user = post.getUser();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
        this.boardType = post.getBoardType();
        this.likeCount = post.getLikeCount();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public User getUser() {
        return this.user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public BoardType getBoardType() {
        return boardType;
    }

    public int getLikeCount() {
        return likeCount;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + title + " - " + user + " (" + createdAt + ")\n" + content;
    }
}
