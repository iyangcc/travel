package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteFavoriteDao;
import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteFavoriteDaoImpl;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDao dao = new UserDaoImpl();
    private RouteDao r_dao = new RouteDaoImpl();
    private RouteFavoriteDao rf_dao = new RouteFavoriteDaoImpl();
    /**
     * 查找用户名是否存在
     * @return
     * @param username
     */
    @Override
    public Boolean findUserName(String username) {
        User user = dao.findUserName(username);
        if(user!=null){
            return true;
        }
        return false;
    }

    /**
     * 注册新用户
     * @param user
     * @return
     */
    @Override
    public boolean registerUser(User user) {
        //保存用户，设置唯一的激活码
        user.setCode(UuidUtil.getUuid());
        //设置用户状态
        user.setStatus("N");
        int count = dao.registerUsr(user);
        //保存成功后发送激活邮件
        if(count>0){
            String context = "<div><h4>欢迎您注册黑马旅游网，如果是本人操作，则点击<a href='http://localhost/travel/user/active?code="+user.getCode()+"'>【激活】</a>后，方可登录。如果非本人操作忽略此信息。</h4></div>";
            String title ="在线激活黑马旅游用户信息";
            //发送邮件
            MailUtils.sendMail(user.getEmail(),context,title);
        }
        return count>0;
    }

    /**
     * 验证激活码
     * @param code
     * @return
     */
    @Override
    public boolean checkCode(String code) {
        User user = dao.checkCode(code);
        if(user!=null){
            changeStatus(user);
        }
        return user!=null;
    }

    /**
     * 更新用户状态
     * @param user
     */
    @Override
    public void changeStatus(User user) {
        dao.changeStatus(user);
    }

    /**
     * 用户登录
     * @param user
     * @return
     */
    @Override
    public User login(User user) {
       return dao.login(user);
    }

    /**
     * 查询用户信息
     * @param uid
     * @return
     */
    @Override
    public User viewUser(String uid) {
        int id = Integer.parseInt(uid);
       return dao.viewUser(id);
    }

    /**
     * 通过uid修改用户信息
     * @param user
     * @return
     */
    @Override
    public boolean update(User user) {
        int query = dao.update(user);
        return query>0;
    }

    /**
     * 修改用户密码及检验
     * @param uid
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @Override
    public boolean changePassword(String uid, String oldPassword, String newPassword) {
        int id = Integer.parseInt(uid);
        boolean bool = dao.checkOldPwd(id,oldPassword);
        if(bool){
            int i = dao.changePwd(id,newPassword);
            return i > 0;
        }else{
            return false;
        }
    }

    /**
     * 分页查询用户的收藏列表
     * @param uid
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public PageBean<Route> favorite(String uid, String currentPage, String pageSize) {
        int id = Integer.parseInt(uid);
        int current = 1;
        int size = 12;
        if(currentPage!=null&&!"".equals(currentPage)){
            current = Integer.parseInt(currentPage);
        }
        if(pageSize!=null&&!"".equals(pageSize)){
            size = Integer.parseInt(pageSize);
        }
        int start = (current - 1) * size;
        int totalCount = rf_dao.totalCount(id);
        int totalPage = totalCount % size == 0 ? totalCount / size : (totalCount / size) + 1;
        List<Route> list = rf_dao.queryPage(id,start,size);
        if(list!=null){
            for (int i = 0; i < list.size(); i++) {
                //通过rid查询路线详情，并设置回去
                list.set(i,r_dao.findById(list.get(i).getRid()));
            }
        }
        return new PageBean<Route>(totalCount,totalPage,current,size,0,list);
    }
}
