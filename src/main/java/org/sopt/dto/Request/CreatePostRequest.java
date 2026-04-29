package org.sopt.dto.Request;

import org.sopt.domain.BoardType;

// 게시글 작성 요청 (클라이언트 → 서버)
public class CreatePostRequest {
    private String title;
    private String content;
    private String author;
    private BoardType boardType;

    public CreatePostRequest() {
    }

    public CreatePostRequest(String title, String content, String author, BoardType boardType) {
        this.title = title;
        this.content = content;
        this.author = author;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public BoardType getBoardType() {
        return boardType;
    }

    public void setBoardType(BoardType boardType) {
        this.boardType = boardType;
    }
}
