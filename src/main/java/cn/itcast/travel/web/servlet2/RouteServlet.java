package cn.itcast.travel.web.servlet2;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.RankService;
import cn.itcast.travel.service.RouteFavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.RankServiceImpl;
import cn.itcast.travel.service.impl.RouteFavoriteServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;
import cn.itcast.travel.web.servlet2.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @Author: Zhangzy
 * @CreateDate: 2018/12/4 19:22
 * @Description: 推荐线路查询
 */
@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {

    private RouteService service = new RouteServiceImpl();
    private RouteFavoriteService rf_service = new RouteFavoriteServiceImpl();
    private RankService rank_service = new RankServiceImpl();

    /**
     * 分页查询推荐路线
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取所有参数
        String cid = request.getParameter("cid");
        String currentPage = request.getParameter("currentPage");
        String pageSize = request.getParameter("pageSize");
        String keyWords = request.getParameter("keyWords");
        PageBean<Route> route = service.queryPage(cid, currentPage, pageSize, keyWords);
        goResult(route, response);
    }

    /**
     * 查询详情页信息
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取线路id
        String rid = request.getParameter("rid");
        Route route = service.findOne(rid);
        ResultInfo result = new ResultInfo();
        result.setStatus(true);
        result.setData(route);
        result.setErrorMsg(null);
        goResult(result, response);
    }

    /**
     * 收藏路线
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取已登录用户
        User user = loggedUser(request);
        //获取参数
        String rid = request.getParameter("rid");
        //数据返回对象
        ResultInfo result = new ResultInfo();
        if(user!=null){
            boolean bool = rf_service.addFavorite( user.getUid(),rid);
            result.setStatus(bool);
        }else{
            result.setStatus(false);
        }
        goResult(result,response);
    }

    /**
     * 查询是否收藏路线
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取参数
        User user = loggedUser(request);
        String rid = request.getParameter("rid");
        //数据返回对象
        ResultInfo result = new ResultInfo();
        if(user!=null){
            boolean bool = rf_service.favorited(user.getUid(),rid);
            result.setStatus(bool);
        }
        goResult(result,response);
    }

    /**
     * 取消线路收藏
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void cancelFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取参数
        User user = loggedUser(request);
        String rid = request.getParameter("rid");
        //数据返回对象
        ResultInfo result = new ResultInfo();
        if(user!=null){
            boolean bool = rf_service.cancelFavorite(user.getUid(),rid);
            result.setStatus(bool);
        }
        goResult(result,response);
    }

    /**
     * 查询线路的收藏次数
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void favoriteCount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取参数
        String rid = request.getParameter("rid");
        //数据返回对象
        ResultInfo result = new ResultInfo();
        int count = rf_service.favoriteCount(rid);
        result.setStatus(true);
        result.setData(count);
        goResult(result,response);
    }

    /**
     * 首页最新旅游
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void latest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResultInfo result = new ResultInfo();
        List<Route> list = service.latest();
        result.setStatus(true);
        result.setData(list);
        goResult(result,response);
    }

    /**
     * 首页人气旅游
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void sentiment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResultInfo result = new ResultInfo();
        PageBean<Route> page = rank_service.queryPage("1","","","");
        //只返回前4条数据
        page.setData(page.getData().subList(0,4));
        result.setStatus(true);
        result.setData(page);
        goResult(result,response);
    }

    /**
     * 首页主题旅游
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void theme(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResultInfo result = new ResultInfo();
        List<Route> list = service.theme();
        result.setStatus(true);
        result.setData(list);
        goResult(result,response);
    }

    /**
     * 首页通过rid获取旅游列表
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void routeList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取rid
        String cid = request.getParameter("cid");
        //创建返回数据对象
        ResultInfo result = new ResultInfo();
        List<Route> list = service.listByRid(cid);
        result.setStatus(true);
        result.setData(list);
        goResult(result,response);
    }

    /**
     * 通过cid查询当前分类的热门路线
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void hotRoute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取rid
        String cid = request.getParameter("cid");
        //创建返回数据对象
        ResultInfo result = new ResultInfo();
        List<Route> list = service.hotRouteByCid(cid);
        result.setStatus(true);
        result.setData(list);
        goResult(result,response);
    }
}
