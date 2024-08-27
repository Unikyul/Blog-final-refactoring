package shop.mtcoding.blog.core.error.ex;

//권환,실행 오류
public class Exception403 extends RuntimeException {
    public Exception403(String message) {

        super(message);// 부모한테 넘기기
    }


}
