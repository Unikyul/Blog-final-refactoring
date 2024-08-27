package shop.mtcoding.blog.core.error;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import shop.mtcoding.blog.core.error.ex.Exception400;


@Component // 컨퍼넌트 스캔 , new 생성자
@Aspect// AOP등록
public class GlobalValidationHandler {


    @Before("@annotation(org.springframework.web.bind.annotation.PostMapping) || @annotation(org.springframework.web.bind.annotation.PutMapping)")
    public void validCheck(JoinPoint jp) {
        Object[] args = jp.getArgs(); // 모든 매개변수를 가지고 온다
        for (Object arg : args) {
            if (arg instanceof Errors) {
                Errors errors = (Errors) arg;


                if (errors.hasErrors()) {
                    for (FieldError error : errors.getFieldErrors()) {
                        throw new Exception400(error.getDefaultMessage() + ":" + error.getField());

                    }

                }
            }

        }


    }


    @Around("@annotation(shop.mtcoding.blog.core.Hello)") // Around 만 리턴이 필요하다, 리플랙션 해줘야해서
    // @Around ex) loginform 이전과 이 후를 다룰수있다.
    //@Before("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    //Joinpoint 매개변수
    public Object hello1(ProceedingJoinPoint jp) throws Throwable {
        System.out.println("AOP hello1  before 호출됨");
        Object proceed = jp.proceed(); // @Hello 어노테이션이 붙은 함수 호출 "user/login/form";
        System.out.println("AOP hello1 after 호출됨");
        System.out.println(proceed);
        return proceed;
    }

    //


}
