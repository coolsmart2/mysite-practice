package jyw.mysite.controller;

import jyw.mysite.domain.Member;
import jyw.mysite.domain.MemberLoginForm;
import jyw.mysite.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;

    @GetMapping
    public String loginForm() {
        return "loginForm";
    }

    @PostMapping
    public String login(@ModelAttribute("form") MemberLoginForm form) {
        // 로그인 실패시 다시 로그인폼으로 로그인 성공시엔 홈파면으로 리다이렉트

        Member member = new Member(form.getLoginId(), form.getPassword());
        if (loginService.loginMember(member)) {
            return "redirect:/";
        }
        return "loginForm";
    }

}
