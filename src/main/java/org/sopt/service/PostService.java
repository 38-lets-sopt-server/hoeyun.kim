package org.sopt.service;

import org.sopt.domain.BoardType;
import org.sopt.domain.Post;
import org.sopt.dto.Request.CreatePostRequest;
import org.sopt.dto.Request.UpdatePostRequestDto;
import org.sopt.dto.Response.CreatePostResponse;
import org.sopt.dto.Response.PostPageResponse;
import org.sopt.dto.Response.ReadPostResponseDto;
import org.sopt.repository.PostRepository;
import org.sopt.validator.PostValidator;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class PostService {
    private final PostRepository postRepository;
    private final PostValidator postValidator = new PostValidator();

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public CreatePostResponse createPost(CreatePostRequest request) {
        postValidator.validateTitleAndContent(request.getTitle(), request.getContent());
        validateBoardType(request.getBoardType());
        Post newPost = new Post(
                postRepository.generateId(),
                request.getTitle(),
                request.getContent(),
                request.getAuthor(),
                LocalDateTime.now().toString(),
                request.getBoardType()
        );
        postRepository.save(newPost);
        return new CreatePostResponse(newPost.getId());
    }

    // READ - 전체 📝 과제
    // 자유게시판 목록 화면에서 호출돼요
    public PostPageResponse getAllPosts(int page, int size) {
        validatePageRequest(page, size);
        return createPostPageResponse(postRepository.getAllPosts(), page, size);
    }

    public PostPageResponse getPostsByBoardType(BoardType boardType, int page, int size) {
        validatePageRequest(page, size);
        List<Post> filteredPosts = postRepository.getAllPosts().stream()
                .filter(post -> post.getBoardType() == boardType)
                .toList();
        return createPostPageResponse(filteredPosts, page, size);
    }

    // READ - 단건 📝 과제
    // 목록에서 특정 게시글을 탭하면 호출돼요 (게시글 상세 화면)
    public ReadPostResponseDto readPost(Long id) {
        return new ReadPostResponseDto(findPostById(id));
    }

    // UPDATE 📝 과제
    // 게시글 수정 화면에서 "완료"를 누르면 호출돼요
    public String updatePost(Long id, UpdatePostRequestDto request) {
        postValidator.validateTitleAndContent(request.getTitle(), request.getContent());
        validateBoardType(request.getBoardType());

        Post post = findPostById(id);
        post.update(request.getTitle(), request.getContent(), request.getBoardType());
        return "수정 완료!";
    }

    // DELETE 📝 과제
    // 게시글 상세에서 삭제를 누르면 호출돼요
    public String deletePost(Long id) {
        Post post = findPostById(id);
        postRepository.remove(post);
        return "삭제 완료!";
    }

    private Post findPostById(Long id) {
        return postRepository.findById(id);
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

        List<ReadPostResponseDto> pagedPosts = posts.subList(startIndex, endIndex).stream()
                .map(ReadPostResponseDto::new)
                .toList();

        return new PostPageResponse(pagedPosts, page, size, totalElements, totalPages);
    }
}
