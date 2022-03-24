package jyw.mysite.controller;

import jyw.mysite.domain.entity.Member;
import jyw.mysite.domain.form.MemberLoginForm;
import jyw.mysite.domain.form.MemberSignUpForm;
import jyw.mysite.exception.CheckPwException;
import jyw.mysite.exception.LoginIdException;
import jyw.mysite.exception.PwPatternException;
import jyw.mysite.repository.MemberConst;
import jyw.mysite.service.BoardService;
import jyw.mysite.service.MemberService;
import jyw.mysite.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final BoardService boardService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("form") MemberLoginForm loginForm) {
        return "loginForm";
    }

    @PostMapping("/login")
    public String login(
            @Validated @ModelAttribute("form") MemberLoginForm form,
            BindingResult bindingResult,
            @RequestParam(defaultValue = "/") String redirectURL,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes,
            @CookieValue(name = "boardPage", required = false) String boardPage,
            @CookieValue(name = "boardRow", required = false) String boardRow
    ) {
        if (bindingResult.hasErrors()) {
            return "loginForm";
        }
        Member member = memberService.checkLogin(new Member(form.getLoginId(), form.getPassword()));
        if (member == null) {
            bindingResult.reject("loginFail");
            return "loginForm";
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, member.getId());

        boardService.redirectBoardPage(redirectAttributes, boardPage, boardRow);
        // server.servlet.session.tracking-modes=cookie
        // application.properties에다가 위의 설정을 해주지 않으면 홈화면에 "/" 말고 뒤에 세션 정보까지 같이 들어가 404 에러가 발생한다.
        return "redirect:" + redirectURL/* + "?r=True"*/;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/back";
    }

    @GetMapping("/sign-up")
    public String signUpForm(@ModelAttribute("form") MemberSignUpForm form) {
        return "signUpForm";
    }

    @PostMapping("/sign-up")
    public String signUp(
            @Validated @ModelAttribute("form") MemberSignUpForm form,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "signUpForm";
        }
        try {
            Member signUpMember = new Member(form.getLoginId(), form.getPassword());
            memberService.joinAndValidate(signUpMember, form.getCheckPassword());
            return "redirect:/back";
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
