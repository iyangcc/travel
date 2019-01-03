package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RankDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RankDaoImpl implements RankDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 查询线路总条数
     * @return
     * @param rnameKey
     * @param money_start
     * @param money_end
     */
    @Override
    public int totalCount(String rnameKey, int money_start, int money_end) {
        //初始化sql
        String sql = "select count(*) from tab_route where 1 = 1 ";
        StringBuilder str = new StringBuilder(sql);
        ArrayList<Object> params = new ArrayList<>();
        if (rnameKey!=null&&rnameKey.length()>0){
            str.append(" and rname like ? ");
            params.add("%"+rnameKey+"%");
        }
        if(money_start >= 0){
            str.append(" and price >= ? ");
            params.add(money_start);
        }
        if(money_end >= 0){
            str.append(" and price <= ? ");
            params.add(money_end);
        }
        sql = str.toString();
        return template.queryForObject(sql,Integer.class,params.toArray());
    }

    /**
     * 分页查询线路收藏
     * @param start
     * @param size
     * @param rnameKey
     * @param money_start
     * @param money_end
     * @return
     */
    @Override
    public List<Route> queryPage(int start, int size, String rnameKey, int money_start, int money_end) {
        //初始化sql
        String sql = "select * from tab_route where 1 = 1 ";
        StringBuilder str = new StringBuilder(sql);
        ArrayList params = new ArrayList<>();
        List<Route> list = null;
        if (rnameKey!=null&&rnameKey.length()>0){
            str.append(" and rname like ? ");
            params.add("%"+rnameKey+"%");
        }
        if(money_start >= 0){
            str.append(" and price >= ? ");
            params.add(money_start);
        }
        if(money_end >= 0){
            str.append(" and price <= ? ");
            params.add(money_end);
        }
        str.append(" order by count desc ");
        str.append(" limit ? , ? ");
        params.add(start);
        params.add(size);
        sql = str.toString();
        try {
            list = template.query(sql , new BeanPropertyRowMapper<Route>(Route.class) , params.toArray());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return list;
    }
}
