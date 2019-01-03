package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Category;

import java.util.List;

public interface CategoryDao {
    /**
     * 查找所有分类
     * @return
     */
    List<Category> findAll();

    /**
     * 通过id查找分类信息
     * @param cid
     * @return
     */
    Category findOne(int cid);
}
