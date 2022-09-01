package jpa.jpashop.service;

import jpa.jpashop.domain.Member;
import jpa.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * Sign In
     * @param member
     * @return memberId
     */
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member); // Check Duplicate
        memberRepository.save(member);

        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("This User is Duplicate User");
        }
    }

    /**
     * Checked All User
     * @return List<Member>
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * Checked One
     * @param memberId
     * @return Member
     */
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
