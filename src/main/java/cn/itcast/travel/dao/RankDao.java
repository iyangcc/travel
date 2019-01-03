package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RankDao {
    /**
     * 查询线路总条数
     * @return
     * @param rnameKey
     * @param money_start
     * @param money_end
     */
    int totalCount(String rnameKey, int money_start, int money_end);

    /**
     * 分页查询线路收藏
     * @param start
     * @param size
     * @param rnameKey
     * @param money_start
     * @param money_end
     * @return
     */
    List<Route> queryPage(int start, int size, String rnameKey, int money_start, int money_end);
}
