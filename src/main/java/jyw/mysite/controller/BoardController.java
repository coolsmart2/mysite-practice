package jyw.mysite.controller;

import jyw.mysite.domain.entity.Comment;
import jyw.mysite.domain.entity.Member;
import jyw.mysite.domain.entity.Post;
import jyw.mysite.domain.form.CommentForm;
import jyw.mysite.domain.form.PostForm;
import jyw.mysite.service.CommentService;
import jyw.mysite.service.MemberService;
import jyw.mysite.service.BoardService;
import jyw.mysite.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class BoardController {

    private final MemberService memberService;
    private final BoardService boardService;
    private final CommentService commentService;

    @GetMapping("/write")
    public String postForm(
            @ModelAttribute("form") PostForm form,
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER) Long memberId,
            Model model
    ) {
        Member findMember = memberService.findOneById(memberId);
        model.addAttribute("member", findMember);

        return "postForm";
    }

    @PostMapping("/write")
    public String newPost(
            @Validated @ModelAttribute("form") PostForm postForm,
            BindingResult bindingResult,
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER) Long memberId,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        Member findMember = memberService.findOneById(memberId);
        model.addAttribute("member", findMember);

        if (bindingResult.hasErrors()) {
            return "postForm";
        }

        Post post = new Post(findMember, LocalDateTime.now(), postForm.getTitle(), postForm.getContent());
        boardService.join(post);

        redirectAttributes.addAttribute("postId", post.getId());
        return "redirect:/post/{postId}";
    }

    @GetMapping("/{postId}/edit")
    public String editPostForm(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER) Long memberId,
            @PathVariable Long postId,
            @ModelAttribute("form") PostForm form,
            Model model
    ) {
        Member findMember = memberService.findOneById(memberId);
        model.addAttribute("member", findMember);

        Post post = boardService.findOneById(postId);
        if (post == null) {
            // ????????? ???????????? ???????????? ????????????
            return "error404";
        }

        if (!Objects.equals(findMember.getId(), post.getMember().getId())) {
            // ?????? ??????????????? ??????????????? ?????? ?????????????????? ?????????????????? ??? ?????????
//            if (boardPage != null) {
//                return "redirect:" + boardPage;
//            }
            return "redirect:/back";
        }

        form.setTitle(post.getTitle());
        form.setContent(post.getContent());

        return "postForm";
    }

    @PostMapping("/{postId}/edit")
    public String editPost(
            @PathVariable Long postId,
            @ModelAttribute("form") PostForm postForm,
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER) Long memberId,
            Model model
    ) {

        Member findMember = memberService.findOneById(memberId);
        model.addAttribute("member", findMember);

        boardService.editPost(postId, postForm);

        return "redirect:/post/{postId}";
    }

    @GetMapping("/{postId}")
    public String showPost(
            @PathVariable Long postId,
            @ModelAttribute("commentForm") CommentForm commentForm,
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER) Long memberId,
            Model model
    ) {
        // ?????? ????????? ????????? ????????? ????????? ???????????? ???????????? ???????
        // ?????? ???????????? ????????? ??????????????? ?????? ????????? ???????????? ???
        Member findMember = memberService.findOneById(memberId);
        model.addAttribute("member", findMember);

        Post findPost = boardService.findOneById(postId);
        if (findPost == null) {
            return "redirect:/back";
        }
        model.addAttribute("post", findPost);

        String[] contents = findPost.getContent().split("\n");
        model.addAttribute("contents", contents);

        if (Objects.equals(findPost.getMember().getId(), memberId)) {
            model.addAttribute("canEdit", true);
        } else {
            model.addAttribute("canEdit", false);
        }

        List<Comment> comments = commentService.getComments(findPost);
        model.addAttribute("comments", comments);

        return "post";
    }

    // DELETE Api ???????????? restful ?????? ???????????? ????????? ?????? ???????????? api??? ???????????? ???????????? ??? ???????
    @GetMapping("/{postId}/delete")
    public String deletePost(@PathVariable Long postId) {
        boardService.delete(postId);
        return "redirect:/back";
    }
}
