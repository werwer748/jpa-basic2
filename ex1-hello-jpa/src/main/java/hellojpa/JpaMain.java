package hellojpa;

import jakarta.persistence.*;
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
            //? 임베디드 값 타입 - 컬렉션 대안 확인
            Member member = new Member();
            member.setUsername("member1");

            // 단순 값 저장
            member.setHomeAddress(new Address("homeCity", "street", "10000"));

            // 컬렉션 저장
            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("피자");

            AddressEntity old1 = new AddressEntity("old1", "street", "10001");
            AddressEntity old2 = new AddressEntity("old2", "street", "10002");
            member.getAddressHistory().add(old1);
            member.getAddressHistory().add(old2);

            em.persist(member); // member(homeAddress 포함), favoriteFoods, addressHistory 모두 저장 => insert 6개

            em.flush();
            em.clear();

            System.out.println("======= find =======");
            Member findMember = em.find(Member.class, member.getId());// member에만 SELECT가 나간다. => 지연로딩이라서!!

            System.out.println("======= update =======");
            // homeCity -> newCity
//            findMember.getAddressHistory().setCity("newCity"); // 불변 객체여서 이렇게 하면 안됨. 업데이트문은 나간다.

            //* 올바른 방법 => 통으로 갈아껴야함.(추적이 안되기 때문에)
            Address a = findMember.getHomeAddress();

            //* 기본적인 값타입 업데이트 => 통으로 갈아낀걸 확인(새로운 객체를 만들어서 집어 넣음)
            findMember.setHomeAddress(new Address("newCity", a.getStreet(), a.getZipcode()));

            //* 컬렉션 변경 - Set
            //* favoriteFoods는 Set<String>이고 이 String 자체가 값타입이어서 이런식으로 써야함.
            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().add("한식");

            /**
             * 컬렉션 값타입이 아닌 1:다 관계로 풀어서 재실행
             */
//            findMember.getAddressHistory().remove(new AddressEntity("old1", "street", "10001")); // 삭제 안됨
            findMember.getAddressHistory().remove(old1); // 같은 객체를 넘김
            findMember.getAddressHistory().add(new AddressEntity("newCity1", "street", "10003"));

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
