package com.workintech.Twitter.repository;

import com.workintech.Twitter.entity.Comment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByTweetId(Long tweetId);
    Optional<Comment> findByIdAndUserId(Long commentId, Long userId);
    @Modifying
    @Query("DELETE FROM Comment c WHERE c.id = :commentId AND (c.user.id = :userId OR c.tweet.user.id = :tweetOwnerId)")
    void deleteByCommentIdAndUserIdOrTweetOwnerId(Long commentId, Long userId, Long tweetOwnerId);

}
