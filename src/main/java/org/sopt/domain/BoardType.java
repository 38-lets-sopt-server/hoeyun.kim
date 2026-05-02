package org.sopt.domain;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "게시판 타입", allowableValues = {"FREE", "HOT", "SECRET"})
public enum BoardType {
    FREE,
    HOT,
    SECRET
}
