//package jyw.mysite.service;
//
//import jyw.mysite.domain.Member;
//import jyw.mysite.repository.MemberRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class MemberService {
//
//    private final MemberRepository memberRepository;
//
//    public Member loginMember(Member member) {
//        if (checkMember(member)) {
//            memberRepository.save(member);
//            return member;
//        }
//        return null;
//    }
//
//    private boolean checkMember(Member member) {
//        return memberRepository.findByLoginId(member.getLoginId()).isEmpty();
//    }
//}
