package hellojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            // 영속
            Member member = em.find(Member.class, 150L);
            member.setName("AAAAA");

            em.detach(member); // 비영속 상태 만들기
//            em.clear(); // 1차 캐시 완전히 지우기
            Member member2 = em.find(Member.class, 150L);

            System.out.println("=========================");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

}
