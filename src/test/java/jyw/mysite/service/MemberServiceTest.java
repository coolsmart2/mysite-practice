package jyw.mysite.service;

import jyw.mysite.domain.Member;
import jyw.mysite.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemoryMemberRepository memberRepository = new MemoryMemberRepository();
    MemberService memberService = new MemberService(memberRepository);

    @Test
    void 로그인아이디로회원찾기() {
        Member member = new Member("test", "1111");
        memberService.join(member);

        Member findMember = memberService.findOneByLoginId("adf");
        Assertions.assertThat(findMember).isEqualTo(null);
    }
}