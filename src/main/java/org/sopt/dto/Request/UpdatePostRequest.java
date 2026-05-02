package org.sopt.dto.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import org.sopt.domain.BoardType;

@Schema(description = "게시글 수정 요청")
public class UpdatePostRequest {
    @Schema(description = "수정할 게시글 제목", example = "수정된 제목")
    private String title;

    @Schema(description = "수정할 게시글 내용", example = "수정된 내용입니다.")
    private String content;

    @Schema(description = "수정할 게시판 타입", example = "HOT")
    private BoardType boardType;

    public UpdatePostRequest() {
    }

    public UpdatePostRequest(String title, String content, BoardType boardType) {
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
