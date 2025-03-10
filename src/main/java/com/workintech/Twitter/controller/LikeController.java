package com.workintech.Twitter.controller;

import com.workintech.Twitter.services.LikeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
@AllArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/{userId}/{tweetId}")
    public ResponseEntity<String> likeTweet(@PathVariable Long userId, @PathVariable Long tweetId) {
        try {
            likeService.likeTweet(userId, tweetId);
            return ResponseEntity.ok("Tweet liked successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + e.getMessage());
        }
    }

    @PostMapping("/dislike/{userId}/{tweetId}")
    public ResponseEntity<String> dislikeTweet(@PathVariable Long userId, @PathVariable Long tweetId) {
        try {
            likeService.dislikeTweet(userId, tweetId);
            return ResponseEntity.ok("Tweet disliked successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/count/{tweetId}")
    public ResponseEntity<Long> countLikes(@PathVariable Long tweetId) {
        try {
            Long likeCount = likeService.countLikes(tweetId);
            return ResponseEntity.ok(likeCount);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
}
