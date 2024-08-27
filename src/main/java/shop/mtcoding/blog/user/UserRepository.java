package shop.mtcoding.blog.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import shop.mtcoding.blog.core.error.ex.Exception401;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Repository
public class UserRepository {


    private final EntityManager em;

    //유저네임로 조회
    public User findByUsername(String username) {
        Query query = em.createQuery("select u  from User u where u.username=:username", User.class);
        query.setParameter("username", username);

        try {
            User user = (User) query.getSingleResult();
            return user;

        } catch (Exception e) {

            return null;
        }


    }

    //401 터트리기 여기서 터트리기
    public User findByUsernameAndPassword(String username, String password) throws NoResultException {
        Query query = em.createQuery("select u  from User u where u.username=:username and u.password=:password", User.class);
        query.setParameter("username", username);
        query.setParameter("password", password);

        try {
            User user = (User) query.getSingleResult();
            return user;
        } catch (Exception e) {


            // e.getmsg는 해주면 안된다. 서버 노출이 된다.
            throw new Exception401("인증되지 않았습니다.");


        }
    }


    @Transactional // 비영속 객체를 영속성 컨텍스트에 잠깐 스치면 바로 insert 해준다.
    public void save(User user) {
        System.out.println("담기기전 :" + user.getId());
        em.persist(user);
        System.out.println("담기기후  :" + user.getId());


    }


    public User findById() {
        Query query = em.createNativeQuery("select * from user_tb where id = 1");
        Object[] obs = (Object[]) query.getSingleResult();
        System.out.println(obs[0]);
        System.out.println(obs[1]);
        System.out.println(obs[2]);
        System.out.println(obs[3]);
        System.out.println(obs[4]);

        User user = new User();
        user.setId((Integer) obs[0]);
        user.setCreatedAt((Timestamp) obs[1]);
        user.setEmail((String) obs[2]);
        user.setPassword((String) obs[3]);
        user.setUsername((String) obs[4]);

        return user;
    }
}


