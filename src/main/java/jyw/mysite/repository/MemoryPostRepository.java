package jyw.mysite.repository;

import jyw.mysite.domain.Member;
import jyw.mysite.domain.Post;

import java.util.*;

public class MemoryPostRepository implements PostRepository {

    private final Map<Long, Post> posts = new HashMap<>();
    private Long sequence = 0L;

    @Override
    public Post save(Post post) {
        posts.put(++sequence, post);
        post.setId(sequence);
        return post;
    }

    @Override
    public Optional<Post> findById(Long id) {
        return Optional.ofNullable(posts.get(id));
    }

    @Override
    public List<Post> findAll() {
        return new ArrayList<>(posts.values());
    }

    @Override
    public List<Post> findByMember(Member member) {
        List<Post> findPost = new ArrayList<>();
        for (Post post : posts.values()) {
            if (post.getMember() == member) {
                findPost.add(post);
            }
        }
        return findPost;
    }

    public void clear() {
        posts.clear();
    }
}