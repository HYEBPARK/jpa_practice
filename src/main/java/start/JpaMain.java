package start;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {

        // 엔티티 매니저 팩토리 - 생성
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa_practice");
        // 엔티티 매니저 - 생성
        EntityManager em = emf.createEntityManager();
        // 트랜잭션 - 획득
        EntityTransaction tx = em.getTransaction();

        try{
            tx.begin();
            logic(em);
            tx.commit();
        } catch (Exception e){
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    private static void logic(EntityManager em) {
    }
}
