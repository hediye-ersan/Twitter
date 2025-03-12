package com.workintech.Twitter.controller;

import com.workintech.Twitter.dto.TweetResponse;
import com.workintech.Twitter.entity.Tweet;
import com.workintech.Twitter.entity.User;
import com.workintech.Twitter.services.TweetService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/tweets")
public class TweetController {

    private final TweetService tweetService;

    // Tweet oluşturulur
    @PostMapping("/user/{userId}")
    public ResponseEntity<TweetResponse> createTweet(
            @PathVariable Long userId,
            @AuthenticationPrincipal User authenticatedUser,  // Basic Auth’tan gelen kullanıcı
            @Valid @RequestBody Tweet tweet) {

        // Kullanıcı sadece kendi adına tweet atabilir
        if (!authenticatedUser.getId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // 403 Forbidden
        }

        Tweet createdTweet = tweetService.createTweet(userId, tweet);
        TweetResponse tweetResponse = new TweetResponse(
                createdTweet.getId(),
                createdTweet.getText(),
                createdTweet.getUser().getUsername(),
                createdTweet.getLikeCount(),
                createdTweet.getRetweetCount()
        );
        return new ResponseEntity<>(tweetResponse, HttpStatus.CREATED);
    }
    // Kullanıcı id'sine göre tüm tweet'leri getirilir
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TweetResponse>> getAllTweetsByUserId(@PathVariable Long userId) {
        List<Tweet> tweets = tweetService.getAllTweetsByUserId(userId);
        List<TweetResponse> tweetResponses = tweets.stream()
                .map(tweet -> new TweetResponse(tweet.getId(), tweet.getText(), tweet.getUser().getUsername(), tweet.getLikeCount(), tweet.getRetweetCount()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(tweetResponses);
    }

    // Tweet id'sine göre tweet getirilir
    @GetMapping("/{id}")
    public ResponseEntity<TweetResponse> getTweetById(@PathVariable Long id) {
        Optional<Tweet> tweet = tweetService.getTweetById(id);
        return tweet.map(t -> new ResponseEntity<>(new TweetResponse(t.getId(), t.getText(), t.getUser().getUsername(), t.getLikeCount(), t.getRetweetCount()), HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<TweetResponse>> getAllTweets() {
        List<Tweet> tweets = tweetService.getAllTweets();
        List<TweetResponse> tweetResponses = tweets.stream()
                .map(tweet -> new TweetResponse(tweet.getId(), tweet.getText(), tweet.getUser().getUsername(), tweet.getLikeCount(), tweet.getRetweetCount()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(tweetResponses);
    }

    @PutMapping("/user/{userId}/tweet/{id}")
    public ResponseEntity<TweetResponse> updateTweet(@PathVariable Long id, @RequestBody Tweet updatedTweet, @PathVariable Long userId) {
        Tweet tweet = tweetService.updateTweet(id, updatedTweet, userId);
        TweetResponse tweetResponse = new TweetResponse(tweet.getId(), tweet.getText(), tweet.getUser().getUsername(), tweet.getLikeCount(), tweet.getRetweetCount());
        return ResponseEntity.ok(tweetResponse);
    }

   @DeleteMapping("/user/{userId}/tweet/{id}")
   public ResponseEntity<String> deleteTweet(@PathVariable Long id, @PathVariable Long userId) {
       tweetService.deleteTweet(id, userId);
       return ResponseEntity.ok("Tweet successfully deleted");
   }
}
