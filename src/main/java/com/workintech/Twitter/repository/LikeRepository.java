package com.workintech.Twitter.repository;

import com.workintech.Twitter.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    //kullanıcının tweeti beğenip beğenmediğini kontrol eder
    boolean existsByUserIdAndTweetId(Long userId, Long tweetId);
    void deleteByUserIdAndTweetId(Long userId, Long tweetId);
}
