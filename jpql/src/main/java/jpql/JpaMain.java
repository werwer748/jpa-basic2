package jpql;

import jakarta.persistence.*;

import java.util.List;

public class JpaMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            //? JPQL
            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            em.flush();
            em.clear();

            /**
             * 스칼라 타입 프로젝션 - new 명령어(dto)로 조회
             * 단순 값을 DTO로 바로 조회
             * 패키지명을 포함한 전체 클래스 명 입력
             * 순서와 타입이 일치하는 생성자 필요
             */
            List<MemberDto> resultList = em.createQuery("select new jpql.MemberDto(m.username, m.age) from Member m", MemberDto.class)
                    .getResultList();

            for (MemberDto memberDto : resultList) {
                System.out.println("memberDto = " + memberDto.getUsername());
                System.out.println("memberDto = " + memberDto.getAge());
            }

            System.out.println("===================");
            tx.commit();
        } catch (Exception e) {
            System.out.println("ROLLBACK EXCEPTION");
            e.printStackTrace();
            tx.rollback();
        } finally {
            // 작업이 끝난 후 자원 반납
            em.close();
        }

        emf.close();
    }

}
