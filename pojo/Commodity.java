package com.zero.tianmao.pojo;

import java.sql.Timestamp;

// 商品
public class Commodity {
    // 商品id
    String commodityId;

    // 店铺id
    String storeId;

    // 商品价格
    Double price;

    // 库存数量
    Integer inventoryQuantity;

    // 商品状态标识
    String state;

    // 发布时间
    Timestamp publishTime;

    // 商品介绍
    String introduction;
}
