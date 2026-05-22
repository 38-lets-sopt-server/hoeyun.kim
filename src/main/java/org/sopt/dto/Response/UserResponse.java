// 사용자 기본 정보를 반환하는 응답 DTO
package org.sopt.dto.Response;

import org.sopt.domain.User;

public record UserResponse(
        Long id,
        String nickname,
        String email
) {

    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getNickname(),
                user.getEmail()
        );
    }
}
