package jyw.mysite;

import jyw.mysite.domain.Member;
import jyw.mysite.domain.Post;
import jyw.mysite.repository.MemberRepository;
import jyw.mysite.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @PostConstruct
    public void init() {
        Member member = new Member("test", "1111!");
        memberRepository.save(member);
        log.info("init member={}", member);
        log.info("members={}", memberRepository.findAll());


        for (int i = 0; i < 5; i++) {
            String title = "Hello " + i;
            String content = "Woooooooooorld!";
            postRepository.save(new Post(member, LocalDateTime.now(), title, content));
        }
    }
}
