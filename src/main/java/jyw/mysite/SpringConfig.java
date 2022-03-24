package jyw.mysite;

import jyw.mysite.repository.*;
import jyw.mysite.repository.h2.H2CommentRepository;
import jyw.mysite.repository.h2.H2MemberRepository;
import jyw.mysite.repository.h2.H2PostRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class SpringConfig {

    @PersistenceContext
    EntityManager em;

    @Bean
    public MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
        return new H2MemberRepository(em);
    }

    @Bean
    public PostRepository postRepository() {
//        return new MemoryPostRepository();
        return new H2PostRepository(em);
    }

    @Bean
    public CommentRepository commentRepository() {
        return new H2CommentRepository(em);
    }
}
