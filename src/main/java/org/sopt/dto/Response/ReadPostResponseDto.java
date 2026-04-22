package org.sopt.dto.Response;

import org.sopt.domain.Post;

// 게시글 조회 응답 (서버 → 클라이언트)
public class ReadPostResponseDto {
    Long id;
    String title;
    String content;
    String author;
    String createdAt;

    public ReadPostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.author = post.getAuthor();
        this.createdAt = post.getCreatedAt();
    }

    @Override
    public String toString() {
        return "[" + id + "] " + title + " - " + author + " (" + createdAt + ")\n" + content;
    }
}