package cn.itcast.travel.web.servlet;

import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Zhangzy
 * @CreateDate: 2018/12/2 17:28
 * @Description: 邮箱激活用户
 */
@WebServlet("/activeUserServlet")
public class ActiveUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取激活码
        String code = request.getParameter("code");
        UserService service = new UserServiceImpl();
        if(code!=null){
            String messge = null;
            //查询激活码，比较校验
            boolean bool = service.checkCode(code);
            if(bool){
                //成功
                messge = "<div style='width:80%;margin:200px auto;'><h4 style='text-align:center; font-size:32px; line-height:60px'>激活成功，现在前去<a href='login.html' style='color:orange;'>登录</a>！</h4></div>";
            }else{
                //失败
                messge = "<div style='width:80%;margin:200px auto;'><h4 style='text-align:center; font-size:32px; line-height:60px'><span style='color:red;'>激活失败</span>，请联系管理员</h4></div>";
            }
            response.getWriter().write(messge);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
