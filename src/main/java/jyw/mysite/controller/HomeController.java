package jyw.mysite.controller;

import jyw.mysite.domain.entity.Member;
import jyw.mysite.repository.PostRepository;
import jyw.mysite.service.MemberService;
import jyw.mysite.service.BoardService;
import jyw.mysite.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;
    private final BoardService boardService;

    public final static String DOMAIN = "http://localhost:8080/";

    @GetMapping("/")
    public String home(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Long memberId,
            @RequestParam(defaultValue = "1") String page,
            @RequestParam(defaultValue = "10") String row,
            HttpServletResponse response,
            Model model
    ) {

        // 이전 페이지로 돌아기기 위한 현재 페이지를 쿠키에 넣거나 교체해줌
        Cookie pageCookie = new Cookie("boardPage", String.valueOf(page));
        response.addCookie(pageCookie);

        Cookie rowCookie = new Cookie("boardRow", String.valueOf(row));
        response.addCookie(rowCookie);

        boardService.setBoardPage(model, Integer.parseInt(row), Integer.parseInt(page));
        boardService.setBoardPageIndex(model, Integer.parseInt(row), Integer.parseInt(page));

        model.addAttribute("currentPage", page);
        log.info("page = {}", page);

        if (memberId == null) {
            return "home";
        }

        Member findMember = memberService.findOneById(memberId);
        model.addAttribute("member", findMember);

        return "homeLogin";
    }

    @GetMapping("/back")
    public String backHome(
            @CookieValue(name = "boardPage", required = false) String boardPage,
            @CookieValue(name = "boardRow", required = false) String boardRow,
            RedirectAttributes redirectAttributes
    ) {
        boardService.redirectBoardPage(redirectAttributes, boardPage, boardRow);
        return "redirect:/";
    }
}
