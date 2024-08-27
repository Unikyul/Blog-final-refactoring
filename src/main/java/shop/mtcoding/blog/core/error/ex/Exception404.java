package shop.mtcoding.blog.core.error.ex;

//인증관련 오류
public class Exception404 extends  RuntimeException{
    public Exception404(String message) {

        super(message);// 부모한테 넘기기
    }


}
