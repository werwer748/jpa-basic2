package hellojpa;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class MemberProduct {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    //? - 중간에 테이블에서 엔티티로 승격시킴으로써 필드들을 추가해 사용성을 높일 수 있다
    private int count;
    private int price;

    private LocalDateTime orderDatetime;
}
