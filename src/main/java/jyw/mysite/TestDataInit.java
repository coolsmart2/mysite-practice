package jyw.mysite;

import jyw.mysite.domain.Member;
import jyw.mysite.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberRepository memberRepository;

    @PostConstruct
    public void init() {
        Member member = new Member("test", "1111!");
        memberRepository.save(member);
        log.info("init member={}", member);
        log.info("members={}", memberRepository.findAll());
    }
}
