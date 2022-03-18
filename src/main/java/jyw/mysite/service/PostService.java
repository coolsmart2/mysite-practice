package jyw.mysite.service;

import jyw.mysite.domain.Member;
import jyw.mysite.domain.Post;
import jyw.mysite.domain.PostHome;
import jyw.mysite.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post join(Post post) {
        return postRepository.save(post);
    }

    public Post findOneById(Long id) {
        Optional<Post> findPost = postRepository.findById(id);
        return findPost.orElse(null);
    }

    public Post findOneByMember(Member member) {
        List<Post> findPost = postRepository.findByMember(member);
        if (!findPost.isEmpty()) {
            return findPost.get(0);
        }
        return null;
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public void postsToModel(Model model) {
        List<Post> posts = postRepository.findAll();
        List<PostHome> postHomes = new ArrayList<>();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일 a h시 m분");
        for (Post post : posts) {
            String formatTime = post.getLocalDateTime().format(dateTimeFormatter);
            postHomes.add(new PostHome(post.getTitle(), formatTime));
        }
        model.addAttribute("postHomes", postHomes);
    }
}
