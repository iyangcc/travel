package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

import java.util.ArrayList;

public interface UserDao {
    /**
     * 查找用户名是否存在
     * @return
     * @param username
     */
    User findUserName(String username);

    /**
     * 注册新用户
     * @param user
     * @return
     */
    int registerUsr(User user);

    /**
     * 验证激活码
     * @param code
     * @return
     */
    User checkCode(String code);

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
     * @param id
     * @return
     */
    User viewUser(int id);

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    int update(User user);

    /**
     * 校验旧密码是否正确
     * @param id
     * @param oldPassword
     * @return
     */
    boolean checkOldPwd(int id, String oldPassword);

    /**
     * 通过用户id修改密码
     * @param id
     * @param newPassword
     * @return
     */
    int changePwd(int id, String newPassword);

}
