package org.sopt.service;

import org.sopt.domain.BoardType;
import org.sopt.domain.Like;
import org.sopt.domain.Post;
import org.sopt.domain.User;
import org.sopt.dto.Request.CreatePostRequest;
import org.sopt.dto.Request.UpdatePostRequest;
import org.sopt.dto.Response.CreatePostResponse;
import org.sopt.dto.Response.PostPageResponse;
import org.sopt.dto.Response.ReadPostResponse;
import org.sopt.exception.DuplicateLikeException;
import org.sopt.exception.LikeNotFoundException;
import org.sopt.exception.PostNotFoundException;
import org.sopt.exception.UserNotFoundException;
import org.sopt.repository.LikeRepository;
import org.sopt.repository.PostRepository;
import org.sopt.repository.UserRepository;
import org.sopt.validator.PostValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    private final PostValidator postValidator = new PostValidator();

    public PostService(PostRepository postRepository, UserRepository userRepository, LikeRepository likeRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
    }

    @Transactional
    public CreatePostResponse createPost(CreatePostRequest request) {
        postValidator.validateTitleAndContent(request.getTitle(), request.getContent());
        validateBoardType(request.getBoardType());
        User user = findUserById(request.getUserId());
        Post newPost = new Post(
                request.getTitle(),
                request.getContent(),
                user,
                request.getBoardType()
        );
        postRepository.save(newPost);
        return new CreatePostResponse(newPost.getId());
    }

    @Transactional(readOnly = true)
    public PostPageResponse getAllPosts(int page, int size) {
        validatePageRequest(page, size);
        return createPostPageResponse(postRepository.findAllWithUserAndLikes(), page, size);
    }


    @Transactional(readOnly = true)
    public PostPageResponse getPostsByBoardType(BoardType boardType, int page, int size) {
        validatePageRequest(page, size);
        return createPostPageResponse(postRepository.findAllByBoardTypeWithUserAndLikes(boardType), page, size);
    }


    @Transactional(readOnly = true)
    public ReadPostResponse readPost(Long id) {
        return new ReadPostResponse(findPostByIdWithUserAndLikes(id));
    }


    @Transactional
    public String updatePost(Long id, UpdatePostRequest request) {
        postValidator.validateTitleAndContent(request.getTitle(), request.getContent());
        validateBoardType(request.getBoardType());

        Post post = findPostById(id);
        post.update(request.getTitle(), request.getContent(), request.getBoardType());
        return "수정 완료!";
    }


    public String deletePost(Long id) {
        Post post = findPostById(id);
        postRepository.delete(post);
        return "삭제 완료!";
    }

    @Transactional
    public String likePost(Long postId, Long userId) {
        Post post = findPostById(postId);
        User user = findUserById(userId);

        if (likeRepository.existsByUserIdAndPostId(userId, postId)) {
            throw new DuplicateLikeException();
        }

        likeRepository.save(new Like(user, post));
        return "좋아요 추가 완료!";
    }

    @Transactional
    public String cancelLike(Long postId, Long userId) {
        findPostById(postId);
        findUserById(userId);

        Like like = likeRepository.findByUserIdAndPostId(userId, postId)
                .orElseThrow(LikeNotFoundException::new);

        likeRepository.delete(like);
        return "좋아요 취소 완료!";
    }

    private Post findPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);
    }

    private Post findPostByIdWithUserAndLikes(Long id) {
        return postRepository.findByIdWithUserAndLikes(id)
                .orElseThrow(PostNotFoundException::new);
    }

    private User findUserById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("userId는 필수입니다.");
        }
        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }


    private void validatePageRequest(int page, int size) {
        if (page < 0) {
            throw new IllegalArgumentException("page는 0 이상이어야 합니다.");
        }
        if (size < 1) {
            throw new IllegalArgumentException("size는 1 이상이어야 합니다.");
        }
    }

    private void validateBoardType(BoardType boardType) {
        if (boardType == null) {
            throw new IllegalArgumentException("boardType은 필수입니다.");
        }
    }

    private PostPageResponse createPostPageResponse(List<Post> posts, int page, int size) {
        int totalElements = posts.size();
        int totalPages = totalElements == 0 ? 0 : (int) Math.ceil((double) totalElements / size);
        int startIndex = page * size;
        int endIndex = Math.min(startIndex + size, totalElements);

        if (startIndex >= totalElements) {
            return new PostPageResponse(List.of(), page, size, totalElements, totalPages);
        }

        List<ReadPostResponse> pagedPosts = posts.subList(startIndex, endIndex).stream()
                .map(ReadPostResponse::new)
                .toList();

        return new PostPageResponse(pagedPosts, page, size, totalElements, totalPages);
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }
}
