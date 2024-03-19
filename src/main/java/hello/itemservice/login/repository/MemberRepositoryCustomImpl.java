package hello.itemservice.login.repository;

import hello.itemservice.domain.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Repository
@RequiredArgsConstructor
@Transactional
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

    @Autowired
    private final EntityManager em;
    MemberRepository memberRepository;
    private static long sequence = 0L;
    @Override
    public Member saveMember(Member member) {
        member.setId(++sequence);
        log.info("save: member={}", member);
        em.persist(member);
        return member;
    }

    @Override
    public Member findByLoginID(String loginId) {
        return memberRepository.findAll().stream()
                .filter(m -> m.getLoginId().equals(loginId))
                .findFirst().get();
    }

//    @Override
//    public Member findByMemberId(Long id) {
//        return memberRepository.findAll().stream()
//                .filter(m -> m.getId().equals(id))
//                .findFirst().get();
//    }
}
