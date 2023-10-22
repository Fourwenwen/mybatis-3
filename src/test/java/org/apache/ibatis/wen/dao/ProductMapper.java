package org.apache.ibatis.wen.dao;

import org.apache.ibatis.wen.domain.Product;

import java.util.List;

public interface ProductMapper {

    // 根据id查询商品信息
    Product find(long id);
    // 根据名称搜索商品信息
    List<Product> findByName(String name);
    // 保存商品信息
    long save(Product product);

}
