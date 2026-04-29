package org.sopt.dto.Response;

import org.sopt.domain.BoardType;
import org.sopt.domain.Post;
import org.sopt.domain.User;

// 게시글 조회 응답 (서버 → 클라이언트)
public class ReadPostResponseDto {
    private Long id;
    private String title;
    private String content;
    private User user;
    private String createdAt;
    private BoardType boardType;

    public ReadPostResponseDto() {
    }

    public ReadPostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.user = post.getUser();
        this.createdAt = post.getCreatedAt();
        this.boardType = post.getBoardType();
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

    public String getCreatedAt() {
        return createdAt;
    }

    public BoardType getBoardType() {
        return boardType;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + title + " - " + user + " (" + createdAt + ")\n" + content;
    }
}
