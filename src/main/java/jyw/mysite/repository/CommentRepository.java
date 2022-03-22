package jyw.mysite.repository;

import jyw.mysite.domain.entity.Comment;
import jyw.mysite.domain.entity.Post;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    Long save(Comment comment);

    Optional<Comment> findById(Long id);

    List<Comment> findByPost(Post post);

    Comment findParentComment(Comment comment);

}
