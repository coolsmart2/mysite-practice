package jyw.mysite;

import jyw.mysite.domain.entity.Member;
import jyw.mysite.domain.entity.Post;
import jyw.mysite.repository.MemberRepository;
import jyw.mysite.repository.PostRepository;
import jyw.mysite.service.MemberService;
import jyw.mysite.service.PostService;
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
    private final PostService postService;

    @PostConstruct
    public void init() {
        Member member = new Member("test", "1111!");
        memberService.join(member);
        log.info("init member={}", member);
        log.info("members={}", memberService.findAll());


        for (int i = 0; i < 1000; i++) {
            String title = "Hello " + i;
            String content = "Woooooooooorld!";
            postService.join(new Post(member, LocalDateTime.now(), title, content));
        }
    }
}
