package org.sopt.dto.Request;

import org.sopt.domain.BoardType;
import org.sopt.domain.User;

// 게시글 작성 요청 (클라이언트 → 서버)
public class CreatePostRequest {
    private String title;
    private String content;
    private User user;
    private BoardType boardType;

    public CreatePostRequest() {
    }

    public CreatePostRequest(String title, String content, User user, BoardType boardType) {
        this.title = title;
        this.content = content;
        this.user = user;
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

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BoardType getBoardType() {
        return boardType;
    }

    public void setBoardType(BoardType boardType) {
        this.boardType = boardType;
    }
}
