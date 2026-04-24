package org.sopt.dto.Response;

import org.sopt.domain.Post;

// 게시글 조회 응답 (서버 → 클라이언트)
public class ReadPostResponseDto {
    private Long id;
    private String title;
    private String content;
    private String author;
    private String createdAt;

    public ReadPostResponseDto() {
    }

    public ReadPostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.author = post.getAuthor();
        this.createdAt = post.getCreatedAt();
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

    public String getAuthor() {
        return author;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + title + " - " + author + " (" + createdAt + ")\n" + content;
    }
}
