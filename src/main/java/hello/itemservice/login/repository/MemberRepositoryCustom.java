package hello.itemservice.login.repository;

import hello.itemservice.domain.Member;

public interface MemberRepositoryCustom{
    public Member saveMember(Member member);
    public Member findByLoginID(String loginId);
    //public Member findByMemberId(Long id);
}
