package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Test
    public void 회원가입() throws Exception {

        //given
        Member member = new Member();
        member.setName("홍길동");

        //when
        Long savedId = memberService.join(member);

        //then
        em.flush();
        assertEquals(member, memberRepository.findOne(savedId));

    }
    
    @Test
    public void 중복회원예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("Hi");

        Member member2 = new Member();
        member2.setName("Hi");

        //when
        memberService.join(member1);
        try {
            memberService.join(member2);
        }catch(IllegalStateException e){
            return;
        }

        //then
        fail("예외가 발생해야한다.");
        
    }

}