package com.workintech.Twitter.services;

import com.workintech.Twitter.entity.Comment;
import com.workintech.Twitter.entity.Tweet;
import com.workintech.Twitter.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    Comment addComment(Long tweetId, Long userId, String content);
    Comment updateComment(Long commentId, Long userId, String newContent);
    void deleteComment(Long commentId, Long userId);

}
