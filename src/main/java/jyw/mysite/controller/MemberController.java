package jyw.mysite.controller;

import jyw.mysite.domain.Member;
import jyw.mysite.domain.MemberLoginForm;
import jyw.mysite.domain.MemberSignUpForm;
import jyw.mysite.exception.CheckPwException;
import jyw.mysite.exception.LoginIdException;
import jyw.mysite.exception.PwPatternException;
import jyw.mysite.repository.MemberConst;
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
    public String loginForm(@ModelAttribute("form") MemberLoginForm loginForm) {
        return "loginForm";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute("form") MemberLoginForm loginForm, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "loginForm";
        }
        Member member = memberService.loginMember(new Member(loginForm.getLoginId(), loginForm.getPassword()));
        if (member == null) {
            bindingResult.reject("loginFail");
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
    public String signUp(@Validated @ModelAttribute("form") MemberSignUpForm signUpForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signUpForm";
        }
        try {
            Member signUpMember = new Member(signUpForm.getLoginId(), signUpForm.getPassword());
            memberService.joinAndSignUp(signUpMember, signUpForm.getCheckPassword());
            return "redirect:/";
        } catch (LoginIdException e) {
            bindingResult.reject("loginIdDuplicate");
        } catch (PwPatternException e) {
            bindingResult.reject("passwordOutOfPattern", new Object[]{MemberConst.PASSWORD_MIN, MemberConst.PASSWORD_MAX}, null);
        } catch (CheckPwException e) {
            bindingResult.reject("checkPasswordFail");
        }
        return "signUpForm";
    }
}
