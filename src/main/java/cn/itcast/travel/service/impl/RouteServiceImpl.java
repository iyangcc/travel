package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.*;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    private RouteDao dao = new RouteDaoImpl();
    private CategoryDao cDao = new CategoryDaoImpl();
    private RouteImgDao imgDao = new RouteImgDaoImpl();
    private SellerDao sellerDao = new SellerDaoImpl();

    /**
     * 分页查询推荐路线
     * @param cid
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public PageBean<Route> queryPage(String cid, String currentPage, String pageSize, String keyWords) {
        int id = 0;
        int current = 1;
        int size = 10;
        if(cid!=null&&!"".equals(cid)){
            id = Integer.parseInt(cid);
        }
        if(currentPage!=null&&!"".equals(currentPage)){
            current = Integer.parseInt(currentPage);
        }
        if(pageSize!=null&&!"".equals(pageSize)){
            size = Integer.parseInt(pageSize);
        }
        int start = (current - 1) * size;
        int totalCount = dao.totalCount(id,keyWords);
        int totalPage = totalCount % size == 0 ? totalCount / size : (totalCount / size) + 1;
        List<Route> list = dao.queryPage(id,start,size,keyWords);
        return new PageBean<Route>(totalCount,totalPage,current,size,id,list);
    }

    /**
     * 查询详情页信息
     * @param rid
     * @return
     */
    @Override
    public Route findOne(String rid) {
        int id = Integer.parseInt(rid);
        Route route = dao.findById(id);
        int cid = route.getCid();
        int sid = route.getSid();
        Category c = cDao.findOne(cid);
        List<RouteImg> imgList = imgDao.findById(id);
        Seller seller = sellerDao.findById(sid);
        route.setCategory(c);
        route.setRouteImgList(imgList);
        route.setSeller(seller);
        return route;
    }

    /**
     * 最新旅游线路
     * @return
     */
    @Override
    public List<Route> latest() {
        //定义要查询的条数
        int count = 4;
        return dao.findLatest(count);
    }

    /**
     * 主题线路
     * @return
     */
    @Override
    public List<Route> theme() {
        //定义要查询的条数
        int count = 4;
        return dao.findTheme(count);
    }

    /**
     * 通过rid获取线路列表
     * @param cid
     * @return
     */
    @Override
    public List<Route> listByRid(String cid) {
        //定义要查询的条数
        int count = 6;
        return dao.listByRid(Integer.parseInt(cid),count);
    }

    /**
     * 通过cid查询热门推荐
     * @param cid
     * @return
     */
    @Override
    public List<Route> hotRouteByCid(String cid) {
        //定义查询的条数
        int count = 5;
        return dao.hotRouteByCid(Integer.parseInt(cid),count);
    }
}
