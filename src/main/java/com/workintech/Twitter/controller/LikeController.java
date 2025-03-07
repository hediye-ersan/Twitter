package com.workintech.Twitter.controller;

import com.workintech.Twitter.services.LikeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/like")
@AllArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<String> likeTweet(@RequestParam Long userId, @RequestParam Long tweetId) {
        try {
            likeService.likeTweet(userId, tweetId);
            return ResponseEntity.ok("Tweet liked successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/dislike")
    public ResponseEntity<String> dislikeTweet(@RequestParam Long userId, @RequestParam Long tweetId) {
        try {
            likeService.dislikeTweet(userId, tweetId);
            return ResponseEntity.ok("Tweet disliked successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
