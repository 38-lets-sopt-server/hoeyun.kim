package org.sopt.dto.Response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "게시글 목록 페이지 응답")
public class PostPageResponse {
    @Schema(description = "현재 페이지의 게시글 목록")
    private final List<ReadPostResponse> posts;

    @Schema(description = "현재 페이지 번호", example = "0")
    private final int page;

    @Schema(description = "페이지 크기", example = "10")
    private final int size;

    @Schema(description = "전체 게시글 수", example = "25")
    private final long totalElements;

    @Schema(description = "전체 페이지 수", example = "3")
    private final int totalPages;

    public PostPageResponse(
            List<ReadPostResponse> posts,
            int page,
            int size,
            long totalElements,
            int totalPages
    ) {
        this.posts = posts;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public List<ReadPostResponse> getPosts() {
        return posts;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }
}
