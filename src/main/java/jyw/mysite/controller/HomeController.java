package jyw.mysite.controller;

import jyw.mysite.domain.Member;
import jyw.mysite.service.MemberService;
import jyw.mysite.session.SessionConst;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;

    @GetMapping("/")
    public String home(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Long memberId, Model model) {

        if (memberId == null) {
            return "home";
        }

        // 이렇게 꺼내오면 속도 느려지는거 아닌가?
        Member member = memberService.findOneById(memberId);
        model.addAttribute("member", member);

        return "homeLogin";
    }
}
