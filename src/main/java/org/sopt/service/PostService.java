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
import org.sopt.exception.InvalidBoardTypeException;
import org.sopt.exception.LikeNotFoundException;
import org.sopt.exception.PostNotFoundException;
import org.sopt.exception.UserNotFoundException;
import org.sopt.repository.LikeRepository;
import org.sopt.repository.PostRepository;
import org.sopt.repository.UserRepository;
import org.sopt.validator.PostValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final PostValidator postValidator;

    public PostService(
            PostRepository postRepository,
            UserRepository userRepository,
            LikeRepository likeRepository,
            PostValidator postValidator
    ) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
        this.postValidator = postValidator;
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
        return createPostPageResponse(postRepository.findAll(PageRequest.of(page, size)));
    }


    @Transactional(readOnly = true)
    public PostPageResponse getPostsByBoardType(BoardType boardType, int page, int size) {
        validatePageRequest(page, size);
        validateBoardType(boardType);
        return createPostPageResponse(postRepository.findByBoardType(boardType, PageRequest.of(page, size)));
    }


    @Transactional(readOnly = true)
    public ReadPostResponse readPost(Long id) {
        Post post = findPostByIdWithUser(id);
        return new ReadPostResponse(post, likeRepository.countByPostId(post.getId()));
    }


    @Transactional
    public String updatePost(Long id, UpdatePostRequest request) {
        postValidator.validateTitleAndContent(request.getTitle(), request.getContent());
        validateBoardType(request.getBoardType());

        Post post = findPostById(id);
        post.update(request.getTitle(), request.getContent(), request.getBoardType());
        return "수정 완료!";
    }


    @Transactional
    public String deletePost(Long id) {
        Post post = findPostById(id);
        likeRepository.deleteByPostId(id);
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

    private Post findPostByIdWithUser(Long id) {
        return postRepository.findByIdWithUser(id)
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
            throw new InvalidBoardTypeException();
        }
    }

    private PostPageResponse createPostPageResponse(Page<Post> postPage) {
        List<Post> posts = postPage.getContent();
        Map<Long, Long> likeCountByPostId = getLikeCountByPostId(posts);
        List<ReadPostResponse> postResponses = posts.stream()
                .map(post -> new ReadPostResponse(post, likeCountByPostId.getOrDefault(post.getId(), 0L)))
                .toList();

        return new PostPageResponse(
                postResponses,
                postPage.getNumber(),
                postPage.getSize(),
                postPage.getTotalElements(),
                postPage.getTotalPages()
        );
    }

    private Map<Long, Long> getLikeCountByPostId(List<Post> posts) {
        if (posts.isEmpty()) {
            return Map.of();
        }

        List<Long> postIds = posts.stream()
                .map(Post::getId)
                .toList();

        return likeRepository.countLikesByPostIds(postIds).stream()
                .collect(Collectors.toMap(
                        LikeRepository.PostLikeCount::getPostId,
                        LikeRepository.PostLikeCount::getLikeCount
                ));
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }
}
