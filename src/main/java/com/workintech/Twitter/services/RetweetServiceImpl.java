package com.workintech.Twitter.services;

import com.workintech.Twitter.entity.Retweet;
import com.workintech.Twitter.entity.Tweet;
import com.workintech.Twitter.entity.User;
import com.workintech.Twitter.repository.Retweetrepository;
import com.workintech.Twitter.repository.TweetRepository;
import com.workintech.Twitter.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RetweetServiceImpl implements RetweetService {

    private final Retweetrepository retweetRepository;
    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;


    @Override
    @Transactional
    public void retweet(Long userId, Long tweetId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new IllegalArgumentException("Tweet not found"));

        Optional<Retweet> existingRetweet = retweetRepository.findByUserAndTweet(userId, tweetId);

        if (existingRetweet.isPresent()) {
            throw new IllegalArgumentException("User already retweeted this tweet");
        }

        Retweet retweet = new Retweet();
        retweet.setUser(user);
        retweet.setTweet(tweet);
        retweetRepository.save(retweet);

        // Retweet sayısını günceller
        tweet.setRetweetCount(tweet.getRetweetCount() + 1);
        tweetRepository.save(tweet);  // Güncellenmiş tweet'i kaydeder

    }

    @Override
    @Transactional
    public void unretweet(Long userId, Long tweetId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new IllegalArgumentException("Tweet not found"));
        Optional<Retweet> existingRetweet = retweetRepository.findByUserAndTweet(userId, tweetId);
        if (existingRetweet.isEmpty()) {
            throw new IllegalArgumentException("User hasn't retweeted this tweet yet");
        }
        retweetRepository.delete(existingRetweet.get());

        // Retweet sayısını günceller
        tweet.setRetweetCount(tweet.getRetweetCount() - 1);
        tweetRepository.save(tweet);  // Güncellenmiş tweet'i kaydeder

    }

    @Override
    public Long countRetweets(Long tweetId) {
        return retweetRepository.countByTweetId(tweetId);
    }
}
