package com.workintech.Twitter.dto;

public record TweetResponse(Long id, String text, String username, int likeCount, int retweetCount) {
}
