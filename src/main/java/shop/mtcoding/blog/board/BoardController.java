package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import shop.mtcoding.blog.user.User;

import java.util.List;


@RequiredArgsConstructor
// 식별자 요청 받기, 응답 하기
@Controller // 식별자 요청을 받을 수 있다.
public class BoardController {

    private final BoardService boardService;
    private final HttpSession session;

    //@Autowired
    //private HttpServletRequest request; 리퀘스트는 싱글톤이 아니기 때문에 IoC에 저장 하지 못한다.
    //


    //url : http://loacahost8080/board/1/update
    //body: title = 제목1변경&content=내용1변경
    //content-type : x-www-form-urlencoded
    @PostMapping("/api/board/{id}/update")
    public String update(@PathVariable("id") int id, @Valid BoardRequest.UpdateDTO updateDTO, Errors errors) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        // 인증 체크 필요함

        boardService.게시글수정(id, updateDTO, sessionUser);
        //만들어놨으면 redirect
        return "redirect:/board/" + id;

    }

    @PostMapping("/api/board/{id}/delete")
    public String delete(@PathVariable("id") int id) {

        User sessionUser = (User) session.getAttribute("sessionUser");

        boardService.게시글삭제(id, sessionUser);
        return "redirect:/";
    }


    // subtitle=제목1&postContent=내용1
    //board/save 를 붙이 이유는 https 1.0버전으로 하려고 그리고 자바스크립트 등 라이브러리 X ,순순한 스프링 프로그램으로만 만들려고
    // subtitle=제목1&postContent=내용1
    @PostMapping("/api/board/save")
    //SaveDTO가 만들어질때 공백이나 null일때 Errors로 보낸다.
    public String save(@Valid BoardRequest.SaveDTO saveDTO, Errors errrors) { // 스프링 기본전략 = x-www-form-urlencoded 파싱
        User sessionUser = (User) session.getAttribute("sessionUser");

        // 인증 체크 필요함


        boardService.게시글쓰기(saveDTO, sessionUser);
        return "redirect:/";
    }


    // get, post  //Model model Request 객체이다.
    //메인페이지는 그냥 /
    @GetMapping("/")
    public String list(HttpServletRequest request) {

        //view에 데이터를 전달 한 게 아니라 request 객체에서 보관되어있다가 musatche에서 찾아낸거다.
        List<Board> boardList = boardService.게시글목록보기();
        request.setAttribute("models", boardList);


        //session
        //HttpSession session = request.getSession();
        //session.setAttribute("num", 1);

        return "board/list";
    }

    //상세보기
    // 1. 메서드 : Get
    // 2. 주소 : /board/1
    // 3. 응답 : board/detail
    @GetMapping("/board/{id}")
    public String detail(@PathVariable("id") Integer id, HttpServletRequest request) {

        User sessionUser = (User) session.getAttribute("sessionUser");

        BoardResponse.DetailDTO detailDTO = boardService.상세보기(id, sessionUser);
        request.setAttribute("model", detailDTO);

        //     Board board = boardRepository.findById(id);
        //    request.setAttribute("model", board);
        //이 게시글의 주인인가요?
        //    request.setAttribute("isOwner",false);
        return "board/detail";
    }

    // 1. 메서드 : Get
    // 2. 주소 : /board/save-form
    // 3. 응답 : board/save-form
    @GetMapping("/api/board/save-form")
    public String saveForm() {

        return "board/save-form";
    }

    // 1. 메서드 : Get
    // 2. 주소 : /board/1/update-form
    // 3. 응답 : board/update-form
    // /board/3, /board/4
    @GetMapping("/api/board/{id}/update-form")
    public String updateForm(@PathVariable("id") int id, @Valid HttpServletRequest request, Errors errors) {

        //부가로직
        User sessionUser = (User) session.getAttribute("sessionUser");

        //핵심로직
        Board board = boardService.게시글수정화면(id, sessionUser);
        request.setAttribute("model", board);


        return "board/update-form";
    }

    //LAZY
    @GetMapping("/test/board/1")
    public void testBoard() {
        //     List<Board> boardList = boardRepository.findAll();
        System.out.println("---------------------------------------------");
        //      System.out.println(boardList.get(2).getUser().getPassword());
        System.out.println("---------------------------------------------");
    }

}


