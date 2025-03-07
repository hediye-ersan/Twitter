package com.workintech.Twitter.services;

import com.workintech.Twitter.entity.Tweet;
import com.workintech.Twitter.repository.TweetRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class TweetServiceImpl implements TweetService {

    private final TweetRepository tweetRepository;


    @Override
    public Tweet createTweet(Tweet tweet) {
        if (tweet.getUser() == null) {
            throw new IllegalArgumentException("Tweet bir kullanıcıya ait olmalıdır.");
        }
        tweet.setLikeCount(0);  // Yeni bir tweet oluşturulurken beğeni sayısını sıfırlayalım.
        return tweetRepository.save(tweet);
    }

    @Override
    public List<Tweet> getAllTweetsByUserId(Long userId) {
        return tweetRepository.findAllByUserId(userId);
    }

    @Override
    public Optional<Tweet> getTweetById(Long id) {
        return tweetRepository.findById(id);
    }

    @Override
    public Tweet updateTweet(Long id, Tweet updatedTweet, Long userId) {
        Optional<Tweet> tweetOptional = tweetRepository.findById(id);
        if(tweetOptional.isPresent()){
            Tweet tweet = tweetOptional.get();
            if (!tweet.getUser().getId().equals(userId)) {
                throw new IllegalArgumentException("Sadece tweet sahibi tweeti güncelleyebilir.");
            }
            tweet.setText(updatedTweet.getText());

            return tweetRepository.save(tweet);
        } else {
            throw new IllegalArgumentException("Tweet bulunamadı.");

        }
    }

    @Override
    public void deleteTweet(Long id, Long userId) {
        Optional<Tweet> tweetOptional = tweetRepository.findById(id);
        if (tweetOptional.isPresent()){
            Tweet tweet = tweetOptional.get();
            if (!tweet.getUser().getId().equals(userId)) {
                throw new IllegalArgumentException("Sadece tweet sahibi tweeti silebilir.");
            }
            tweetRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Tweet bulunamadı.");
        }

    }
}
