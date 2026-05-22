package org.sopt.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
@Schema(description = "사용자 정보")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "사용자 ID", example = "1")
    private Long id;

    @Schema(description = "사용자 닉네임", example = "sopt")
    private String nickname;

    @Schema(description = "사용자 이메일", example = "sopt@example.com")
    private String email;

    protected User() {}

    public User(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
    }

    public Long getId() {
        return this.id;
    }

    public String getNickname() {
        return this.nickname;
    }

    public String getEmail() {
        return this.email;
    }
}
