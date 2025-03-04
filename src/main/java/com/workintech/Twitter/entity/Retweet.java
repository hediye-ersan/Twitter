package com.workintech.Twitter.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "retweets", schema = "twitter")
public class Retweet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "retweet_id")
    @NotNull
    private Long id;

    //Retweet bir tweete aittir.
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},
            fetch = FetchType.LAZY)
    @JoinColumn(name = "tweet_id")
    private Tweet tweet;

    //Retweet bir kullanıcıya ait olur.
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
