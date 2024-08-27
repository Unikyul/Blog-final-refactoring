package shop.mtcoding.blog.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import shop.mtcoding.blog.core.interceptor.LoginInterceptor;


//인터셉터
@Configuration // IoC에 저장됨
public class WebConfig implements WebMvcConfigurer {

    //모든 곳에서 말고 특정한 곳에서 사용
    // /user, /board 할 때만 뜬다.
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/api/**");
    }
}
