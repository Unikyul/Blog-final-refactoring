package shop.mtcoding.blog.board;


//C -> S -> R


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.mtcoding.blog.core.error.ex.Exception403;
import shop.mtcoding.blog.user.User;

import java.util.List;


@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;  //DI 끝


    @Transactional
    public void 게시글수정(int id, BoardRequest.UpdateDTO updateDTO, User sessionUser) {

        //1.게시글 조회(없으면 404)
        Board board = boardRepository.findById(id);


        //2. 권환체크
        if (board.getUser().getId() != sessionUser.getId()) {
            throw new Exception403("게시글 수정할 권한이 없습니다.");
        }

        //3.게시글 수정
        board.setTitle(updateDTO.getTitle());
        board.setContent(updateDTO.getContent());


    }  //flush() 자동 호출됨(더티체킹)
    // Hibernate:
    //    update
    //        board_tb
    //    set
    //        content=?,
    //        created_at=?,
    //        title=?,
    //        user_id=?
    //    where
    //        id=?


    public Board 게시글수정화면(int id, User sessionUser) {
        //나는 throw로 넘겼기 때문에 이렇게 하면 된다.,


        Board board = boardRepository.findById(id);

        if (board.getUser().getId() != sessionUser.getId()) {
            throw new Exception403("게시글 수정할 권한이 없습니다.");
        }

        return board;

    }


    public List<Board> 게시글목록보기() {
        List<Board> boardList = boardRepository.findAll();
        return boardList;
    }


    //2개 리턴을 못한다.
    //비즈니스 로직에는 Controller가 해줄 필요가 없다.
    public BoardResponse.DetailDTO 상세보기(int id, User sessionUser) {

        Board board = boardRepository.findById(id); // 조인(Board - User) 포함, 절대 null 일수 가 없다.

        return new BoardResponse.DetailDTO(board, sessionUser);
    }

    @Transactional
    public void 게시글쓰기(BoardRequest.SaveDTO saveDTO, User sessionUser) {
        boardRepository.save(saveDTO.toEntity(sessionUser));


    }

    @Transactional
    public void 게시글삭제(int id, User sessionUser) {
        //1.컨트롤러로 부터 게시글 id를 받기

        //2. 게시글 존재 여부 확인 없으면(404)
        Board board = boardRepository.findById(id);

        // if (boardRepository.findById(id) == null) {
        //    throw new Exception404("이미 존재하지 않는 게시글 입니다.,");


        //3. 내가 쓴글인지 확인하기(403)
        if (sessionUser.getId() != board.getUser().getId()) {
            throw new Exception403("권환이 없습니다.");
        }


        //if (!boardRepository.findById(sessionUser.getId()).equals(boardRepository.findById(id))) {
        //    throw new Exception403(" 내가 작성한 글이 아닙니다.,");


        // }
        //4. 게시글 삭제하기
        boardRepository.deleteById(id);

    }
}

