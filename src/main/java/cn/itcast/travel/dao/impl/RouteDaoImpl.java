package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 查询分页数据的总数据条数
     * @param id
     * @param keyWords
     * @return
     */
    @Override
    public int totalCount(int id, String keyWords) {
        //初始化sql
        String sql = "select count(*) from tab_route where 1 = 1 ";
        StringBuilder str = new StringBuilder(sql);
        ArrayList<Object> params = new ArrayList<>();
        if (id != 0){
           str.append(" and cid = ? ");
           params.add(id);
        }
        if (!"".equals(keyWords)&&keyWords!=null) {
            str.append(" and rname like ? ");
            params.add("%"+keyWords+"%");
        }
        sql = str.toString();
        return template.queryForObject(sql,Integer.class,params.toArray());
    }

    /**
     * 分页查询的数据
     * @param id
     * @param start
     * @param size
     * @param keyWords
     * @return
     */
    @Override
    public List<Route> queryPage(int id, int start, int size, String keyWords) {
        List<Route> list = null;
        String sql = "select * from tab_route where 1 = 1 ";
        StringBuilder str = new StringBuilder(sql);
        ArrayList<Object> params = new ArrayList<>();
        if (id != 0) {
            str.append(" and cid = ? ");
            params.add(id);
        }
        if (!"".equals(keyWords)&&keyWords!=null) {
            str.append(" and rname like ? ");
            params.add("%"+keyWords+"%");
        }
        str.append(" limit ? , ? ");
        params.add(start);
        params.add(size);
        sql = str.toString();
        try{
            list = template.query(sql, new BeanPropertyRowMapper<Route>(Route.class), params.toArray());
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 通过id获取线路信息
     * @param id
     * @return
     */
    @Override
    public Route findById(int id) {
        String sql = "select * from tab_route where rid = ?";
        Route r = null;
        try {
            r = template.queryForObject(sql,new BeanPropertyRowMapper<Route>(Route.class),id);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return r;
    }

    /**
     * 最新旅游线路
     * @param count
     * @return
     */
    @Override
    public List<Route> findLatest(int count) {
        String sql = "select * from tab_route order by rdate desc limit 0 , ?";
        List<Route> list = null;
        try {
            list = template.query(sql,new BeanPropertyRowMapper<Route>(Route.class),count);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 主题旅游线路
     * @param count
     * @return
     */
    @Override
    public List<Route> findTheme(int count) {
        String sql = "select * from tab_route where isThemeTour = ? order by count desc limit 0 , ?";
        List<Route> list = null;
        try {
            list = template.query(sql,new BeanPropertyRowMapper<Route>(Route.class),1,count);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 通过cid获取线路列表
     * @param cid
     * @param count
     * @return
     */
    @Override
    public List<Route> listByRid(int cid, int count) {
        String sql = "select * from tab_route where cid = ? order by count desc limit 0 , ?";
        List<Route> list = null;
        try {
            list = template.query(sql,new BeanPropertyRowMapper<Route>(Route.class),cid,count);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 通过cid查询热门
     * @param cid
     * @param count
     * @return
     */
    @Override
    public List<Route> hotRouteByCid(int cid, int count) {
        String sql = "select * from tab_route where cid = ? order by count desc limit 0 , ?";
        List<Route> list = null;
        try {
           list = template.query(sql,new BeanPropertyRowMapper<>(Route.class),cid,count);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return list;
    }
}
