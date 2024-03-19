package hello.itemservice.login.repository;

import hello.itemservice.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> , MemberRepositoryCustom{
    public Optional<Member> findByLoginId(String loginId);
}
