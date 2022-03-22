package jyw.mysite.service;

import jyw.mysite.domain.entity.Comment;
import jyw.mysite.domain.entity.Post;
import jyw.mysite.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public Long join(Comment comment) {
        return commentRepository.save(comment);
    }

    @Transactional
    public Comment findOneById(Long id) {
        Optional<Comment> findComment = commentRepository.findById(id);
        return findComment.orElse(null);
    }

    @Transactional
    public List<Comment> getComments(Post post) {
        return commentRepository.findByPost(post);
    }

    @Transactional
    public Comment getParentComment(Comment comment) {
        return commentRepository.findParentComment(comment);
    }
}
