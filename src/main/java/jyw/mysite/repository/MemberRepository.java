package jyw.mysite.repository;

import jyw.mysite.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    public Member save(Member member);

    public Optional<Member> findById(Long id);

    public List<Member> findAll();

    public List<Member> findByLoginId(String LoginId);
}
