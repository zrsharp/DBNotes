package com.zero.tianmao.pojo;

// 多对多中间表，商品和类别的映射
public class CommodityToCategory {
    String id;

    // 商品类别id
    String categoryId;

    // 商品id
    String commodityId;
}
