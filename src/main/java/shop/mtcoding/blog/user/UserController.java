package shop.mtcoding.blog.user;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import shop.mtcoding.blog.core.Hello;

@RequiredArgsConstructor // final이 붙은 애들의 생성자를 만들어준다,.
@Controller
public class UserController {


    //User에서는 UserService만 사용 할 수 있게 해주는 게 좋다 Borad에서 가져오기 않기!
    //트랜잭션관리도 어려워진다.
    //간단하게 위임만 하면 된다.

    private final UserService userService;
    private final HttpSession session;


    //회원가입 하고 난 후
    @PostMapping("/join")
    public String join(@Valid UserRequest.JoinDTO joinDTO, Errors errors) {
        //insert 하는 모든 것들은 toEntitiy로 하면 된다.
        userService.회원가입(joinDTO);
        return "redirect:/login-form";

    }

    @GetMapping("/join-form")
    public String joinForm() {
        return "user/join-form";


    }

    @PostMapping("/login")
    public String login(@Valid UserRequest.loginDTO loginDTO, Errors errors) {
        // User sessionUser = userRepository.findByUsernameAndPassword(loginDTO.getUsername(),loginDTO.getPassword());
        User sessionUser = userService.로그인(loginDTO);
        session.setAttribute("sessionUser", sessionUser);
        return "redirect:/";
    }

    @Hello
    @GetMapping("/login-form")
    public String loginForm() {
        System.out.println("loginForm 호출됨");
        return "user/login-form";


    }

    @GetMapping("/logout")
    public String logout() {
        session.invalidate();  // 안에 있는 세션 데이터를 다 날린다.
        return "redirect:/";
    }

}
