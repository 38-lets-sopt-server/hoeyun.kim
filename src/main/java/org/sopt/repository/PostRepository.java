package org.sopt.repository;

import org.sopt.domain.Post;

import java.util.List;

public interface PostRepository {
    Post save(Post post);

    Long generateId();

    Post findById(Long id);

    List<Post> getAllPosts();

    void remove(Post post);
}
