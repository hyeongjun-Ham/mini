package com.project.mini.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.mini.dto.request.PostDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@Entity
@SequenceGenerator(
        name = "POST_ID_GENERATOR",
        sequenceName = "POST_SEQUENCES",
        initialValue = 1, allocationSize = 1
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
    private String imgUrl;

    @Column(nullable = false)
    private String imgFilename;


    @Column(nullable = false)
    private String transImgFileName;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "post",cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    @Column(nullable = false)
    private int happypoint;


    public Post(PostDto dto , User user , Map<String , String> imgResult){
        this.imgUrl = imgResult.get("url");
        this.imgFilename = imgResult.get("fileName");
        this.transImgFileName = imgResult.get("transImgFileName");
        this.user = user;
        this.happypoint = dto.getHappypoint();
        this.content = dto.getContent();
    }
    public void Update(PostDto dto , Map<String , String> imgResult){
        this.imgUrl = imgResult.get("url");
        this.imgFilename = imgResult.get("fileName");
        this.transImgFileName = imgResult.get("transImgFileName");
        this.content = dto.getContent();
        this.happypoint = dto.getHappypoint();
    }

    public void Update(PostDto dto) {
        this.content = dto.getContent();
        this.happypoint = dto.getHappypoint();
    }
}
