package com.workintech.Twitter.services;

import com.workintech.Twitter.entity.Tweet;

import java.util.List;
import java.util.Optional;

public interface TweetService {

  Tweet createTweet(Long userID, Tweet tweet);
  List<Tweet> getAllTweetsByUserId(Long userId);
  Optional<Tweet> getTweetById(Long id);
  Tweet updateTweet(Long id, Tweet updatedTweet, Long userId);
  void deleteTweet(Long id, Long userId);


}
