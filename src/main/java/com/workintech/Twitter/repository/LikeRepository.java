package com.workintech.Twitter.repository;

import com.workintech.Twitter.entity.Like;
import com.workintech.Twitter.entity.Tweet;
import com.workintech.Twitter.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {


    //Kullanıcının tweeti beğenip beğenmediğini kontrol etmesini sağlar
    @Query("SELECT l FROM Like l WHERE l.user.id = userId AND l.tweet.id = tweetId")
    Optional<Like> findByUserAndTweet(User user, Tweet tweet);

    //Tweetin kaç beğeni aldığını sayar
    @Query("SELECT COUNT(l) FROM Like l WHERE l.tweet.id = tweetId")
    Long countByTweetId(Long tweetId);

    //Kullanıcının tweetdeki beğenisini silmesini sağlar
    void deleteByUserAndTweet(User user, Tweet tweet);

}
