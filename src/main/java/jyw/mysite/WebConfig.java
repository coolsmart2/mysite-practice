package jyw.mysite;

import jyw.mysite.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .order(1)
                .addPathPatterns(UrlConst.REDIRECT_URL) // 여기에 mysite 에 해당되는 모든 url 넣기, 그래야지 이상한 url에 대해 오류페이지 실행 가능
//                .addPathPatterns("/**") // 여기에 mysite 에 해당되는 모든 url 넣기, 그래야지 이상한 url에 대해 오류페이지 실행 가능
                .excludePathPatterns("/css/**", "/*.ico", "/error", "/", "/login", "/sign-up", "/back");
    }
}
