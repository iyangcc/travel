package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {

    private CategoryDao dao = new CategoryDaoImpl();

    /**
     * 查找所有的分类
     * @return
     */
    @Override
    public List<Category> findAll() {
        //获取jedis连接
        Jedis jedis = new Jedis();
        Set<String> category = jedis.zrange("category", 0, -1);
        if(category==null||category.size()==0){
            //查询数据库
            List<Category> all = dao.findAll();
            //存入Jedis
            for (Category c : all) {
                jedis.zadd("category",c.getCid(),c.getCname());
            }
            return all;
        }
        //查询缓存
        Set<Tuple> tuples = jedis.zrangeWithScores("category", 0, -1);
        ArrayList<Category> list = new ArrayList<>();
        for (Tuple t : tuples) {
            list.add(new Category((int)t.getScore(),t.getElement()));
        }
        return list;
    }
}
