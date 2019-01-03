package cn.itcast.travel.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
* @Description:    解决中文乱码问题，处理所有的请求
* @Author:         zhangziyang
* @CreateDate:     2018/12/2 13:24
* @Version:        1.0
*/

@WebFilter("/*")
public class CharsetFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //父接口转子接口
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        //获取请求方式
        String method = request.getMethod();
        if (method.equalsIgnoreCase("post")){
            //post请求中文乱码处理
            request.setCharacterEncoding("utf-8");
        }
        //处理响应中文乱码
        response.setContentType("text/html;charset=utf-8");
        //放行
        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
