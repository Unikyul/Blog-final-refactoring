package shop.mtcoding.blog.core.error.ex;

//인증관련 오류
public class Exception500 extends  RuntimeException{
    public Exception500(String message) {

        super(message);// 부모한테 넘기기
    }


}
