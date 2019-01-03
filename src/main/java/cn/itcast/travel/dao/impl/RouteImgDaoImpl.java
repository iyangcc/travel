package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RouteImgDaoImpl implements RouteImgDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    /**
     * 获取图片列表
     * @param id
     * @return
     */
    @Override
    public List<RouteImg> findById(int id) {
        String sql = "select * from tab_route_img where rid = ?";
        List<RouteImg> list = null;
        try {
            list = template.query(sql,new BeanPropertyRowMapper<>(RouteImg.class),id);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return list;
    }
}
