package hello.itemservice.repository;

import hello.itemservice.domain.Member;
import hello.itemservice.login.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryCustomImplTest {

    @Autowired
    MemberRepository memberRepository;
    @Test
    public void 회원가입() throws Exception {

        Member member = new Member("qw","김현민", "1q~");
        //given
        Member save = memberRepository.saveMember(member);
        //when
        Member member1 = memberRepository.findByLoginId(save.getLoginId()).get();
        //then
        assertThat(save.getName()).isEqualTo(member.getName());
        assertThat(member1.getLoginId()).isEqualTo("qw");
    }
}