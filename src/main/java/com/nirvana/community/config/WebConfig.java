package com.nirvana.community.config;

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
    private String[] excludePathPatterns = {"/","/callback","/js/**","/css/**","/img/**","/fonts/**"};
    //需要拦截路径
    private String[] pathPatterns = {"/**"};

    //将拦截器放入spring容器中管理
    @Bean
    public LoginInterceptor getLoginInterceptor(){
        return new LoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.getLoginInterceptor()).addPathPatterns(pathPatterns).excludePathPatterns(excludePathPatterns);
    }
}
