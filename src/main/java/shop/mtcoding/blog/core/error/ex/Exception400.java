package shop.mtcoding.blog.core.error.ex;

//유효성 검사 할 때 사용!
public class Exception400 extends  RuntimeException{

    public Exception400(String message) {

        super(message);// 부모한테 넘기기
    }
}
