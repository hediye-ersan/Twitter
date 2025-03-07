package com.workintech.Twitter.services;

import com.workintech.Twitter.entity.Like;
import com.workintech.Twitter.entity.Tweet;
import com.workintech.Twitter.entity.User;
import com.workintech.Twitter.repository.LikeRepository;
import com.workintech.Twitter.repository.TweetRepository;
import com.workintech.Twitter.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LikeServiceImpl implements LikeService{

    private final LikeRepository likeRepository;
    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;


    @Override
    @Transactional
    public void likeTweet(Long userId, Long tweetId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User  not found"));
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new IllegalArgumentException("Tweet not found"));

        Optional<Like> existingLike = likeRepository.findByUserAndTweet(user, tweet);

        if (existingLike.isPresent()) {
            throw new IllegalArgumentException("User  already liked this tweet");
        }

        Like like = new Like();
        like.setUser (user);
        like.setTweet(tweet);
        likeRepository.save(like);

        //Like sayısını günceller
        tweet.setLikeCount(tweet.getLikeCount() + 1);
        tweetRepository.save(tweet);  //Güncellenmiş tweet'i kaydeder
    }


    @Override
    @Transactional
    public void dislikeTweet(Long userId, Long tweetId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new IllegalArgumentException("Tweet not found"));

        Optional<Like> existingLike = likeRepository.findByUserAndTweet(user, tweet);

        if (existingLike.isEmpty()) {
            throw new IllegalArgumentException("User hasn't liked this tweet yet");
        }

        likeRepository.deleteByUserAndTweet(user, tweet);

        //Like sayısını günceller
        tweet.setLikeCount(tweet.getLikeCount() - 1);
        tweetRepository.save(tweet);  //Güncellenmiş tweet'i kaydeder
    }

    @Override
    public Long countLikes(Long tweetId) {
        return likeRepository.countByTweetId(tweetId);
    }
}
