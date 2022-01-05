package start;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;


public class JpaMain {
    public static void main(String[] args) {

        // 1. 엔티티 매니저 팩토리 생성
        // persistence.xml 의 영속성 유닛을 찾아서 엔티티 매니저 팩토리를 생성
        // 영속성 유닛 ? 응용프로그램의 EntityManager 인스턴스에 의해 관리되는 모든 엔티티 클래스 집합을 정의한다.
        // persistence.xml 설정 정보를 읽어 JPA 를 동작시키기 위한 객체를 만들고
        // JPA 구현체에 따라서는 데이터베이스 커넥션 풀을 생성한다
        // 커넥션 풀 ? 1. 사용자가 DB를 사용하기 위하여 Connection을 요청한다.
                    // 2. Connection Pool에서 사용되지 않고 있는 Connection 객체를 제공한다.
                    // 3. 사용자가 Connection 객체를 사용 완료하면 pool로 반환한다.
        // 따라서 생성 비용이 아주 크다. => 애플리케이션 전체에서 딱 한 번만 생성하고 공유해서 사용해야한다.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa_practice");

        // 2. 엔티티 매니저 생성
        // 엔티티 매니저 팩토리에서 엔티티 매니저 생성
        // 엔티티를 데이터베이스에 CRUD 가능
        // 데이터베이스 커넥션과 밀접한 관계가 있으므로 스레드간에 공유나 재사용은하면 안 된다.
        EntityManager em = emf.createEntityManager();



        // jpa 사용시 항상 트랜잭션 안에서 데이터를 변경해야한다.
        // 트랜잭션 없이 데이터를 변경하면 예외가 발생한다.
        // 트랜잭션 획득
        EntityTransaction tx = em.getTransaction();
        try{
            tx.begin(); // 트랜잭션 시작
            logic(em); // 비즈니스 로직 실행
            tx.commit(); // 트랜잭션 커밋
        } catch (Exception e){
            tx.rollback(); // 예외 발생 시 트랜잭션 롤백
        } finally {
            em.close(); // 반드시 엔티티 매니저 종료
        }
        emf.close(); // 반드시 엔티티 팩토리 종료
    }

    private static void logic(EntityManager em) {

        String id = "id1";
        Member member = new Member();
        member.setId(id);
        member.setUsername("혭");
        member.setAge(2);

        // Create
        // INSERT INTO MEMBER (ID, NAME, AGE) VALUES ('id1', 혭', 2)
        em.persist(member);
        // Update
        member.setAge(20);
        //한 건 조회
        Member findMember = em.find(Member.class, id);
        System.out.println("이름: " + findMember.getUsername() +" 나이: "+ findMember.getAge());

        // 목록 조회
        // JPQL(Java Persistence Query Lang)
        // JPQL은 엔티티 객체를 대상으로 쿼리한다. => 클래스와 필드를 대상으로
        List<Member> members = em.createQuery("select m from Member m", Member.class) // Member는 회원 엔테테 객체임 ! 테이블 아님
                .getResultList();
        System.out.println("배열 크기 : " + members.size());

        // delete
        em.remove(member);
    }
}


//public class JpaMain {
//    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa_practice");
//
//    public static void main(String[] args) {
//        Member member = createMember("memberA", "회원1");
//        member.setUsername("회원명 변경");
//        mergeMember(member);
//    }
//
//    // 영속성 컨텍스트 1 시작
//    static Member createMember(String id, String username) {
//        EntityManager em1 = emf.createEntityManager();
//        EntityTransaction tx1 = em1.getTransaction();
//        tx1.begin();
//
//        Member member = new Member();
//        member.setId(id);
//        member.setUsername(username);
//        em1.persist(member);
//        tx1.commit();
//        System.out.println("member = " + member.getUsername()); // member = 회원1
//        em1.close(); // 영속성 컨텍스트 1 종료
//        // member 엔티티는 준영속상태 => 영속상태에 저장되었다가 분리된 상태
//        return member;
//    }
//
//    private static void mergeMember(Member member) {
//        // 영속성 컨텍스트 2 시작
//        EntityManager em2 = emf.createEntityManager();
//        EntityTransaction tx2 = em2.getTransaction();
//        tx2.begin();
//
//        Member mergeMember = em2.merge(member);
//        tx2.commit();
//
//        //준영속 상태
//        System.out.println("member = " + member.getUsername()); // member = 회원명 변경
//
//
//        //영속상태
//        System.out.println("mergeMember = " + mergeMember.getUsername());// mergeMember = 회원명 변경
//
//
//        System.out.println("em2 contains = " + em2.contains(member));// em2 contains = false
//
//        System.out.println("em2 contains = " + em2.contains(mergeMember));// em2 contains = true
//
//
//        em2.close(); // 영속성 컨텍스트 2 종료
//
//    }
//
//}
//
