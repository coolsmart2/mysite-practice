package jyw.mysite.repository;

import jyw.mysite.domain.entity.Member;
import jyw.mysite.domain.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

    int MAX_PAGE_INDEX = 10;

    Long save(Post post);

    Optional<Post> findById(Long id);

    List<Post> findAll();

    List<Post> findByMember(Member member);

    List<Post> findPage(int row, int page);

    void deleteById(Long id);
}
