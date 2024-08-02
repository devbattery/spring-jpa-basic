package hellojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.util.List;
import java.util.Set;
import org.hibernate.Hibernate;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            Member member = new Member();
            member.setUsername("member1");
            member.setHomeAddress(new Address("homeCity", "street1", "10000"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("피자");

            member.getAddressHistory().add(new AddressEntity("old1", "street1", "10000"));
            member.getAddressHistory().add(new AddressEntity("old2", "street1", "10000"));

            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("=============== START ===============");
            Member findMember = em.find(Member.class, member.getId());
            List<AddressEntity> addressHistory = findMember.getAddressHistory();
            for (AddressEntity address : addressHistory) {
                System.out.println("address.getCity() = " + address.getAddress().getCity());
            }

            Set<String> favoriteFoods = findMember.getFavoriteFoods();
            for (String favoriteFood : favoriteFoods) {
                System.out.println("favoriteFood = " + favoriteFood);
            }

            // homeCity -> newCity
//            findMember.getHomeAddress().setCity("newCity");
            Address a = findMember.getHomeAddress();
            findMember.setHomeAddress(new Address("newCity", a.getStreet(), a.getZipcode()));

            // 치킨 -> 한식
            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().add("한식");

            // equals 해시코드 넣은 거 중요
            findMember.getAddressHistory().remove(new AddressEntity("old1", "street1", "10000"));
            findMember.getAddressHistory().add(new AddressEntity("newCity1", "street1", "10000"));

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }

}
