package jyw.mysite.service;

import jyw.mysite.domain.Member;
import jyw.mysite.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member findOneById(Long id) {
        Optional<Member> findMember = memberRepository.findById(id);
        return findMember.orElse(null);
    }

    public Member findOneByLoginId(String loginId) {
        List<Member> findMember = memberRepository.findByLoginId(loginId);
        if (!findMember.isEmpty()) {
            return findMember.get(0);
        }
        return null;
    }

}
