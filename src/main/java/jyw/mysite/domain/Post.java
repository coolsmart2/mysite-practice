package jyw.mysite.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Post {

    private Long id;
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
