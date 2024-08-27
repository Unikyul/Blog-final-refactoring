package shop.mtcoding.blog.user;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.mtcoding.blog.core.error.ex.Exception400;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;


    @Transactional
    //비정상 로직만 if에 넣어서 걸러내라!! if, else로 해버리면 가독성이랑 로직이 더 길어진다....
    public void 회원가입(UserRequest.JoinDTO joinDTO) {
        User oldUser = userRepository.findByUsername(joinDTO.getUsername());

        if (oldUser != null) {
            throw new Exception400("이미 존재하는 유저네임입니다.,");
        }
        userRepository.save(joinDTO.toEntity());


    }

    @Transactional
    public User 로그인(UserRequest.loginDTO loginDTO) {
        User user = userRepository.findByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword());
        return user;

    }


}
