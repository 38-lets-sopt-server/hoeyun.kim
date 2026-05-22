// 리프레시 토큰 엔티티를 조회하고 삭제하는 JPA 리포지토리
package org.sopt.repository;

import org.sopt.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByMemberId(Long memberId);
}
