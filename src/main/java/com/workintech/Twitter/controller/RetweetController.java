package com.workintech.Twitter.controller;

import com.workintech.Twitter.services.RetweetService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/retweet")
@AllArgsConstructor
public class RetweetController {

    private final RetweetService retweetService;


    //Tweet id'si ve kullanıcı id'si ile retweet yapılır
    @PostMapping
    public String retweetTweet(@RequestParam Long userId, @RequestParam Long tweetId) {
        try {
            retweetService.retweet(userId, tweetId);
            return "Tweet retweeted successfully";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    //Tweet id'si ve kullanıcı id'si ile retweet kaldırılır
    @DeleteMapping
    public String unretweetTweet(@RequestParam Long userId,@RequestParam Long tweetId) {
        try {
            retweetService.unretweet(userId, tweetId);
            return "Tweet unretweeted successfully";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

}
