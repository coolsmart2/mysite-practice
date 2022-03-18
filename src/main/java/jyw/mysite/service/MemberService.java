package jyw.mysite.service;

import jyw.mysite.domain.Member;
import jyw.mysite.exception.CheckPwException;
import jyw.mysite.exception.LoginIdException;
import jyw.mysite.exception.PwPatternException;
import jyw.mysite.repository.MemberConst;
import jyw.mysite.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member loginMember(Member member) {
        List<Member> findMember = memberRepository.findByLoginId(member.getLoginId());
        if (!findMember.isEmpty()) {
            if (findMember.get(0).getPassword().equals(member.getPassword())) {
                return findMember.get(0);
            }
        }
        return null;
    }

    public Member join(Member member) {
        return memberRepository.save(member);
    }

    public Member joinAndSignUp(Member member, String checkPassword) throws LoginIdException, PwPatternException, CheckPwException {
        validateDuplicateMember(member.getLoginId());
        validatePwPattern(member.getPassword());
        validateCheckPw(member.getPassword(), checkPassword);
        return memberRepository.save(member);
    }

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

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    private void validateDuplicateMember(String loginId) throws LoginIdException {
        List<Member> findMember = memberRepository.findByLoginId(loginId);
        if (!findMember.isEmpty()) {
            throw new LoginIdException("존재하는 아이디 입니다.");
        }
    }

    private void validatePwPattern(String password) throws PwPatternException {
        Pattern pattern = Pattern.compile(MemberConst.PASSWORD_PATTERN(MemberConst.PASSWORD_MIN, MemberConst.PASSWORD_MAX)); // 테스트로 조건 완화
        Matcher matcher = pattern.matcher(password);
        if (!matcher.find()) {
            throw new PwPatternException("{0}~{1}자 이내 영문,숫자,특수문자를 조합해야 합니다.");
        }
    }

    private void validateCheckPw(String password, String checkPassword) throws CheckPwException {
        if (!password.equals(checkPassword)) {
            throw new CheckPwException("비밀번호가 틀렸습니다.");
        }
    }
}
