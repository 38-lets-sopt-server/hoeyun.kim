package org.sopt.dto.Request;

import org.sopt.domain.BoardType;

public class UpdatePostRequestDto {
    private String title;
    private String content;
    private BoardType boardType;

    public UpdatePostRequestDto() {
    }

    public UpdatePostRequestDto(String title, String content, BoardType boardType) {
        this.title = title;
        this.content = content;
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

    public BoardType getBoardType() {
        return boardType;
    }

    public void setBoardType(BoardType boardType) {
        this.boardType = boardType;
    }
}
