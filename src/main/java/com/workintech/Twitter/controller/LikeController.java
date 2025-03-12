package com.workintech.Twitter.controller;

import com.workintech.Twitter.entity.User;
import com.workintech.Twitter.services.LikeService;
import com.workintech.Twitter.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
@AllArgsConstructor
public class LikeController {

    private final LikeService likeService;
    private final UserService userService;

    @PostMapping("/{tweetId}")
    public ResponseEntity<String> likeTweet(@PathVariable Long tweetId, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            // Kullanıcıyı user_name (kullanıcı adı) ile buluyoruz
            User user = userService.findByUsername(userDetails.getUsername())
                    .orElseThrow(() -> new EntityNotFoundException("User with username " + userDetails.getUsername() + " not found"));

            Long authenticatedUserId = user.getId(); // Kullanıcı ID'sini alıyoruz
            System.out.println("Authenticated User ID: " + authenticatedUserId);

            likeService.likeTweet(authenticatedUserId, tweetId);  // Kullanıcı ID'si ile tweetId'yi gönderiyoruz
            return ResponseEntity.ok("Tweet liked successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + e.getMessage());
        }
    }

    @PostMapping("/dislike/{tweetId}")
    public ResponseEntity<String> dislikeTweet(@PathVariable Long tweetId, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            User user = userService.findByUsername(userDetails.getUsername())
                    .orElseThrow(() -> new EntityNotFoundException("User with username " + userDetails.getUsername() + " not found"));

            Long authenticatedUserId = user.getId();
            System.out.println("Authenticated User ID: " + authenticatedUserId);

            likeService.dislikeTweet(authenticatedUserId, tweetId);
            return ResponseEntity.ok("Tweet disliked successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + e.getMessage());
        }
    }

    // Bir tweet'in kaç beğeni aldığını döndüren endpoint
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
