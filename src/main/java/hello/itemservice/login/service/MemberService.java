package hello.itemservice.login.service;

import hello.itemservice.domain.Member;
import hello.itemservice.login.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void saveMembers(Member member) {
        Member save = memberRepository.save(member);
    }
}
