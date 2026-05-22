package org.sopt.dto.Request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "사용자 가입 요청")
public class UserCreateRequest {
    @Schema(description = "사용자 닉네임", example = "sopt")
    private String nickname;

    @Schema(description = "사용자 이메일", example = "sopt@example.com")
    private String email;

    @Schema(description = "사용자 비밀번호", example = "password123")
    private String password;

    public UserCreateRequest() {
    }

    public UserCreateRequest(String nickname, String email, String password) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
