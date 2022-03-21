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

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

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
    public int getTotalPost(int row) {
        return postRepository.findAll().size();
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
    public void postsToModel(Model model, int row, int page) {
        List<Post> posts = postRepository.findPage(row, page);
        List<PostHome> postHomes = new ArrayList<>();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일 a h시 m분");
        for (Post post : posts) {
            String formatTime = post.getLocalDateTime().format(dateTimeFormatter);
            postHomes.add(new PostHome(post.getId(), post.getMember().getLoginId(), post.getTitle(), formatTime));
        }
        model.addAttribute("postHomes", postHomes);
    }
}
