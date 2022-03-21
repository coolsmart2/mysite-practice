package jyw.mysite.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Post {

    @Id @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
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
