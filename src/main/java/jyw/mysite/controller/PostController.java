package jyw.mysite.controller;

import jyw.mysite.domain.Member;
import jyw.mysite.domain.Post;
import jyw.mysite.domain.PostForm;
import jyw.mysite.domain.PostHome;
import jyw.mysite.service.MemberService;
import jyw.mysite.service.PostService;
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
import java.time.LocalDateTime;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PostController {

    private final MemberService memberService;
    private final PostService postService;

    @GetMapping("/write")
    public String postForm(@ModelAttribute("form") PostForm postForm) {
        return "postForm";
    }

    @PostMapping("/write")
    public String newPost(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER) Long memberId,
            @Validated @ModelAttribute("form") PostForm postForm,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "postForm";
        }

        Member postMember = memberService.findOneById(memberId);
        Post post = new Post(postMember, LocalDateTime.now(), postForm.getTitle(), postForm.getContent());
        postService.join(post);

        return "redirect:/";
    }
}
