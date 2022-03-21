package jyw.mysite.repository;

import jyw.mysite.domain.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Long save(Member member);

    Optional<Member> findById(Long id);

    List<Member> findAll();

    List<Member> findByLoginId(String loginId);
}
