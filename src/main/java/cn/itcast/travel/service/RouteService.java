package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteService {
    /**
     * 分页查询推荐路线
     * @param cid
     * @param currentPage
     * @param pageSize
     * @return
     */
    PageBean<Route> queryPage(String cid, String currentPage, String pageSize,String keyWords);

    /**
     * 查询详情页信息
     * @param rid
     * @return
     */
    Route findOne(String rid);

    /**
     * 最新旅游线路
     * @return
     */
    List<Route> latest();

    /**
     * 主题线路
     * @return
     */
    List<Route> theme();

    /**
     * 通过cid获取线路列表
     * @param cid
     * @return
     */
    List<Route> listByRid(String cid);

    /**
     * 通过cid查询热门推荐
     * @param cid
     * @return
     */
    List<Route> hotRouteByCid(String cid);
}
