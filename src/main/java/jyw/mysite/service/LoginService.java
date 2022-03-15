package jyw.mysite.service;

import jyw.mysite.domain.Member;
import jyw.mysite.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    public boolean loginMember(Member member) {
        List<Member> findMember = memberRepository.findByLoginId(member.getLoginId());
        if (!findMember.isEmpty()) {
            return findMember.get(0).getPassword().equals(member.getPassword());
        }
        return false;
    }
}
