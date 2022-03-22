package jyw.mysite.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Comment {

    @Id @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment parentComment;
    @OneToMany(mappedBy = "parentComment")
    private List<Comment> childComment;

    private String content;

    private LocalDateTime localDateTime;

    public Comment() {
    }

    public Comment(Post post, Member member, Comment parentComment, String content, LocalDateTime localDateTime) {
        this.post = post;
        this.member = member;
        this.parentComment = parentComment;
        this.content = content;
        this.localDateTime = localDateTime;
    }
}
