package cn.itcast.travel.web.servlet2;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RankService;
import cn.itcast.travel.service.impl.RankServiceImpl;
import cn.itcast.travel.web.servlet2.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Zhangzy
 * @CreateDate: 2018/12/8 9:24
 * @Description: 排行榜相关类
 */
@WebServlet("/rank/*")
public class RankServlet extends BaseServlet {
    private RankService service = new RankServiceImpl();
    /**
     * 收藏排行榜
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void favorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取参数
        String currentPage = request.getParameter("currentPage");
        String rnameKey = request.getParameter("rnameKey");
        String moneyStart = request.getParameter("moneyStart");
        String moneyEnd = request.getParameter("moneyEnd");
        //创建返回数据对象
        ResultInfo result = new ResultInfo();
        PageBean<Route> page = service.queryPage(currentPage,rnameKey,moneyStart,moneyEnd);
        result.setStatus(true);
        result.setData(page);
        goResult(result,response);
    }
}
