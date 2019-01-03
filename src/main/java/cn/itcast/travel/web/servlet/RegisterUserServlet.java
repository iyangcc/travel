package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @Author: Zhangzy
 * @CreateDate: 2018/12/2 13:35
 * @Description: 注册用户信息
 */
@WebServlet("/registerUserServlet")
public class RegisterUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取验证码
        String checkcode = request.getParameter("checkcode");
        HttpSession session = request.getSession();
        String checkCode_service = (String) session.getAttribute("checkCode_session");
        //删除生成的验证码
        session.removeAttribute("checkCode_session");
        //创建数据返回结构对象
        ResultInfo result = new ResultInfo();
        //判断验证码是否一致
        if(checkCode_service == null || !checkCode_service.equalsIgnoreCase(checkcode)){
            //不一致
            result.setStatus(false);
            result.setErrorMsg("验证码错误！");
            goResult(result,response);
            return;
        }
        //验证码一致，获取参数
        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        //封装对象
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        UserService service = new UserServiceImpl();
        boolean bool = service.registerUser(user);
        if(bool){
            //成功
            result.setStatus(true);
        }else{
            //失败
            result.setStatus(false);
            result.setErrorMsg("注册失败，请稍后重试");
        }
        goResult(result,response);
    }

    /**
     * 返回数据
     * @param info
     * @param response
     * @throws IOException
     */
    public void goResult(Object info,HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
