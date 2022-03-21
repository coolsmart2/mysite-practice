package jyw.mysite.service;

import jyw.mysite.domain.entity.Member;
import jyw.mysite.repository.MemoryMemberRepository;
import jyw.mysite.repository.MemoryPostRepository;
import org.junit.jupiter.api.Test;

class PostServiceTest {

    MemoryMemberRepository memberRepository = new MemoryMemberRepository();
    MemoryPostRepository postRepository = new MemoryPostRepository();

    @Test
    void 모든글불러오기() {
        Member member = new Member("test", "1111!");
        memberRepository.save(member);

//        for (int i = 0; i < 50; i++) {
//            String title = "Hello " + i;
//            String content = "Woooooooooorld!" + i;
//            postRepository.save(new Post(member, title, content));
//        }
//
//
//        List<Post> all = postRepository.findAll();
//        for (Post post : all) {
//            System.out.println("post.getTitle() = " + post.getTitle());
//        }
    }
    
}