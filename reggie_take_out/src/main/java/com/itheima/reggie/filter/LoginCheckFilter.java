package com.itheima.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.itheima.reggie.common.BaseContext;
import com.itheima.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    //路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        Long empId = (Long) request.getSession().getAttribute("employee");
        BaseContext.setCurrentId(empId);

//        **过滤器具体的处理逻辑如下：**

//        A. 获取本次请求的URI
        String requestURL = request.getRequestURI();
        log.info("拦截到请求：{}",request.getRequestURI());
        //定义不需要处理的请求路径
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                //而这样的话，就要求我们在测试时，每一次都需要先登录，
                // 登录完成后在进行图片上传的测试，为了简化我们的测试，
                // 我们可以在 LoginCheckFilter 的doFilter方法中，在
                // 不需要处理的请求路径的数组中再加入请求路径 /common/** , 如下:
                "/common/**",
                "/user/sendMsg",
                "/user/login",
        };


//        B. 判断本次请求, 是否需要登录, 才可以访问
        boolean check = check(urls,requestURL);


//        C. 如果不需要，则直接放行
        if (check){
            log.info("本次请求{}不需要放行",requestURL);
            filterChain.doFilter(request,response);
            return;
        }


//        D. 判断登录状态，如果已登录，则直接放行
        if (request.getSession().getAttribute("employee")!=null){
            log.info("用户已登录，用户id为：{}",request.getSession().getAttribute("employee"));
            filterChain.doFilter(request,response);
            return;
        }

        //4-2、判断登录状态，如果已登录，则直接放行
        if(request.getSession().getAttribute("user") != null){
            log.info("用户已登录，用户id为：{}",request.getSession().getAttribute("user"));

            Long userId = (Long) request.getSession().getAttribute("user");
            BaseContext.setCurrentId(userId);

            filterChain.doFilter(request,response);
            return;
        }


//
//        E. 如果未登录, 则返回未登录结果
        log.info("用户未登录");
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
    }

    /**
     * 路径匹配，检查本次请求是否需要放行
     * @param urls
     * @param requestURL
     * @return
     */
    public boolean check(String[] urls,String requestURL){
        for (String url:urls){
            boolean match = PATH_MATCHER.match(url,requestURL);
            if (match){
                return true;
            }
        }
        return false;
    }
}
