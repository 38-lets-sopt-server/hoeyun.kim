package org.sopt.dto.Response;

import java.util.List;

public class PostPageResponse {
    private final List<ReadPostResponseDto> posts;
    private final int page;
    private final int size;
    private final long totalElements;
    private final int totalPages;

    public PostPageResponse(
            List<ReadPostResponseDto> posts,
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

    public List<ReadPostResponseDto> getPosts() {
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
