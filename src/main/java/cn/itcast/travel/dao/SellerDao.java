package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Seller;

public interface SellerDao {
    /**
     * 通过id查询商家信息
     * @param sid
     * @return
     */
    Seller findById(int sid);
}
