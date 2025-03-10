package com.workintech.Twitter.services;

import com.workintech.Twitter.entity.Like;
import com.workintech.Twitter.entity.Tweet;
import com.workintech.Twitter.entity.User;
import com.workintech.Twitter.repository.LikeRepository;
import com.workintech.Twitter.repository.TweetRepository;
import com.workintech.Twitter.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
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
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + userId + " not found"));

        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new EntityNotFoundException("Tweet with ID " + tweetId + " not found"));

        Optional<Like> existingLike = likeRepository.findByUserAndTweet(user, tweet);

        if (existingLike.isPresent()) {
            throw new IllegalArgumentException("User with ID " + userId + " already liked this tweet with ID " + tweetId);
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
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + userId + " not found"));

        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new EntityNotFoundException("Tweet with ID " + tweetId + " not found"));

        Optional<Like> existingLike = likeRepository.findByUserAndTweet(user, tweet);

        if (existingLike.isEmpty()) {
            throw new IllegalArgumentException("User with ID " + userId + " hasn't liked this tweet with ID " + tweetId);
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
