package jpabook.jpashop;

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
            tx.commit();    // 정상적일 때 -> commit  커밋하는 시점에 DB에 쿼리가 날라감
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
