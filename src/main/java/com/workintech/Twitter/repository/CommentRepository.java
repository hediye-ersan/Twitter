package com.workintech.Twitter.repository;

import com.workintech.Twitter.entity.Comment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    //Bir Tweetin tüm yorumlarını listeler
    @Query("SELECT c FROM Comment c WHERE c.tweet.id = :tweetId")
    List<Comment> findAllByTweetId(Long tweetId);


    //Kullanıcının yorumu bulmasını sağlar
    @Query("SELECT c FROM Comment c WHERE c.id = :commentId AND c.user.id = :userId")
    Optional<Comment> findByIdAndUserId(Long commentId, Long userId);


    //Yorum sahibi veya tweet sahibinin yorumu silebilmesini sağlar
    @Modifying
    @Transactional
    @Query("DELETE FROM Comment c WHERE c.id = :commentId AND (c.user.id = :userId OR c.tweet.user.id = :tweetOwnerId)")
    void deleteByIdAndUserOrTweetOwner(Long commentId, Long userId, Long tweetOwnerId);
}
