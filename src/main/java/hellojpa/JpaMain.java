package hellojpa;

import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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

            member.getAddressesHistory().add(new AddressEntity("old1", "street1", "10000"));
            member.getAddressesHistory().add(new AddressEntity("old2", "street1", "10000"));

            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("=================== START ==================");
            Member findMember = em.find(Member.class, member.getId());

            AddressEntity addressEntity = new AddressEntity("old1", "street1", "10000");

//            findMember.getHomeAddress().setCity("newCity");
//            Address homeAddress = findMember.getHomeAddress();
//            findMember.setHomeAddress(new Address("newCity", homeAddress.getStreet(), homeAddress.getZipcode()));

//            findMember.getFavoriteFoods().remove("치킨");
//            findMember.getFavoriteFoods().add("한식");

            findMember.getAddressesHistory().remove(new AddressEntity("old1", "street1", "10000"));
            findMember.getAddressesHistory().add(new AddressEntity("newCity1", "street1", "10000"));

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
