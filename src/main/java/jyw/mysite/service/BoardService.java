package jyw.mysite.service;

import jyw.mysite.domain.entity.Member;
import jyw.mysite.domain.entity.Post;
import jyw.mysite.domain.form.PostForm;
import jyw.mysite.domain.form.PostHome;
import jyw.mysite.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final PostRepository postRepository;

    @Transactional
    public Long join(Post post) {
        return postRepository.save(post);
    }

    @Transactional
    public Post findOneById(Long id) {
        Optional<Post> findPost = postRepository.findById(id);
        return findPost.orElse(null);
    }

    @Transactional
    public Post findOneByMember(Member member) {
        List<Post> findPost = postRepository.findByMember(member);
        if (!findPost.isEmpty()) {
            return findPost.get(0);
        }
        return null;
    }

    @Transactional
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Transactional
    public void editPost(Long id, PostForm postForm) {
        Optional<Post> findPost = postRepository.findById(id);
        if (findPost.isPresent()) {
            findPost.get().setTitle(postForm.getTitle());
            findPost.get().setContent(postForm.getContent());
        }
//        else {
//            // post가 없을 경우 예외처리
//        }
    }

    @Transactional
    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    @Transactional
    public void setBoardPage(Model model, int row, int page) {
        List<Post> posts = postRepository.findPage(row, page);
        List<PostHome> postHomes = new ArrayList<>();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일 a h시 m분");
        for (Post post : posts) {
            String formatTime = post.getLocalDateTime().format(dateTimeFormatter);
            postHomes.add(new PostHome(post.getId(), post.getMember().getLoginId(), post.getTitle(), post.getComments().size(), formatTime));
        }
        model.addAttribute("postHomes", postHomes);
    }

    @Transactional
    public void setBoardPageIndex(Model model, int row, int page) {

        // 머리 안 돌아가서 대충 짠 코드 리팩토링 필요함!!!
        int totalPost = postRepository.findAll().size(); // 1012
        int totalPage = totalPost / PostRepository.MAX_PAGE_INDEX
                + (int) Math.ceil((double) (totalPost % PostRepository.MAX_PAGE_INDEX) /  PostRepository.MAX_PAGE_INDEX);

        if (page > totalPage) {
            page = totalPage;
        } else if (page < 1) {
            page = 1;
        }
        int current = (page - 1) / PostRepository.MAX_PAGE_INDEX;
        int start = PostRepository.MAX_PAGE_INDEX * current;
        int end = Math.min(start + PostRepository.MAX_PAGE_INDEX, totalPage);

        List<Integer> pageList = new ArrayList<>();
        for (int i = start; i < end; i++) {
            pageList.add(i + 1);
        }

        // 게시판에 아무 글도 없을 경우 디폴트 값 입력
        if (pageList.size() == 0) {
            pageList.add(1);
            totalPage = 1;
        }
        model.addAttribute("totalPage", totalPage); // 101
        model.addAttribute("pageList", pageList);
    }

    // 여기에 넣어도 될까
    public void redirectBoardPage(
            RedirectAttributes redirectAttributes,
            String boardPage,
            String boardRow
    ) {
        if (boardPage.equals("")) {
            boardPage = "1";
        }
        if (boardRow.equals("")) {
            boardRow = "10";
        }
        redirectAttributes.addAttribute("page", boardPage);
        redirectAttributes.addAttribute("row", boardRow);
    }
}
