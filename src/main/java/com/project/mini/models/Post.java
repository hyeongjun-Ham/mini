package com.project.mini.models;

import com.project.mini.dto.request.PostDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@SequenceGenerator(
        name = "POST_ID_GENERATOR",
        sequenceName = "POST_SEQUENCES",
        initialValue = 1, allocationSize = 100
)
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
    private User user;

    @Column(nullable = false)
    private Long happypoint;

    public Post(PostDto dto){
        this.happypoint = dto.getHappypoint();
        this.img = dto.getImg();
        this.content = dto.getContent();
    }
}
