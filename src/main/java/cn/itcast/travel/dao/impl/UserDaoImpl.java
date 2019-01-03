package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    /**
     * 查找用户名是否存在
     * @return
     * @param username
     */
    @Override
    public User findUserName(String username) {
        User user = null;
        String sql = "select * from tab_user where username = ?";
        try {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username);
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 注册新用户
     * @param user
     * @return
     */
    @Override
    public int registerUsr(User user) {
        String sql = "insert into tab_user values(null,?,?,?,?,?,?,?,?,?)";
        return template.update(sql, user.getUsername(), user.getPassword(), user.getName(), user.getBirthday(), user.getSex(), user.getTelephone(),user.getEmail(), user.getStatus(), user.getCode());
    }

    /**
     * 验证激活码
     * @param code
     * @return
     */
    @Override
    public User checkCode(String code) {
        User user = null;
        String sql = "select * from tab_user where code = ?";
        try{
            user= template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), code);
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 更新用户状态
     * @param user
     */
    @Override
    public void changeStatus(User user) {
        String sql = "update tab_user set status = 'Y' where uid = ?";
        template.update(sql,user.getUid());
    }

    /**
     * 用户登录
     * @param user
     * @return
     */
    @Override
    public User login(User user) {
        User u = null;
        String sql = "select * from tab_user where username = ? and password = ?";
        try{
            u = template.queryForObject(sql,new BeanPropertyRowMapper<>(User.class),user.getUsername(),user.getPassword());
        }catch (Exception e){
            e.printStackTrace();
        }
        return u;
    }

    /**
     * 查询用户信息
     * @param id
     * @return
     */
    @Override
    public User viewUser(int id) {
        String sql = "select * from tab_user where uid = ?";
        User user = null;
        try {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), id);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    @Override
    public int update(User user) {
        String sql = "update tab_user set name = ? , telephone = ? , birthday = ? where uid = ?";
        return template.update(sql,user.getName(),user.getTelephone(),user.getBirthday(),user.getUid());
    }

    /**
     * 校验旧密码是否正确
     * @param id
     * @param oldPassword
     * @return
     */
    @Override
    public boolean checkOldPwd(int id, String oldPassword) {
        String sql = "select * from tab_user where uid = ? and password = ?";
        User user = null;
        try{
            user = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), id, oldPassword);
        }catch (Exception e){
            e.printStackTrace();
        }
        return user!=null;
    }

    /**
     * 通过用户id修改密码
     * @param id
     * @param newPassword
     * @return
     */
    @Override
    public int changePwd(int id, String newPassword) {
        String sql = "update tab_user set password = ? where uid = ?";
        return template.update(sql, newPassword, id);
    }

}
