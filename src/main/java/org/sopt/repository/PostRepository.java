package org.sopt.repository;

import org.sopt.domain.Post;
import org.sopt.exception.PostNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PostRepository {
    private static final List<Post> postList = new ArrayList<>();
    private Long nextId = 1L;

    public Post save(Post post) {
        postList.add(post);
        return post;
    }

    public Long generateId() {
        return nextId++;
    }

    public Post findById(Long id) {  // null 대신 Optional
        return postList.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(PostNotFoundException::new);
    }

    public List<Post> getAllPosts() {
        return postList;
    }

    public void remove(Post post) {
        postList.remove(post);
    }
}
