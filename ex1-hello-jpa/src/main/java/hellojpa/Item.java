package hellojpa;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
/**
 * @DiscriminatorColumn: DTYPE을 생성해 준다.
 * name 속성: 컬럼명을 지정할 수 있다.
 * SINGLE_TABLE 전략일 경우 생략도 가능(DTYPE 컬럼 자동 생성)
 */
@DiscriminatorColumn
public abstract class Item {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private int price;

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
