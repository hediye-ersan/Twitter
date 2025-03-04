package com.workintech.Twitter.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull

    private Long id;

    @NotBlank
    @Column(name = "text")
    private String text;

    //Bir kullanıcı tarafından bir çok tweet atılabilir.
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    //Bir tweetin beğeni sayısını öğrenmek için bu ilişki kuruldu.
    @OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL)
    private List<Like> likes;

    //Beğeni sayısı tutar
    @Column(name = "like_count")
    private int likeCount;

    //bir tweetin birden çok yorumu olabilir
    @OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL)
    private List<Comment> comments;

    //Bir tweet birden çok retweet olabilir
    @OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL)
    private List<Retweet> retweets;








}
