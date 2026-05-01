package org.sopt.repository;

import org.sopt.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("select distinct p from Post p join fetch p.user left join fetch p.likes")
    List<Post> findAllWithUserAndLikes();

    @Query("select distinct p from Post p join fetch p.user left join fetch p.likes where p.boardType = :boardType")
    List<Post> findAllByBoardTypeWithUserAndLikes(@Param("boardType") org.sopt.domain.BoardType boardType);
}
