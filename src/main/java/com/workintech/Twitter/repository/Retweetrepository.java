package com.workintech.Twitter.repository;

import com.workintech.Twitter.entity.Retweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface Retweetrepository extends JpaRepository<Retweet, Long> {

    //Kullanıcının bir tweeti retweet edip etmediğini kontrol etmesini sağlar
    @Query("SELECT r FROM Retweet r WHERE r.user.id = userId AND r.tweet.id = tweetId")
    Optional<Retweet> findByUserAndTweet(Long userId, Long tweetId);
}
