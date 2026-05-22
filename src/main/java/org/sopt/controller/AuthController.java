// 로그인과 내 정보 조회 API를 제공하는 인증 컨트롤러
package org.sopt.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.sopt.dto.Response.ApiResponse;
import org.sopt.dto.Response.TokenResponse;
import org.sopt.dto.Response.UserResponse;
import org.sopt.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "로그인 (Access Token + Refresh Token 발급)")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponse>> login(
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ) {
        TokenResponse tokens = authService.login(email, password);

        return ResponseEntity.ok(ApiResponse.success("COMMON_200", "로그인 완료!", tokens));
    }

    @Operation(summary = "토큰 재발급")
    @PostMapping("/reissue")
    public ResponseEntity<ApiResponse<TokenResponse>> reissue(
            @RequestParam("refreshToken") String refreshToken
    ) {
        TokenResponse tokens = authService.reissue(refreshToken);

        return ResponseEntity.ok(ApiResponse.success("COMMON_200", "토큰 재발급 완료!", tokens));
    }

    @Operation(summary = "내 정보 조회 (Access Token 검증)")
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> me(Authentication authentication) {

        if (authentication == null || authentication.getPrincipal() == null) {
            throw new IllegalArgumentException("인증되지 않았습니다.");
        }

        Long userId = Long.parseLong(authentication.getName());
        UserResponse userResponse = authService.getMemberById(userId);

        return ResponseEntity.ok(ApiResponse.success("COMMON_200", "내 정보 조회 완료!", userResponse));
    }
}
