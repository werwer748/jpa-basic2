package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jpabook.jpashop.domain.Book;
import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;

public class JpaMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            //? 실전 예제

            Book book = new Book();
            book.setName("JPA");
            book.setAuthor("김영한");

            em.persist(book);

            OrderItem orderItem = new OrderItem();
            orderItem.setItem(book);
            em.persist(orderItem);

            Order order = new Order();
            order.addOrderItem(orderItem);
            em.persist(order);

            Delivery delivery = new Delivery();
            delivery.setOrder(order);
            em.persist(delivery);

            em.flush();
            em.clear();

//            Order findOrder = em.find(Order.class, order.getId());
            Delivery findDelivery = em.find(Delivery.class, delivery.getId());

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
