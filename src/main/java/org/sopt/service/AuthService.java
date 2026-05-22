// 로그인 검증과 토큰 발급을 처리하는 인증 서비스
package org.sopt.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.sopt.domain.RefreshToken;
import org.sopt.domain.User;
import org.sopt.dto.Response.TokenResponse;
import org.sopt.dto.Response.UserResponse;
import org.sopt.repository.RefreshTokenRepository;
import org.sopt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;

    @Value("${security.jwt.refresh-token-expires-in-seconds:1209600}")
    private long refreshTokenExpiresInSeconds;

    public UserResponse loginWithCredentials(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다.");
        }

        return UserResponse.from(user);
    }

    @Transactional
    public TokenResponse login(String email, String password) {
        UserResponse userResponse = loginWithCredentials(email, password);

        String accessToken = jwtService.generateAccessToken(userResponse.id(), userResponse.email());
        String refreshToken = jwtService.generateRefreshToken(userResponse.id());

        // 기존 Refresh Token 삭제 후 새로 저장
        refreshTokenRepository.deleteByMemberId(userResponse.id());
        refreshTokenRepository.save(
                RefreshToken.of(userResponse.id(), refreshToken, refreshTokenExpiresInSeconds)
        );

        return TokenResponse.of(accessToken, refreshToken);
    }

    public UserResponse getMemberById(Long memberId) {
        User user = userRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        return UserResponse.from(user);
    }
}
