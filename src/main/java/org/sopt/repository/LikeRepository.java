package org.sopt.repository;

import org.sopt.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByUserIdAndPostId(Long userId, Long postId);

    Optional<Like> findByUserIdAndPostId(Long userId, Long postId);

    long countByPostId(Long postId);

    @Query("""
            select l.post.id as postId, count(l.id) as likeCount
            from Like l
            where l.post.id in :postIds
            group by l.post.id
            """)
    List<PostLikeCount> countLikesByPostIds(@Param("postIds") List<Long> postIds);

    @Modifying
    @Query("delete from Like l where l.post.id = :postId")
    void deleteByPostId(@Param("postId") Long postId);

    interface PostLikeCount {
        Long getPostId();

        Long getLikeCount();
    }
}
