package com.workintech.Twitter.repository;

import com.workintech.Twitter.entity.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TweetRepository extends JpaRepository<Tweet, Long> {

    List<Tweet> findByUserId(Long userId);

    //Kullanıcının tüm tweetlerini listeler son atılan en tepede gözükecek şekilde.
    @Query("SELECT t FROM Tweet t WHERE t.user.id = :userId ORDER BY t.id DESC")
    List<Tweet> findAllByUserId(Long userId);
}
