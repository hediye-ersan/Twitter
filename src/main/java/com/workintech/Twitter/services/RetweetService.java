package com.workintech.Twitter.services;

public interface RetweetService {
    void retweet(Long userId, Long tweetId);
    void unretweet(Long userId, Long tweetId);
    Long countRetweets(Long tweetId);
}
