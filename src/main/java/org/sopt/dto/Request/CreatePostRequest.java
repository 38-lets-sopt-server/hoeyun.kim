package org.sopt.dto.Request;

import org.sopt.domain.BoardType;

// 게시글 작성 요청 (클라이언트 → 서버)
public class CreatePostRequest {
    private String title;
    private String content;
    private Long userId;
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
