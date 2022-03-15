package jyw.mysite.controller;

import jyw.mysite.domain.Member;
import jyw.mysite.domain.MemberLoginForm;
import jyw.mysite.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
    public String loginForm(@ModelAttribute("loginForm") MemberLoginForm loginForm) {
        return "loginForm";
    }

    @PostMapping
    public String login(@Validated @ModelAttribute("loginForm") MemberLoginForm loginForm, BindingResult bindingResult) {
        // 로그인 실패시 다시 로그인폼으로 로그인 성공시엔 홈파면으로 리다이렉트
        if (bindingResult.hasErrors()) {
            log.info("bindingResult = {}", bindingResult.getFieldError());
            return "loginForm";
        }
        Member member = loginService.loginMember(new Member(loginForm.getLoginId(), loginForm.getPassword()));
        if (member == null) {
            bindingResult.reject("loginFail");
            log.info("bindingResult={}", bindingResult);
            return "loginForm";
        }
        return "redirect:/";
    }

}
