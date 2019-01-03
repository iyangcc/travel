package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

public interface RankService {
    /**
     * 分页获取排行信息
     * @param currentPage
     * @param rnameKey
     * @param moneyStart
     * @param moneyEnd
     * @return
     */
    PageBean<Route> queryPage(String currentPage, String rnameKey, String moneyStart, String moneyEnd);
}
