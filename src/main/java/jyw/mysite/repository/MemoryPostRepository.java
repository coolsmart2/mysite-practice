package jyw.mysite.repository;

import jyw.mysite.domain.entity.Member;
import jyw.mysite.domain.entity.Post;

import java.util.*;

public class MemoryPostRepository implements PostRepository {

    private final Map<Long, Post> posts = new HashMap<>();
    private Long sequence = 0L;

    @Override
    public Long save(Post post) {
        posts.put(++sequence, post);
        post.setId(sequence);
        return post.getId();
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

    @Override
    public List<Post> findPage(int row, int page) {
        List<Post> allPost = findAllByRecent();
        int start = row * (page - 1);
        int end = Math.min(start + row, allPost.size());
        return allPost.subList(start, end);
    }

    public List<Post> findAllByRecent() {
        List<Post> allPost = new ArrayList<>(posts.values());
        allPost.sort(new Comparator<Post>() {
            @Override
            public int compare(Post o1, Post o2) {
                return (int) (o2.getId() - o1.getId());
            }
        });
        return allPost;
    }

    @Override
    public void deleteById(Long id) {
        posts.remove(id);
    }

    public void clear() {
        posts.clear();
    }
}
