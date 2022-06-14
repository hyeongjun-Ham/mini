package com.project.mini.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.mini.dto.request.PostDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@SequenceGenerator(
        name = "POST_ID_GENERATOR",
        sequenceName = "POST_SEQUENCES",
        initialValue = 1, allocationSize = 100
)
@Table(name = "POST")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "POST_ID_GENERATOR")
    @Column(name = "post_id")
    private Long postId;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String img;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "post",cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    @Column(nullable = false)
    private int happypoint;


    public Post(PostDto dto , User user){
        this.user = user;
        this.happypoint = dto.getHappypoint();
        this.img = dto.getImg();
        this.content = dto.getContent();
    }
}
