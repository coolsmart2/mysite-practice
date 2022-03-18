package jyw.mysite;

import jyw.mysite.repository.MemberRepository;
import jyw.mysite.repository.MemoryMemberRepository;
import jyw.mysite.repository.MemoryPostRepository;
import jyw.mysite.repository.PostRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public PostRepository postRepository() {
        return new MemoryPostRepository();
    }
}
