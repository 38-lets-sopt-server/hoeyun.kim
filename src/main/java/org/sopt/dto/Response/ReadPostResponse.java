package org.sopt.dto.Response;

import org.sopt.domain.BoardType;
import org.sopt.domain.Post;
import org.sopt.domain.User;

import java.time.LocalDateTime;

public class ReadPostResponse {
    private Long id;
    private String title;
    private String content;
    private User user;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private BoardType boardType;
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
