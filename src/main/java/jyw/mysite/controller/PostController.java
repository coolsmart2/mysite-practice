package jyw.mysite.controller;

import jyw.mysite.domain.Member;
import jyw.mysite.domain.Post;
import jyw.mysite.domain.PostForm;
import jyw.mysite.service.MemberService;
import jyw.mysite.service.PostService;
import jyw.mysite.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PostController {

    private final MemberService memberService;
    private final PostService postService;

    @GetMapping("/write")
    public String postForm(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER) Long memberId,
            @ModelAttribute("form") PostForm postForm,
            Model model) {
        Member findMember = memberService.findOneById(memberId);
        model.addAttribute("loginId", findMember.getLoginId());

        return "postForm";
    }

    @PostMapping("/write")
    public String newPost(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER) Long memberId,
            @Validated @ModelAttribute("form") PostForm postForm,
            BindingResult bindingResult,
            Model model) {

        Member findMember = memberService.findOneById(memberId);
        model.addAttribute("loginId", findMember.getLoginId());

        if (bindingResult.hasErrors()) {
            return "postForm";
        }

        Member postMember = memberService.findOneById(memberId);
        Post post = new Post(postMember, LocalDateTime.now(), postForm.getTitle(), postForm.getContent());
        postService.join(post);

        return "redirect:/";
    }

    @GetMapping("/post/{postId}")
    public String showPost(
            @PathVariable Long postId,
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER) Long memberId,
            Model model) {

        // 매번 화면에 사용자 정보를 이렇게 노가다로 구현해야 될까?
        Member findMember = memberService.findOneById(memberId);
        model.addAttribute("loginId", findMember.getLoginId());


        Post findPost = postService.findOneById(postId);
        model.addAttribute("title", findPost.getTitle());

        String[] contents = findPost.getContent().split("\n");
        model.addAttribute("contents", contents);

        return "post";
    }
}
