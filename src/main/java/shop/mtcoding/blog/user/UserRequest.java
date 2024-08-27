package shop.mtcoding.blog.user;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

public class UserRequest {

    @Data
    public static class JoinDTO {

        @NotEmpty
        private String username;
        @NotEmpty
        private String password;
        @NotEmpty
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식으로 작성해주세요")
        private String email;

        //DTO를 -> UserObject로 바꿔준다.
        public User toEntity() {
            return User.builder().username(username).password(password).email(email).build();
        }


    }

    @Data
    public static class loginDTO {

        private String username;
        private String password;


    }


}
