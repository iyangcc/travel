package cn.itcast.travel.web.servlet2.base;

import cn.itcast.travel.domain.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author: Zhangzy
 * @CreateDate: 2018/12/4 10:35
 * @Description: 基类Servlet
 */

public class BaseServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取访问的URI
        String uri = req.getRequestURI();
        //获取方法名
        String methodName = uri.substring(uri.lastIndexOf("/") + 1);
        //获取方法
        try {
            //this指代调用该BaseServlet的Servlet
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            //执行方法
            method.invoke(this,req,resp);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回写出数据
     * @param info
     * @param response
     * @throws IOException
     */
    public void goResult(Object info,HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getWriter(),info);
    }

    /**
     * 返回json字符串
     * @param info
     * @return
     * @throws JsonProcessingException
     */
    public String getJson(Object info) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(info);
    }

    public User loggedUser(HttpServletRequest request){
        //获取已登录用户
        HttpSession session = request.getSession();
        return  (User) session.getAttribute("user");

    }
}
