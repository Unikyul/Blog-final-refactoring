package shop.mtcoding.blog.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog.core.error.ex.Exception400;
import shop.mtcoding.blog.user.User;

import java.sql.Timestamp;
import java.util.List;


@RequiredArgsConstructor
@Repository // @Repository를 붙이면 스프링이 new를 해서 IoC(컬렉션 List자료형 같은거) 에 저장한다.
public class BoardRepository {

    // IoC에 있는 객체를 찾아온다.
    private final EntityManager em;

    public Board findByIdV2(int id) {
        Query query = em.createNativeQuery("select bt.id, bt.title, bt.content, bt.user_id, bt.created_at, ut.id u_id, ut.username, ut.password, ut.email, ut.created_at u_created_at from board_tb bt inner join user_tb ut on bt.user_id = ut.id where bt.id = ?", Board.class);
        query.setParameter(1, id);
        try {
            Board board = (Board) query.getSingleResult();
            return board;
        } catch (Exception e) {
            e.printStackTrace();
            // 익세션을 내가 잡은것 까지 배움 - 처리 방법은 v2에서 배우기
            throw new RuntimeException("게시글 id를 찾을 수 없습니다");
        }
    }


    //udapte
    @Transactional
    public void updateById(String title, String content, int id) {
        Query query = em.createNativeQuery("update board_tb set title =? , content =? where id=?");
        query.setParameter(1, title);
        query.setParameter(2, content);
        query.setParameter(3, id);

        query.executeUpdate();


    }

    //delete
//삭제 할 번호가 있는지 없는지 확인 해야함, 트랙젝션이 뜨면 느려짐
//Repository 이름은 직관적 이름을 사용해야한다.
    @Transactional
    public void deleteById(int id) {
        Query query = em.createNativeQuery("delete from board_tb where id =?");
        query.setParameter(1, id);
        query.executeUpdate();


    }


    //Board class 자동으로 오브젝트 맵핑을 해준다.
    public Board findById(int id) {
        // select * from board_tb bt inner join user_tb ut on bt.user_id = ut.id where bt.id =1
        Query query = em.createQuery("select b from Board b join fetch b.user where b.id = :id", Board.class);
        query.setParameter("id", id);

        try {
            // 단일 결과 조회
            Board board = (Board) query.getSingleResult();
            return board;

        } catch (Exception e) {
            e.printStackTrace();//오류 제대로 볼 수 있는 코드
            //내가 예외를 설정 할 수 있다. 익세션을 내가 잡은것 까지 배움 - 처리 방법은 v2에서 배우기
            //자기가 처리 하기 싫으면 throw로 넘길 수 있다.
            throw new Exception400("게시글 아이디를 찾을 수 없습니다.");

        }


    }


    public Board findByIdV3(int id) {
        Query query = em.createNativeQuery("select bt.id, bt.title, bt.content, bt.user_id, bt.created_at, ut.id u_id, ut.username, ut.password, ut.email, ut.created_at u_created_at from board_tb bt inner join user_tb ut on bt.user_id = ut.id where bt.id = ?");
        query.setParameter(1, id);
        Object[] obs = (Object[]) query.getSingleResult();

        System.out.println(obs[0]);
        System.out.println(obs[1]);
        System.out.println(obs[2]);
        System.out.println(obs[3]);
        System.out.println(obs[4]);
        System.out.println(obs[5]);
        System.out.println(obs[6]);
        System.out.println(obs[7]);
        System.out.println(obs[8]);
        System.out.println(obs[9]);

//        1
//        제목1
//        내용1
//        1
//        2024-08-21 12:49:35.197432
//        1
//        ssar
//        1234
//        ssar@nate.com
//        2024-08-21 12:49:35.194432
        Board board = new Board();
        User user = new User();
        board.setId((Integer) obs[0]);
        board.setTitle((String) obs[1]);
        board.setContent((String) obs[2]);
        board.setCreatedAt((Timestamp) obs[4]);

        user.setId((Integer) obs[3]);
        user.setUsername((String) obs[6]);
        user.setPassword((String) obs[7]);
        user.setEmail((String) obs[8]);
        user.setCreatedAt((Timestamp) obs[9]);

        board.setUser(user);

        return board;
    }


    // list 가져오기
    public List<Board> findAll() {
        //Query query = em.createNativeQuery("select * from board_tb order by id desc", Board.class);
        //JPQL
        Query query = em.createQuery("select b from Board b order by b.id desc", Board.class);
        List<Board> boardList = query.getResultList();
        return boardList;
    }

    // insert 하기
    @Transactional
    public void save(Board board) {
        em.persist(board);


    }

}
