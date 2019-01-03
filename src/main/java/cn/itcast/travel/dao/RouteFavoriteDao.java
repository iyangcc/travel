package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteFavoriteDao {
    /**
     * 通过uid收藏线路
     * @param uid
     * @param rid
     * @return
     */
    boolean addFavoriteById(int uid, int rid);

    /**
     * 查询是否收藏路线
     * @param uid
     * @param rid
     * @return
     */
    boolean favorited(int uid, int rid);

    /**
     * 通过rid取消线路收藏
     * @param uid
     * @param rid
     * @return
     */
    boolean cancelFavorite(int uid, int rid);

    /**
     * 查询线路的总收藏次数
     * @param rid
     * @return
     */
    int favoriteCount(int rid);

    /**
     * 修改线路的收藏次数
     * @param rid
     * @param count
     * @return
     */
    boolean changeCount(int rid,int count);

    /**
     * 查询收藏总条数
     * @param id
     * @return
     */
    int totalCount(int id);

    /**
     * 查询用户收藏路线的rid
     * @param id
     * @param start
     * @param size
     * @return
     */
    List<Route> queryPage(int id, int start, int size);

}
