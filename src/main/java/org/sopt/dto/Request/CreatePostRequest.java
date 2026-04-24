package org.sopt.dto.Request;

// 게시글 작성 요청 (클라이언트 → 서버)
public class CreatePostRequest {
    private String title;
    private String content;
    private String author;

    public CreatePostRequest() {
    }

    public CreatePostRequest(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
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
}
