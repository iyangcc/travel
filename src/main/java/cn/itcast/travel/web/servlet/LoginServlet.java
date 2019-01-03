package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @Author: Zhangzy
 * @CreateDate: 2018/12/2 19:10
 * @Description: 用户登录校验
 */
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取参数
        String checkcode = request.getParameter("verifycode");
        Map<String, String[]> map = request.getParameterMap();
        HttpSession session = request.getSession();
        String checkCode_server = (String) session.getAttribute("checkCode_session");
        //删除生成的验证码
        session.removeAttribute("checkCode_session");
        //创建数据返回对象
        ResultInfo result = new ResultInfo();
        //获取参数并判断是否是第一次登录
        boolean flag = false;//默认是第一次
        boolean remberUser = Boolean.parseBoolean(request.getParameter("remberUser"));
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for (Cookie cookie : cookies) {
                if(("username".equals(cookie.getName())||"password".equals(cookie.getName()))&&cookie.getValue()!=null&&cookie.getValue()!=""){
                    flag = true;
                    break;
                }
            }
        }
        if (!flag || !remberUser) {
            //验证码判断
            if (checkCode_server == null || !checkCode_server.equalsIgnoreCase(checkcode)) {
                result.setStatus(false);
                result.setErrorMsg("验证码错误！");
                goResult(result, response);
                return;
            }
        }
            UserService service = new UserServiceImpl();
            //封装用户对象信息
            User user = new User();
            try {
                BeanUtils.populate(user, map);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            User u = service.login(user);
            if (u == null) {
                //失败
                result.setStatus(false);
                result.setErrorMsg("用户名或密码错误！");
            }
            if (u != null && "N".equals(u.getStatus())) {
                //未激活
                result.setStatus(false);
                result.setErrorMsg("尚未激活账户，请先邮件激活！");
            }
            if (u != null && "Y".equals(u.getStatus())) {
                //成功
                result.setStatus(true);
                Cookie c = new Cookie("JSESSIONID",session.getId());
                c.setMaxAge(60*60*24*7);
                response.addCookie(c);
                session.setAttribute("user",u);
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
