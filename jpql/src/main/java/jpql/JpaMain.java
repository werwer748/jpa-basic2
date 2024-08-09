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
            //? jpql 벌크 연산
            Team teamA = new Team();
            teamA.setName("팀A");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("팀B");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("회원1");
            member1.setAge(10);
            member1.changeTeam(teamA);
            member1.setType(MemberType.ADMIN);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("회원2");
            member2.setAge(20);
            member2.changeTeam(teamA);
            member2.setType(MemberType.ADMIN);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("회원3");
            member3.setAge(30);
            member3.changeTeam(teamB);
            member3.setType(MemberType.ADMIN);
            em.persist(member3);

            /**
             * 벌크 연산 - executeUpdate
             * .excuteUpdate 쿼리가 나가면 자동으로 영속성 컨텍스트를 flush한다.
             * 이전 쿼리를 flush하고 해당 쿼리가 실행되는 것.
             *
             * 현재 영속성 컨텍스트에는 회원나이가 반영되어 있지 않음
             */
            int resultCount = em.createQuery("update Member m set m.age = 20") // 자동 flush 후 실행됨
                    .executeUpdate();

            System.out.println("resultCount = " + resultCount);

            // 다시 DB에서 조회 -> 근데 영속성 컨텍스트에는 반영이 안되어 있음
            Member findMember1 = em.find(Member.class, member1.getId());
            Member findMember2 = em.find(Member.class, member2.getId());
            Member findMember3 = em.find(Member.class, member3.getId());
            System.out.println("member1 = " + findMember1.getAge()); // 10
            System.out.println("member2 = " + findMember2.getAge()); // 20
            System.out.println("member3 = " + findMember3.getAge()); // 30

            em.clear(); // 영속성 컨텍스트 초기화

            // 초기화 후 재조회 -> 업데이트가 반영됨
            Member findMember1Re = em.find(Member.class, member1.getId());
            Member findMember2Re = em.find(Member.class, member2.getId());
            Member findMember3Re = em.find(Member.class, member3.getId());
            System.out.println("member1Re = " + findMember1Re.getAge()); // 20
            System.out.println("member2Re = " + findMember2Re.getAge()); // 20
            System.out.println("member3Re = " + findMember3Re.getAge()); // 20

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
