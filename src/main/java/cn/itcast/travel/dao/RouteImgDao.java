package cn.itcast.travel.dao;

import cn.itcast.travel.domain.RouteImg;

import java.util.List;

public interface RouteImgDao {

    /**
     * 获取图片列表
     * @param id
     * @return
     */
    List<RouteImg> findById(int id);
}
