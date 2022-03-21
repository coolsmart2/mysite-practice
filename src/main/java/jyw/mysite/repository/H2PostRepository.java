package jyw.mysite.repository;

import jyw.mysite.domain.entity.Member;
import jyw.mysite.domain.entity.Post;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class H2PostRepository implements PostRepository {

    private final EntityManager em;

    @Override
    public Long save(Post post) {
        em.persist(post);
        return post.getId();
    }

    @Override
    public Optional<Post> findById(Long id) {
        Post post = em.find(Post.class, id);
        return Optional.ofNullable(post);
    }

    @Override
    public List<Post> findAll() {
        return em.createQuery("select p from Post p", Post.class)
                .getResultList();
    }

    @Override
    public List<Post> findByMember(Member member) {
        return em.createQuery("select p from Post p where p.member = :member", Post.class)
                .setParameter("member", member)
                .getResultList();
    }

    @Override
    public List<Post> findPage(int row, int page) {
        return em.createQuery("select p from Post p order by p.id desc", Post.class)
                .setFirstResult(row * (page - 1))
                .setMaxResults(PostRepository.MAX_PAGE_INDEX)
                .getResultList();
    }

    @Override
    public void deleteById(Long id) {
        Post findPost = em.find(Post.class, id);
        em.remove(findPost);
    }
}
