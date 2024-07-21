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
            Movie movie = new Movie();
            movie.setDirector("Bong");
            movie.setActor("Song");
            movie.setName("기생충");
            movie.setPrice(10000);

            em.persist(movie);

            em.flush();
            em.clear();

            Item findItem = em.find(Item.class, movie.getId());
            System.out.println("findItem = " + findItem);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

}
