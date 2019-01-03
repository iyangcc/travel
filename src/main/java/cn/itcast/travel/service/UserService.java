package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;

public interface UserService {
    /**
     * 查找用户名是否存在
     * @return
     * @param username
     */
    Boolean findUserName(String username);

    /**
     * 注册新用户
     * @param user
     * @return
     */
    boolean registerUser(User user);

    /**
     * 验证激活码
     * @param code
     * @return
     */
    boolean checkCode(String code);

    /**
     * 更新用户状态
     * @param user
     */
    void changeStatus(User user);

    /**
     * 用户登录
     * @param user
     * @return
     */
    User login(User user);

    /**
     * 查询用户信息
     * @param uid
     * @return
     */
    User viewUser(String uid);

    /**
     * 通过uid修改用户信息
     * @param user
     * @return
     */
    boolean update(User user);

    /**
     * 修改用户密码及检验
     * @param uid
     * @param oldPassword
     * @param newPassword
     * @return
     */
    boolean changePassword(String uid, String oldPassword, String newPassword);

    /**
     * 分页查询用户的收藏列表
     * @param uid
     * @param currentPage
     * @param pageSize
     * @return
     */
    PageBean<Route> favorite(String uid, String currentPage, String pageSize);
}
