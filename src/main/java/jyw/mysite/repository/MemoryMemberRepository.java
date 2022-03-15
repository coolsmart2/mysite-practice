package jyw.mysite.repository;

import jyw.mysite.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class MemoryMemberRepository implements MemberRepository{

    private Map<Long, Member> members = new HashMap<>();
    private Long sequence = 0L;

    @Override
    public Member save(Member member) {
        members.put(++sequence, member);
        member.setId(sequence);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional. members.get(id)
    }

    @Override
    public List<Member> findAll() {
        return null;
    }

    @Override
    public List<Member> findByLoginId(String LoginId) {
        return null;
    }
}
