package jyw.mysite.controller;

import jyw.mysite.domain.entity.Comment;
import jyw.mysite.domain.entity.Member;
import jyw.mysite.domain.entity.Post;
import jyw.mysite.domain.form.CommentForm;
import jyw.mysite.service.BoardService;
import jyw.mysite.service.CommentService;
import jyw.mysite.service.MemberService;
import jyw.mysite.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/post/{postId}")
public class CommentController {

    private final MemberService memberService;
    private final BoardService boardService;
    private final CommentService commentService;

    @PostMapping("/comment")
    public String newComment(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Long memberId,
            @PathVariable("postId") Long postId,
            @Validated @ModelAttribute("commentForm") CommentForm commentForm,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        redirectAttributes.addAttribute("postId", postId);
        if (bindingResult.hasErrors()) {
            return "redirect:/post/{postId}"; // 이거 안될 거 같음
        }
        Member findMember = memberService.findOneById(memberId);
        Post findPost = boardService.findOneById(postId);
        Comment comment = new Comment(findPost, findMember, null, commentForm.getContent(), LocalDateTime.now());
        commentService.join(comment);

        return "redirect:/post/{postId}";
    }

    @PostMapping("/comment/{commentId}")
    public String newChildComment(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Long memberId,
            @PathVariable("postId") Long postId,
            @PathVariable(required = false) Long commentId,
            @Validated @ModelAttribute("commentForm") CommentForm commentForm,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        redirectAttributes.addAttribute("postId", postId);
        if (bindingResult.hasErrors()) {
            return "redirect:/post/{postId}"; // 이거 안될 거 같음
        }
        Member findMember = memberService.findOneById(memberId);
        Post findPost = boardService.findOneById(postId);
        Comment comment;
        if (commentId == null) {
            comment = new Comment(findPost, findMember, null, commentForm.getContent(), LocalDateTime.now());
        }
        else {
            Comment parentComment = commentService.findOneById(commentId);
            comment = new Comment(findPost, findMember, parentComment, commentForm.getContent(), LocalDateTime.now());
        }
        commentService.join(comment);

        return "redirect:/post/{postId}";
    }
}
