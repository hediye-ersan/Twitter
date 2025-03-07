package com.workintech.Twitter.services;

import com.workintech.Twitter.entity.Comment;
import com.workintech.Twitter.entity.Tweet;
import com.workintech.Twitter.entity.User;
import com.workintech.Twitter.repository.CommentRepository;
import com.workintech.Twitter.repository.TweetRepository;
import com.workintech.Twitter.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;


    @Override
    public Comment addComment(Long tweetId, Long userId, String content) {
        Tweet tweet = tweetRepository.findById(tweetId).orElseThrow(() -> new RuntimeException("Tweet not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User  not found"));
        Comment comment = new Comment();
        comment.setTweet(tweet);
        comment.setUser (user);
        comment.setContent(content);
        comment.setCreatedAt(LocalDateTime.now());
        return commentRepository.save(comment);

    }

    @Override
    public Comment updateComment(Long commentId, Long userId, String newContent) {
        Comment comment = commentRepository.findByIdAndUserId(commentId, userId)
                .orElseThrow(() -> new RuntimeException("Unauthorized or comment not found"));
        comment.setContent(newContent);
        comment.setUpdatedAt(LocalDateTime.now());
        return commentRepository.save(comment);
    }


    @Override
    public void deleteComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        Long tweetOwnerId = comment.getTweet().getUser ().getId();
        if (!comment.getUser ().getId().equals(userId) && !tweetOwnerId.equals(userId)) {
            throw new RuntimeException("You are not authorized to delete this comment");
        }

        commentRepository.deleteById(commentId);

    }
}
