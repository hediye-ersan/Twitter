package com.workintech.Twitter.services;

import com.workintech.Twitter.entity.Like;

public interface LikeService {
    void likeTweet(Long userId, Long tweetId);
    void dislikeTweet(Long userId, Long tweetId);
    Long countLikes(Long tweetId);
}
