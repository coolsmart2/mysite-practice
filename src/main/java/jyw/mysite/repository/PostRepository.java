package jyw.mysite.repository;

import jyw.mysite.domain.Member;
import jyw.mysite.domain.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Post save(Post post);

    Optional<Post> findById(Long id);

    List<Post> findAll();

    List<Post> findByMember(Member member);

    List<Post> findPage(int row, int page);

    void remove(Post post);
}
