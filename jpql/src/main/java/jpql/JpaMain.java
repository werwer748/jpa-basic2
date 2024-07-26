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
            //? jpql case
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("teamA");
            member.setAge(10);
            member.changeTeam(team);
            member.setType(MemberType.ADMIN);
            em.persist(member);

            Member member2 = new Member();
            member2.setUsername("관리자");
            member2.setAge(20);
            member2.changeTeam(team);
            member2.setType(MemberType.ADMIN);
            em.persist(member2);

            em.flush();
            em.clear();

            /**
             * 사용자 정의 함수 호출
             */
//            String query = "select function('group_concat', m.username) " +
            String query = "select group_concat(m.username) " + // 하이버네이트에서 이렇게 쓸 수 있도록 지원해 줌
                    "from Member m";
            List<String> result = em.createQuery(query, String.class)
                    .getResultList();

            for (String s : result) {
                System.out.println("s = " + s);
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
