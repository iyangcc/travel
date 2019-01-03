package cn.itcast.travel.service;

public interface RouteFavoriteService {
    /**
     * 收藏线路
     * @param uid
     * @param rid
     * @return
     */
    boolean addFavorite(int uid, String rid);

    /**
     * 查询是否收藏路线
     * @param uid
     * @param rid
     * @return
     */
    boolean favorited(int uid, String rid);

    /**
     * 通过rid取消线路收藏
     * @param uid
     * @param rid
     * @return
     */
    boolean cancelFavorite(int uid, String rid);

    /**
     * 查询线路的总收藏次数
     * @param rid
     * @return
     */
    int favoriteCount(String rid);
}
