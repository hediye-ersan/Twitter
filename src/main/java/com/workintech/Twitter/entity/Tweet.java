package com.workintech.Twitter.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "tweets", schema = "twitter")
public class Tweet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tweet_id")
    private Long id;

    @NotBlank(message = "Tweet metni boş olamaz.")
    @Size(max = 280, message = "Tweet 280 karakterden fazla olamaz.")
    @Column(name = "text")
    private String text;

    //Bir kullanıcı tarafından bir çok tweet atılabilir.
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    //Bir tweetin beğeni sayısını öğrenmek için bu ilişki kuruldu, birden çok beğenisi olabilir
    @OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Like> likes;

    //Beğeni sayısı tutar
    @Column(name = "like_count")
    private int likeCount;

    //bir tweetin birden çok yorumu olabilir
    @OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Comment> comments;

    //Bir tweet birden çok retweet olabilir
    @OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Retweet> retweets;

    //Retweet sayısını tutar
    @Column(name = "retweet_count")
    private int retweetCount;








}
