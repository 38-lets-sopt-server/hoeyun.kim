// 로그인 성공 시 발급된 액세스 토큰과 리프레시 토큰을 담는 응답 DTO
package org.sopt.dto.Response;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {

    public static TokenResponse of(String accessToken, String refreshToken) {
        return new TokenResponse(accessToken, refreshToken);
    }
}
