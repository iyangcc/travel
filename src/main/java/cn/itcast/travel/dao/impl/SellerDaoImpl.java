package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class SellerDaoImpl implements SellerDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    /**
     * 通过id查询商家信息
     * @param sid
     * @return
     */
    @Override
    public Seller findById(int sid) {
        String sql = "select * from tab_seller where sid = ?";
        Seller s = null;
        try {
           s = template.queryForObject(sql,new BeanPropertyRowMapper<>(Seller.class),sid);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return s;
    }
}
