package org.sopt.repository;

import org.sopt.domain.Post;
import org.sopt.exception.PostNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryPostRepository implements PostRepository {
    private static final List<Post> postList = new ArrayList<>();
    private Long nextId = 1L;

    @Override
    public Post save(Post post) {
        postList.add(post);
        return post;
    }

    @Override
    public Long generateId() {
        return nextId++;
    }

    @Override
    public Post findById(Long id) {
        return postList.stream()
                .filter(post -> post.getId().equals(id))
                .findFirst()
                .orElseThrow(PostNotFoundException::new);
    }

    @Override
    public List<Post> getAllPosts() {
        return postList;
    }

    @Override
    public void remove(Post post) {
        postList.remove(post);
    }
}
