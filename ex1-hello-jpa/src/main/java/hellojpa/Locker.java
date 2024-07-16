package hellojpa;

import jakarta.persistence.*;

@Entity
public class Locker {
    @Id
    @GeneratedValue
    @Column(name = "LOCKER_ID")
    private Long id;

    private String name;

//    // 읽기전용이 된다.
//    @OneToOne(mappedBy = "locker")
//    private Member member;
}
