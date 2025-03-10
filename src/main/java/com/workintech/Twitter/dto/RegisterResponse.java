package com.workintech.Twitter.dto;

import com.workintech.Twitter.entity.Comment;
import com.workintech.Twitter.entity.Retweet;
import com.workintech.Twitter.entity.Tweet;

import java.util.List;

public record RegisterResponse(String username, String password, List<Tweet> tweets, List<Comment> comments , List<Retweet> retweets) {
}
