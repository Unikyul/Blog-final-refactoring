package shop.mtcoding.blog.user;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(UserRepository.class)
public class UserRepositroyTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByUsername() {
        String username = "haha";
        User user = userRepository.findByUsername(username);

        System.out.println("user = " + user);
    }


    @Test
    public void save_test() {
        String username = "haha";
        String password = "1234";
        String email = "haha@nate.com";

        userRepository.save(User.builder().username(username).password(password).email(email).build());
    }

}
