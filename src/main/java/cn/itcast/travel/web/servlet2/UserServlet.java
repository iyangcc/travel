package cn.itcast.travel.web.servlet2;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import cn.itcast.travel.web.servlet2.base.BaseServlet;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @Author: Zhangzy
 * @CreateDate: 2018/12/4 10:36
 * @Description: User相关的业务控制
 */
@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

    //声明UserService业务对象
    private UserService service = new UserServiceImpl();

    /**
     * 用户登录
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            result.setData(u);
            Cookie c = new Cookie("JSESSIONID",session.getId());
            c.setMaxAge(60*60);
            response.addCookie(c);
            session.setAttribute("user",u);
        }
        goResult(result,response);
    }

    /**
     * 新用户注册
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
     * 激活用户
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取激活码
        String code = request.getParameter("code");
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

    /**
     * 退出用户
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取session
        HttpSession session = request.getSession();
        //session自动关闭
        session.invalidate();
        //重定向,跳转到登录页面
        response.sendRedirect(getServletContext().getContextPath()+"/login.html");
    }

    /**
     * 查询已登录用户
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取已登录用户
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        //创建对象
        ResultInfo result = new ResultInfo();
        //判断是否存在user
        if(user!=null){
            result.setStatus(true);
            result.setData(user);
        }else{
            result.setStatus(false);
        }
        //解析数据并返回
        goResult(result,response);
    }

    /**
     * 查询是否已存在用户名
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void existsed(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        Boolean bool = service.findUserName(username);
        ResultInfo result = new ResultInfo();
        if(bool){
            result.setStatus(true);
        }else{
            result.setStatus(false);
        }
        goResult(result,response);
    }

    /**
     * 用户个人中心
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void center(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uid = request.getParameter("uid");
        User user = service.viewUser(uid);
        ResultInfo result = new ResultInfo(true,user,null);
        goResult(result,response);
    }

    /**
     * 修改用户信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        boolean bool = service.update(user);
        ResultInfo result = new ResultInfo();
        if(bool){
            result.setStatus(true);
        }else{
            result.setStatus(false);
            result.setErrorMsg("修改信息失败！");
        }
        goResult(result,response);
    }

    /**
     * 修改用户登录密码
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void changePwd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取参数
        String uid = request.getParameter("uid");
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String checkCode = request.getParameter("checkCode");
        //获取seesion中的验证码
        HttpSession session = request.getSession();
        String checkCode_session = (String) session.getAttribute("checkCode_session");
        session.removeAttribute("checkCode_session");
        //创建返回数据对象
        ResultInfo result = new ResultInfo();
        if(checkCode.equalsIgnoreCase(checkCode_session)&&checkCode_session!=null&&checkCode!=""){
            boolean bool = service.changePassword(uid,oldPassword,newPassword);
            if(bool){
                result.setStatus(true);
            }else{
                result.setStatus(false);
                result.setErrorMsg("旧密码不正确！");
            }
        }else{
            result.setStatus(false);
            result.setErrorMsg("验证码错误！");
        }
        goResult(result,response);
    }

    /**
     * 查询用户的收藏列表
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void favorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取参数
        String uid = request.getParameter("uid");
        String currentPage = request.getParameter("currentPage");
        String pageSize = request.getParameter("pageSize");
        User user = loggedUser(request);
        //创建返回数据对象
        ResultInfo result = new ResultInfo();
        if(user!=null){
            if(Integer.parseInt(uid)==user.getUid()){
                PageBean<Route> page = service.favorite(uid,currentPage,pageSize);
                result.setStatus(true);
                result.setData(page);
            }else{
                result.setStatus(false);
            }
        }else{
            result.setStatus(false);
        }
        goResult(result,response);
    }
}
