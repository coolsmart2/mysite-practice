package jyw.mysite.controller;

import jyw.mysite.domain.Member;
import jyw.mysite.domain.MemberLoginForm;
import jyw.mysite.service.MemberService;
import jyw.mysite.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") MemberLoginForm loginForm) {
        return "loginForm";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute("loginForm") MemberLoginForm loginForm, BindingResult bindingResult, HttpServletRequest request) {
        // 로그인 실패시 다시 로그인폼으로 로그인 성공시엔 홈파면으로 리다이렉트
        if (bindingResult.hasErrors()) {
            log.info("bindingResult = {}", bindingResult.getFieldError());
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
}
