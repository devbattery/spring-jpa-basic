package hellojpa;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

//@Entity
public class Locker {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    // 읽기 전용
    @OneToOne(mappedBy = "locker")
    private Member member;

    public Locker() {
    }

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

}
