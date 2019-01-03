package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.RouteFavoriteDao;
import cn.itcast.travel.dao.impl.RouteFavoriteDaoImpl;
import cn.itcast.travel.service.RouteFavoriteService;

public class RouteFavoriteServiceImpl implements RouteFavoriteService {
    private RouteFavoriteDao rf_dao = new RouteFavoriteDaoImpl();
    /**
     * 收藏线路
     * @param uid
     * @param rid
     * @return
     */
    @Override
    public boolean addFavorite(int uid, String rid) {
        boolean a = false;
        boolean b = rf_dao.addFavoriteById(uid, Integer.parseInt(rid));
        if(b){
            int count = favoriteCount(rid);
            count += 1;
            a = rf_dao.changeCount(Integer.parseInt(rid), count);
        }
        return a&&b;
    }

    /**
     * 查询是否收藏路线
     * @param uid
     * @param rid
     * @return
     */
    @Override
    public boolean favorited(int uid, String rid) {
        return rf_dao.favorited(uid,Integer.parseInt(rid));
    }

    /**
     * 通过rid取消线路收藏
     * @param uid
     * @param rid
     * @return
     */
    @Override
    public boolean cancelFavorite(int uid, String rid) {
        boolean a = false;
        boolean b = rf_dao.cancelFavorite(uid, Integer.parseInt(rid));
        if(b){
            int count = favoriteCount(rid);
            count = count==0?0:count-1;
            a = rf_dao.changeCount(Integer.parseInt(rid), count);
        }
        return a&&b;
    }

    /**
     * 查询线路的总收藏次数
     * @param rid
     * @return
     */
    @Override
    public int favoriteCount(String rid) {
        return rf_dao.favoriteCount(Integer.parseInt(rid));
    }
}
