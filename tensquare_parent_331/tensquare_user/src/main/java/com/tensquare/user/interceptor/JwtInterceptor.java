package com.tensquare.user.interceptor;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import util.JwtUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//拦截器=> 抽取从request中获得的token代码
@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    //在controller方法执行前,先执行
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //1.获得token
        //获得不到=>抛出token异常提示
        String header = request.getHeader("Authorization");

        if(!StringUtils.isEmpty(header)&&header.startsWith("Bearer ")){
            String token = header.substring(7);

            Claims claims = jwtUtils.parseToken(token);

            if(claims!=null){
                String role = (String)claims.get("role");
                request.setAttribute("claims_"+role,claims);

                System.out.println("将jwt信息的载荷放入request域中:claims_"+role);
            }
        }

        return true;
    }
}
