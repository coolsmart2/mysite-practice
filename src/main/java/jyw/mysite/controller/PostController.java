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

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final MemberService memberService;
    private final PostService postService;

    @GetMapping("/write")
    public String postForm(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER) Long memberId,
            @ModelAttribute("form") PostForm postForm,
            Model model
    ) {
        Member findMember = memberService.findOneById(memberId);
        model.addAttribute("member", findMember);

        return "postForm";
    }

    @PostMapping("/write")
    public String newPost(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER) Long memberId,
            @Validated @ModelAttribute("form") PostForm postForm,
            BindingResult bindingResult,
            Model model
    ) {

        Member findMember = memberService.findOneById(memberId);
        model.addAttribute("member", findMember);

        if (bindingResult.hasErrors()) {
            return "postForm";
        }

        Post post = new Post(findMember, LocalDateTime.now(), postForm.getTitle(), postForm.getContent());
        postService.join(post);

        return "redirect:/post/" + post.getId();
    }

    @GetMapping("/{postId}/edit")
    public String editPostForm(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER) Long memberId,
            @PathVariable Long postId,
            @ModelAttribute("form") PostForm postForm,
            Model model
    ) {
        Member findMember = memberService.findOneById(memberId);
        model.addAttribute("member", findMember);

        Post post = postService.findOneById(postId);
        if (post == null) {
            // 나중에 예외처리 페이지로 이동시킴
            return "error404";
        }
        postForm.setTitle(post.getTitle());
        postForm.setContent(post.getContent());

        return "postForm";
    }

    @PostMapping("/{postId}/edit")
    public String editPost(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER) Long memberId,
            @PathVariable Long postId,
            @ModelAttribute("form") PostForm postForm,
            BindingResult bindingResult,
            Model model
    ) {

        Member findMember = memberService.findOneById(memberId);
        model.addAttribute("member", findMember);


        Post post = postService.findOneById(postId);
        if (post == null) {
            // 나중에 예외처리 페이지로 이동시킴
            return "error404";
        }

        if (!Objects.equals(findMember.getId(), post.getMember().getId())) {
            bindingResult.reject("editError");
            // 일단 홈화면으로 리다이렉트 향후 이전페이지로 리다이렉트할 수 있도록
            return "redirect:/";
        }

        post.setContent(postForm.getContent());

        return "redirect:/post/" + postId;
    }

    @GetMapping("/{postId}")
    public String showPost(
            @PathVariable Long postId,
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER) Long memberId,
            Model model
    ) {
        // 매번 화면에 사용자 정보를 이렇게 노가다로 구현해야 될까?
        Member findMember = memberService.findOneById(memberId);
        model.addAttribute("member", findMember);

        Post findPost = postService.findOneById(postId);
        model.addAttribute("post", findPost);

        String[] contents = findPost.getContent().split("\n");
        model.addAttribute("contents", contents);

        if (Objects.equals(findPost.getMember().getId(), memberId)) {
            model.addAttribute("canEdit", true);
        } else {
            model.addAttribute("canEdit", false);
        }

        return "post";
    }
}
