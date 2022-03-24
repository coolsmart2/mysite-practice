package jyw.mysite.repository.memory;

import jyw.mysite.domain.entity.Member;
import jyw.mysite.repository.MemberRepository;

import java.util.*;

//@Repository
public class MemoryMemberRepository implements MemberRepository {

    private final Map<Long, Member> members = new HashMap<>();
    private Long sequence = 0L;

    @Override
    public Long save(Member member) {
        members.put(++sequence, member);
        member.setId(sequence);
        return member.getId();
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(members.get(id));
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(members.values());
    }

    @Override
    public List<Member> findByLoginId(String LoginId) {
        List<Member> findMember = new ArrayList<>();
        for (Member member : members.values()) {
            if (member.getLoginId().equals(LoginId)) {
                findMember.add(member);
            }
        }
        return findMember;
    }

    public void clear() {
        members.clear();
    }
}
