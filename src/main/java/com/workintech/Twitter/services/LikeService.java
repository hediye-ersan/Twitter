package com.workintech.Twitter.services;

import com.workintech.Twitter.entity.Like;

public interface LikeService {
    //Kullanıcının tweeti beğenip beğenmediğini kontrol etmesini sağlar
    void likeTweet(Long userId, Long tweetId);
    //Tweeti beğenmeyi kaldırır
    void dislikeTweet(Long userId, Long tweetId);
    //Tweetin kaç beğeni aldığını sayar
    Long countLikes(Long tweetId);
}
