package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jpabook.jpashop.domain.*;

public class JpaMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            //? jpql 타입 - 엔티티 타입
            Book book = new Book();
            book.setName("JPA");
            book.setAuthor("김영한");

            em.persist(book);

            em.createQuery("select i from Item i where type(i) = Book", Item.class)
                    .getResultList();

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
