package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.RankDao;
import cn.itcast.travel.dao.impl.RankDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RankService;

import java.util.List;

public class RankServiceImpl implements RankService {

    private RankDao dao = new RankDaoImpl();

    /**
     * 分页获取排行信息
     * @param currentPage
     * @param rnameKey
     * @param moneyStart
     * @param moneyEnd
     * @return
     */
    @Override
    public PageBean<Route> queryPage(String currentPage, String rnameKey, String moneyStart, String moneyEnd) {
        int current = 1;
        int money_start = -1;
        int money_end = -1;
        int size = 8;

        if(currentPage!=null&&!"".equals(currentPage)){
            current = Integer.parseInt(currentPage);
        }
        if(moneyStart!=null&&!"".equals(moneyStart)){
            money_start = Integer.parseInt(moneyStart);
        }
        if(moneyEnd!=null&&!"".equals(moneyEnd)){
            money_end = Integer.parseInt(moneyEnd);
        }

        int totalCount = dao.totalCount(rnameKey,money_start,money_end);
        int totalPage = totalCount % size == 0 ? totalCount /size : (totalCount / size) + 1;
        int start = (current - 1) * size;

        List<Route> list = dao.queryPage(start,size,rnameKey,money_start,money_end);

        for (int i = 0; i < list.size(); i++) {
            Route r = list.get(i);
            r.setCid(start+i+1);
            list.set(i,r);
        }
        return new PageBean<Route>(totalCount,totalPage,current,size,0,list);
    }
}
