package jyw.mysite;

import jyw.mysite.domain.entity.Comment;
import jyw.mysite.domain.entity.Member;
import jyw.mysite.domain.entity.Post;
import jyw.mysite.service.CommentService;
import jyw.mysite.service.MemberService;
import jyw.mysite.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberService memberService;
    private final BoardService postService;
    private final CommentService commentService;

    @PostConstruct
    public void init() {
        Member member1 = new Member("test1", "1111!");
        memberService.join(member1);

        Member member2 = new Member("test2", "1111!");
        memberService.join(member2);

        for (int i = 0; i < 0; i++) {
            postService.join(new Post(member1, LocalDateTime.now(), "Hello " + i, "Woooooooooorld!"));
        }

//        String title = "test comment";
//        String content = "write a comment~~~!!!!\n".repeat(10);
//        Post post = new Post(member2, LocalDateTime.now(), title, content);
//        postService.join(post);
//
//        for (int i = 0; i < 5; i++) {
//            Comment comment1 = new Comment(post, member2, null, "parent comment" + i, LocalDateTime.now());
//            commentService.join(comment1);
//            Comment comment2 = new Comment(post, member1, comment1, "child comment" + i, LocalDateTime.now());
//            commentService.join(comment2);
//        }

    }
}
