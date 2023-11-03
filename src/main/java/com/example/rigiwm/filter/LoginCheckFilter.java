package com.example.rigiwm.filter;

import com.alibaba.fastjson.JSON;
import com.example.rigiwm.comment.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebFilter
public class LoginCheckFilter implements Filter {

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 定义通过uri
        String[] uris = {"/employee/login/**", "/employee/logout/**", "/backend/**", "/front/**"};
        // 校验路径

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        filterChain.doFilter(request, response);
//        String uri = request.getRequestURI();
////        log.info("获取uri: {}", uri);
//        if (uriExist(uri, uris)){
//            filterChain.doFilter(request, response);
//            return;
//        }
//        // 校验是否一登录
//        Object employee = request.getSession().getAttribute("employee");
////        log.info("若果已经登录，获取回话：{}", employee);
//        if (employee != null){
//            filterChain.doFilter(request, response);
//            return;
//        }
//        // 拦截的uri，响应失败 通知前端
////        log.info("过滤拦截成功：{}", uri);
//        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }

    public boolean uriExist(String requestUri, String[] uris){
        for (String s : uris) {
            boolean match = pathMatcher.match(s, requestUri);
            if(match){
                return true;
            }
        }
        return false;
    }

}
