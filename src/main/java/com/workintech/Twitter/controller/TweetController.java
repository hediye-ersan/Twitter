package com.workintech.Twitter.controller;

import com.workintech.Twitter.entity.Tweet;
import com.workintech.Twitter.services.TweetService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/tweet")
public class TweetController {

    private final TweetService tweetService;

    @PostMapping//Yeni bir tweet oluşturuyoruz.
    public ResponseEntity<Tweet> createTweet(@RequestBody Tweet tweet) {
        Tweet createdTweet = tweetService.createTweet(tweet);
        return new ResponseEntity<>(createdTweet, HttpStatus.CREATED);
    }

    @GetMapping("/{findByUserId}")//Bu satırda kullanıcıya ait tüm tweetler getiriliyor.
    public ResponseEntity<List<Tweet>> getAllTweetsByUserId(@PathVariable Long userId) {
        List<Tweet> tweets = tweetService.getAllTweetsByUserId(userId);
        return ResponseEntity.ok(tweets);
    }

    @GetMapping("/findById")//Bu satırda tweet id'sine göre tweet getiriliyor.
    public ResponseEntity<Tweet> getTweetById(@RequestParam Long id) {
        Optional<Tweet> tweet = tweetService.getTweetById(id);
        return tweet.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")//Bu satırda tweet güncelleniyor.
    public ResponseEntity<Tweet> updateTweet(@PathVariable Long id, @RequestBody Tweet updatedTweet, @RequestParam Long userId) {
        Tweet tweet = tweetService.updateTweet(id, updatedTweet, userId);
        return ResponseEntity.ok(tweet);
    }

    @DeleteMapping("/{id}")//Bu satırda tweet siliniyor.
    public ResponseEntity<Void> deleteTweet(@PathVariable Long id, @RequestParam Long userId) {
        tweetService.deleteTweet(id, userId);
        return ResponseEntity.noContent().build();
    }
}
