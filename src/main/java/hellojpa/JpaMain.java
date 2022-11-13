package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args){
        //DB당 하나만 생성
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        //고객의 요청이 올 때마다 생성, close()
        //쓰레드 간에 공유 x (사용하고 버려야 함.)
        EntityManager em = emf.createEntityManager();

        //*** JPA의 모든 데이터 변경은 트랜잭션 안에서 실행해야 함. ***
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            Member member = new Member();
            member.setId(3L);
            member.setName("HelloC");
            em.persist(member);

            Member findMember = em.find(Member.class, 1L);
            findMember.setName("HelloJPA2");

            tx.commit();    //정상적일 때 -> commit
        } catch (Exception e) {
            //오류 발생시 -> rollback
            tx.rollback();
        } finally {
            //내부적으로 database connection을 가지고 있어서 꼭 닫아줘야 함.
            em.close();
        }

        emf.close();
    }
}
