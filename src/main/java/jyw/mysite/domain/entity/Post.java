package jyw.mysite.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Post {

    @Id @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();
    private LocalDateTime localDateTime;
    private String title;
    private String content;

    public Post() {
    }

    public Post(Member member, LocalDateTime localDateTime, String title, String content) {
        this.member = member;
        this.localDateTime = localDateTime;
        this.title = title;
        this.content = content;
    }
}
