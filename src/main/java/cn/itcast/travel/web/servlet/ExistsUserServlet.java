package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Zhangzy
 * @CreateDate: 2018/12/2 14:17
 * @Description: 验证用户名是否在数据库中存在
 */
@WebServlet("/existsUserServlet")
public class ExistsUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        UserService service = new UserServiceImpl();
        Boolean bool = service.findUserName(username);
        ResultInfo result = new ResultInfo();
        ObjectMapper mapper= new ObjectMapper();
        if(bool){
            result.setStatus(true);
        }else{
            result.setStatus(false);
        }
        String json = mapper.writeValueAsString(result);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
