package jpabook.jpashop.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
//* 이렇게 인덱스 걸려있는 것도 써주면 개발하기 편함
//@Table(indexes = {
//        @Index(name = "aaa", columnList = "''")
//})
public class Member extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    // 이렇게 메타데이터를 기입해두면 작업할 때 편함
    @Column(length = 50)
    private String name;

    @Embedded // 생략해도 되지만 명확하게 표기해 줌
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
