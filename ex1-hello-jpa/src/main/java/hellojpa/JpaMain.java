package hellojpa;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Hibernate;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            //? JDBC, Spring JDBC Template
            Member member = new Member();
            member.setUsername("member1");
            em.persist(member);

            // flush -> commit, query 시점에 flush 작동

            List<Member> resultList = em.createNativeQuery(
                    "SELECT * FROM MEMBER WHERE member_id = 1 GROUP BY member_id"
            ).getResultList();

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
