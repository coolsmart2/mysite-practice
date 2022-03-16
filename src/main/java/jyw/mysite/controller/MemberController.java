package jyw.mysite.controller;

import jyw.mysite.domain.Member;
import jyw.mysite.domain.MemberLoginForm;
import jyw.mysite.domain.MemberSignUpForm;
import jyw.mysite.service.MemberService;
import jyw.mysite.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("form") MemberLoginForm loginForm) {
        return "loginForm";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute("form") MemberLoginForm loginForm, BindingResult bindingResult, HttpServletRequest request) {
        // 로그인 실패시 다시 로그인폼으로 로그인 성공시엔 홈파면으로 리다이렉트
        if (bindingResult.hasErrors()) {
//            log.info("bindingResult = {}", bindingResult.getFieldError());
            return "loginForm";
        }
        Member member = memberService.loginMember(new Member(loginForm.getLoginId(), loginForm.getPassword()));
        if (member == null) {
            bindingResult.reject("loginFail");
            log.info("bindingResult={}", bindingResult);
            return "loginForm";
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, member.getId());

        // server.servlet.session.tracking-modes=cookie
        // application.properties에다가 위의 설정을 해주지 않으면 홈화면에 "/" 말고 뒤에 세션 정보까지 같이 들어가 404 에러가 발생한다.

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return "redirect:/";
    }

    @GetMapping("/sign-up")
    public String signUpForm(@ModelAttribute("form") MemberSignUpForm signUpForm) {
        return "signUpForm";
    }

    @PostMapping("/sign-up")
    public String signUp(@Validated @ModelAttribute("form") MemberSignUpForm signUpForm, BindingResult bindingResult, Model errModel) {
        if (bindingResult.hasErrors()) {
//            log.info("bindingResult = {}", bindingResult.getFieldError());
            return "signUpForm";
        }

        String loginId = signUpForm.getLoginId();
        String password = signUpForm.getPassword();
        String checkPassword = signUpForm.getCheckPassword();

        log.info("loginId={}, password={}, checkPassword={}", loginId, password, checkPassword);

        // 아이디 중복 체크
        Member checkedLoginId = memberService.findOneByLoginId(loginId);
        if (checkedLoginId != null) {
            bindingResult.reject("loginIdDuplicate");
            log.info("bindingResult = {}", bindingResult.getGlobalError());
            log.info("loginIdDuplicate");
//            errModel.addAttribute("loginIdDuplicate", )
            return "signUpForm";
        }

        // 비밀번호 보안 체크 비밀번호 (숫자, 문자, 특수문자 포함 8 ~ 20자리 이내)
//        Pattern pattern = Pattern.compile("^.*(?=^.{8,20}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$");
        int min = 4, max = 10;
//        Pattern pattern = Pattern.compile("^.*(?=^.{" + min + "," + max + "}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$"); // 테스트로 조건 완화
        Pattern pattern = Pattern.compile("^.{" + min + "," + max + "}$"); // 테스트로 조건 완화
        Matcher matcher = pattern.matcher(password);
        if (!matcher.find()) {
            bindingResult.reject("passwordOutOfPattern", new Object[]{min, max}, null);
            log.info("passwordOutOfPattern");
            return "signUpForm";
        }

        if (!signUpForm.getPassword().equals(checkPassword)) {
            bindingResult.reject("checkPasswordFail");
            log.info("checkPasswordFail");
            return "signUpForm";
        }

        Member signUpMember = new Member(loginId, password);
        memberService.join(signUpMember);

        return "redirect:/";

    }
}
