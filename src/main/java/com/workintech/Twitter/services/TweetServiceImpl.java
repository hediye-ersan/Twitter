package com.workintech.Twitter.services;

import com.workintech.Twitter.entity.Tweet;
import com.workintech.Twitter.entity.User;
import com.workintech.Twitter.repository.TweetRepository;
import com.workintech.Twitter.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class TweetServiceImpl implements TweetService {

    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;


    @Override
    public List<Tweet> getAllTweets() {
        return tweetRepository.findAll();
    }

    @Override
    public Tweet createTweet(Long userId, Tweet tweet) {
        // Kullanıcıyı bul
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Kullanıcıyı tweet'e ekle
        tweet.setUser(user);

        return tweetRepository.save(tweet);
    }

    // Kullanıcı id'sine göre tüm tweet'leri getirilir
    @Override
    public List<Tweet> getAllTweetsByUserId(Long userId) {
        return tweetRepository.findByUserId(userId);
    }

    // Tweet id'sine göre tweet getirilir
    @Override
    public Optional<Tweet> getTweetById(Long id) {
        return tweetRepository.findById(id);
    }



    @Override
    public Tweet updateTweet(Long id, Tweet updatedTweet, Long userId) {
        Tweet tweet = tweetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tweet not found"));

        // Tweet'in sahibi kontrol ediliyor
        if (!tweet.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }

        tweet.setText(updatedTweet.getText());
        return tweetRepository.save(tweet);
    }

    @Override
    public void deleteTweet(Long id, Long userId) {
        Tweet tweet = tweetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tweet not found"));

        // Tweet'in sahibi mi kontrol et
        if (!tweet.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }

        tweetRepository.delete(tweet);
    }
}
