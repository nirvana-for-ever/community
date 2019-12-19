package com.nirvana.community.config;

import com.nirvana.community.interceptor.CheckLoginInterceptor;
import com.nirvana.community.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @program: community
 * @description:
 * @author: Nirvana
 * @create: 2019--12--05--13:33
 **/
@Configuration//相当于spring的配置文件
public class WebConfig implements WebMvcConfigurer {

    //不拦截路径
    private String[] excludePathPatterns = {""};
    //需要拦截路径
    private String[] pathPatternsLogin = {"/**"};
    private String[] pathPatterns = {"/publish**/**","/profile/questions**"};
    //将拦截器放入spring容器中管理
    @Bean
    public CheckLoginInterceptor getCheckLoginInterceptor(){
        return new CheckLoginInterceptor();
    }
    @Bean
    public LoginInterceptor getLoginInterceptor(){
        return new LoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //多个拦截器的执行顺序：拦截器加入的顺序就是执行的顺序

        registry.addInterceptor(this.getCheckLoginInterceptor()).addPathPatterns(pathPatternsLogin);

        registry.addInterceptor(this.getLoginInterceptor()).addPathPatterns(pathPatterns).excludePathPatterns(excludePathPatterns);
    }
}
