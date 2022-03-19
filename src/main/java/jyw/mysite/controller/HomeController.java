package jyw.mysite.controller;

import jyw.mysite.domain.Member;
import jyw.mysite.service.MemberService;
import jyw.mysite.service.PostService;
import jyw.mysite.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;
    private final PostService postService;


    @GetMapping("/")
    public String home(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Long memberId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int row,
            Model model) {

        int totalPost = postService.getTotalPost(row); // 1001
        int totalPage = totalPost / PostService.MAX_PAGE_INDEX
                + (int) Math.ceil((double) (totalPost % PostService.MAX_PAGE_INDEX) /  PostService.MAX_PAGE_INDEX);
        model.addAttribute("totalPage", totalPage); // 101

        if (page > totalPage) {
            page = totalPage;
        } else if (page < 1) {
            page = 1;
        }

        int current = (page - 1) / PostService.MAX_PAGE_INDEX;
        int start = PostService.MAX_PAGE_INDEX * current;
        int end = Math.min(start + PostService.MAX_PAGE_INDEX, totalPage);

        System.out.println("n = " + current);
        System.out.println("start = " + start);
        System.out.println("end = " + end);

        List<Integer> pageList = new ArrayList<>();

        for (int i = start; i < end; i++) {
            pageList.add(i + 1);
        }
        model.addAttribute("pageList", pageList);

        postService.postsToModel(model, row, page);

        if (memberId == null) {
            return "home";
        }

        Member findMember = memberService.findOneById(memberId);
        model.addAttribute("loginId", findMember.getLoginId());

        return "homeLogin";
    }
}
