package com.zero.tianmao.pojo;

import java.sql.Timestamp;

public class CommodityPicture {
    // 商品id
    String commodityId;

    // 图片url
    String picture;

    // 图片的秩，用于排序
    Integer rank;

    // 上传时间
    Timestamp uploadTime;
}
