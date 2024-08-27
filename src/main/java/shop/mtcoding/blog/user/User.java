package shop.mtcoding.blog.user;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;


@Getter
@Setter
@Table(name= "user_tb")
@NoArgsConstructor
@Entity
public class User {




    //비정형 데이터는

    //N쪽에 포린키 가 있어야 한다.
    //명사들 생각하다가 둘의 관계를 보다가 동사,명사가 된다.
    //N:N 이면 무조건 중간에 동사가 나온다.


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true , nullable = false)
    private String username; // 아이디
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String email;

    @CreationTimestamp // 하이버네이트의 도움을 받으면 자동으로 시간이 등록된다.
    private Timestamp createdAt;


    @Builder
    public User(Integer id, String username, String password, String email, Timestamp createdAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.createdAt = createdAt;
    }


}
