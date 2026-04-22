package org.sopt.service;

import org.sopt.domain.Post;
import org.sopt.dto.Request.CreatePostRequest;
import org.sopt.dto.Request.UpdatePostRequestDto;
import org.sopt.dto.Response.CreatePostResponse;
import org.sopt.dto.Response.ReadPostResponseDto;
import org.sopt.exception.PostNotFoundException;
import org.sopt.validator.PostValidator;

import java.util.ArrayList;
import java.util.List;

public class PostService {
    private final List<Post> postList = new ArrayList<>(); // 임시 저장소 (나중에 DB로 교체됨)
    private final PostValidator postValidator = new PostValidator();
    private Long nextId = 1L;

    // CREATE ✅ 같이 구현
    // 글쓰기 화면에서 "완료" 버튼을 누르면 이 메서드가 호출돼요
    public void createPost(String title, String content, String author) {
        try {
            // 글쓰기 화면설계서: 제목은 필수, 최대 50자
            postValidator.validateTitleAndContent(title, content);
            savePost(title, content, author);
            System.out.println("✅ 게시글 등록 완료!");
        } catch (IllegalArgumentException e) {
            System.out.println("🚫 입력 오류: " + e.getMessage());
        }
    }

    // READ - 전체 📝 과제
    // 자유게시판 목록 화면에서 호출돼요
    // TODO: postList가 비어있으면 "등록된 게시글이 없습니다." 출력
    // TODO: 비어있지 않으면 모든 게시글의 getInfo()를 순서대로 출력
    public List<ReadPostResponseDto> readAllPosts() {
        return postList.stream()
                .map(ReadPostResponseDto::new)
                .toList();
    }

    // READ - 단건 📝 과제
    // 목록에서 특정 게시글을 탭하면 호출돼요 (게시글 상세 화면)
    // TODO: postList에서 id가 일치하는 게시글을 찾아 getInfo() 출력
    // TODO: 없으면 "해당 게시글을 찾을 수 없습니다." 출력
    public ReadPostResponseDto readPost(Long id) {
        return new ReadPostResponseDto(findPostById(id));
    }

    // UPDATE 📝 과제
    // 게시글 수정 화면에서 "완료"를 누르면 호출돼요
    // TODO: postList에서 id가 일치하는 게시글을 찾아 update() 호출
    // TODO: 없으면 "해당 게시글을 찾을 수 없습니다." 출력
    public String updatePost(UpdatePostRequestDto request) {
        postValidator.validateTitleAndContent(request.getTitle(), request.getContent());

        Post post = findPostById(request.getId());
        post.update(request.getTitle(), request.getContent());
        return "수정 완료!";
    }

    // DELETE 📝 과제
    // 게시글 상세에서 삭제를 누르면 호출돼요
    // TODO: postList에서 id가 일치하는 게시글을 제거
    // TODO: 성공하면 "삭제 완료!", 없으면 "해당 게시글을 찾을 수 없습니다." 출력
    public String deletePost(Long id) {
        Post post = findPostById(id);
        postList.remove(post);
        return "삭제 완료!";
    }

    public CreatePostResponse createPost(CreatePostRequest request) {
        postValidator.validateTitleAndContent(request.title, request.content);
        Post post = savePost(request.title, request.content, request.author);
        return new CreatePostResponse(post.getId(), "✅ 게시글 등록 완료!");
    }

    private Post savePost(String title, String content, String author) {
        String createdAt = java.time.LocalDateTime.now().toString();
        Post post = new Post(nextId++, title, content, author, createdAt);
        postList.add(post);
        return post;
    }

    private Post findPostById(Long id) {
        return postList.stream()
                .filter(post -> post.getId().equals(id))
                .findFirst()
                .orElseThrow(PostNotFoundException::new);
    }
}
