package jyw.mysite.controller;

import jyw.mysite.domain.Member;
import jyw.mysite.service.MemberService;
import jyw.mysite.service.PostService;
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
    private final PostService postService;

    @GetMapping("/")
    public String home(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Long memberId, Model model) {

        postService.postsToModel(model);

        if (memberId == null) {
            return "home";
        }

        Member findMember = memberService.findOneById(memberId);
        model.addAttribute("loginId", findMember.getLoginId());

        return "homeLogin";
    }
}
