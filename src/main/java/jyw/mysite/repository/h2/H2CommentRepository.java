package jyw.mysite.repository.h2;

import jyw.mysite.domain.entity.Comment;
import jyw.mysite.domain.entity.Member;
import jyw.mysite.domain.entity.Post;
import jyw.mysite.repository.CommentRepository;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class H2CommentRepository implements CommentRepository {

    private final EntityManager em;

    @Override
    public Long save(Comment comment) {
        em.persist(comment);
        return comment.getId();
    }

    @Override
    public Optional<Comment> findById(Long id) {
        Comment comment = em.find(Comment.class, id);
        return Optional.ofNullable(comment);
    }

    @Override
    public List<Comment> findByPost(Post post) {
        return em.createQuery(
                "select c " +
                        "from Comment c " +
                        "where c.post = :post and c.parentComment is null", Comment.class)
                .setParameter("post", post)
                .getResultList();
    }

    @Override
    public Comment findParentComment(Comment comment) {
        return em.createQuery("select c from Comment c where c.parentComment = :comment", Comment.class)
                .setParameter("comment", comment)
                .getResultList()
                .get(0);
    }


}
