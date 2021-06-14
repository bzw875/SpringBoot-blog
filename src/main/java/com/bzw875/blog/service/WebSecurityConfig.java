package com.bzw875.blog.service;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wangchenghao on 2017/8/5.
 */
@Configuration
public class WebSecurityConfig extends WebMvcConfigurerAdapter{

    public final static String SESSION_KEY = "user";

    @Bean
    public SecurityInterceptor getSecurityInterceptor() {
        return new SecurityInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());

        // 排除配置
        addInterceptor.excludePathPatterns(
                "/",
                "/post/detail/**",
                "/login",
                "/article/**",
                "/dologin",
                "/loginout",
                "/test",
                "/css/**",
                "/js/**",
                "/fonts/**",
                "/images/**");
        // 拦截配置
        addInterceptor.addPathPatterns("/**");
    }



    private class SecurityInterceptor extends HandlerInterceptorAdapter {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                throws Exception {
            Cookie[] cookies = request.getCookies();
            String path =request.getServletPath();
            System.out.println(path);
            if(path.contains("article") || path.contains("login")){
                return true;
            }
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(SESSION_KEY)) {
                        return true;
                    }
                }
            }
            response.sendRedirect("/login");
            return false;
        }
    }
}
