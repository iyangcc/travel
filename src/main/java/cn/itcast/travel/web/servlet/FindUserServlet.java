package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Author: Zhangzy
 * @CreateDate: 2018/12/2 23:10
 * @Description: 查询已登录用户名
 */
@WebServlet("/findUserServlet")
public class FindUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取已登录用户
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        //创建对象
        ResultInfo result = new ResultInfo();
        ObjectMapper mapper = new ObjectMapper();
        //判断是否存在user
        if(user!=null){
            result.setStatus(true);
            result.setData(user);
        }else{
            result.setStatus(false);
        }
        //解析数据并返回
        String json = mapper.writeValueAsString(result);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
