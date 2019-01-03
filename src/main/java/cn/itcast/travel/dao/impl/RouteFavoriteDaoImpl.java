package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteFavoriteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;

public class RouteFavoriteDaoImpl implements RouteFavoriteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    /**
     * 通过uid收藏线路
     * @param uid
     * @param rid
     * @return
     */
    @Override
    public boolean addFavoriteById(int uid, int rid) {
        String sql = "insert into tab_favorite values(?,?,?)";
        int i = template.update(sql, rid, new Date(), uid);
        return i > 0;
    }

    /**
     * 查询是否收藏路线
     * @param uid
     * @param rid
     * @return
     */
    @Override
    public boolean favorited(int uid, int rid) {
        String sql = "select count(*) from tab_favorite where uid = ? and rid = ?";
        int i = template.queryForObject(sql, Integer.class, uid, rid);
        return i > 0;
    }

    /**
     * 通过rid取消线路收藏
     * @param uid
     * @param rid
     * @return
     */
    @Override
    public boolean cancelFavorite(int uid, int rid) {
        String sql = "delete from tab_favorite where uid = ? and rid = ?";
        int i = template.update(sql, uid, rid);
        return i > 0;
    }

    /**
     * 查询线路的总收藏次数
     * @param rid
     * @return
     */
    @Override
    public int favoriteCount(int rid) {
        String sql = "select count from tab_route where rid = ?";
        int count = 0;
        try{
            count = template.queryForObject(sql,Integer.class,rid);
        }catch (Exception e){
            e.printStackTrace();
        }
        return count;
    }

    /**
     * 修改线路的收藏次数
     * @param rid
     * @param count
     * @return
     */
    @Override
    public boolean changeCount(int rid, int count) {
        String sql = "update tab_route set count = ? where rid = ?";
        int i = template.update(sql, count, rid);
        return i > 0;
    }

    /**
     * 查询收藏总条数
     * @param id
     * @return
     */
    @Override
    public int totalCount(int id) {
        String sql = "select count(*) from tab_favorite where uid = ?";
        return template.queryForObject(sql, Integer.class, id);
    }

    /**
     * 查询用户收藏路线的rid
     * @param id
     * @param start
     * @param size
     * @return
     */
    @Override
    public List<Route> queryPage(int id, int start, int size) {
        String sql = "select rid from tab_favorite where uid = ? limit ? , ?";
        List list = null;
        try {
            list = template.query(sql,new BeanPropertyRowMapper<Route>(Route.class),id,start,size);
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
