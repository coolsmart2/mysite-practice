package jyw.mysite.repository;

import jyw.mysite.domain.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository memberRepository = new MemoryMemberRepository();

    @BeforeEach
    void beforeEach() {
        memberRepository.clear();
    }

    @Test
    void memorySave() {
        Member member = new Member("test", "1111");
        System.out.println("member.getId() = " + member.getId());
        memberRepository.save(member);
        System.out.println("member.getId() = " + member.getId());

        Optional<Member> findMember = memberRepository.findById(member.getId());

//        if (findMember.isPresent()) {
//            Assertions.assertThat(findMember.get().getLoginId()).isEqualTo(member.getLoginId());
//        }

        findMember.ifPresent(value -> Assertions.assertThat(value.getLoginId()).isEqualTo(member.getLoginId()));
    }

    @Test
    void 로그인아이디로회원찾기() {
        Member member = new Member("test", "1111");
        memberRepository.save(member);

        List<Member> byLoginId = memberRepository.findByLoginId("test2");
        if (!byLoginId.isEmpty()) {
            Assertions.assertThat(byLoginId.get(0)).isEqualTo(null);
        }
    }

}