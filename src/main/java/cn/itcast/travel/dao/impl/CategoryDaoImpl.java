package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    /**
     * 查找所有分类
     * @return
     */
    @Override
    public List<Category> findAll() {
        String sql = "select * from tab_category";
        List<Category> list = null;
        try {
            list = template.query(sql, new BeanPropertyRowMapper<Category>(Category.class));
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 通过id查找分类信息
     * @param cid
     * @return
     */
    @Override
    public Category findOne(int cid) {
        String sql = "select * from tab_category where cid = ?";
        Category c = null;
        try {
            c = template.queryForObject(sql,new BeanPropertyRowMapper<>(Category.class),cid);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return c;
    }
}
