package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteDao {
    /**
     * 查询分页数据的总数据条数
     * @param id
     * @param keyWords
     * @return
     */
    int totalCount(int id, String keyWords);

    /**
     * 分页查询的数据
     * @param id
     * @param start
     * @param size
     * @param keyWords
     * @return
     */
    List<Route> queryPage(int id, int start, int size, String keyWords);

    /**
     * 通过id获取线路信息
     * @param id
     * @return
     */
    Route findById(int id);

    /**
     * 最新旅游线路
     * @param count
     * @return
     */
    List<Route> findLatest(int count);

    /**
     * 主题旅游线路
     * @param count
     * @return
     */
    List<Route> findTheme(int count);

    /**
     * 通过cid获取线路列表
     * @param cid
     * @param count
     * @return
     */
    List<Route> listByRid(int cid, int count);

    /**
     * 通过cid查询热门
     * @param cid
     * @param count
     * @return
     */
    List<Route> hotRouteByCid(int cid, int count);
}
