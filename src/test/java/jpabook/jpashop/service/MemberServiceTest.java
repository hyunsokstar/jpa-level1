package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.fail;


@RunWith(SpringRunner.class) // 이거 뭐지?
@SpringBootTest
@Transactional // erg 의미는?
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;


    @Test
    public void memberSaveTest() {
        // step1: 회원 가입후 id 반환 받기
        Member member = new Member();
        member.setName("hyunsok");
        Long savedMemberId = memberService.join(member);
        System.out.println("savedMemberId : "+ savedMemberId);

        // step2 반환 받은 id로 회원 한명 조회 해서 멤버 객체 리턴 받기
        Member findMember = memberRepository.findOne(savedMemberId);

        //  a 와 b 가 같은 객체임을 확인
        Assertions.assertEquals(member, findMember);
    }

    // 이름이 hyun인 두 멤버 객체 생성후 둘다 회원 가입 함수에 사용
    @Test
    public void duplicateMemberRegisterIsNotAllowed() throws Exception {
        Member member1 = new Member();
        member1.setName("hyun");
        Member member2 = new Member();
        member2.setName("hyun");

        memberService.join(member1);

        try {
            memberService.join(member2); // 여기에서 에러가 발생해야 함
        } catch (IllegalStateException e) {
            // 예외가 발생할 경우 메세지 출력
            System.out.println("중복 회원 가입 에러 발생!!");
            return;
        }
        // 예외가 발생하지 않았으면 test 실패
        fail("예외가 발생해야 한다.");
    }


}
