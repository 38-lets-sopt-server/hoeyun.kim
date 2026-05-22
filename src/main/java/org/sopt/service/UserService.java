// 사용자 생성 요청을 처리하는 서비스
package org.sopt.service;

import org.sopt.domain.User;
import org.sopt.dto.Request.UserCreateRequest;
import org.sopt.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public Long join(UserCreateRequest request) {
        User user = new User(
                request.getNickname(),
                request.getEmail(),
                request.getPassword()
        );

        User savedUser = userRepository.save(user);
        return savedUser.getId();
    }
}
