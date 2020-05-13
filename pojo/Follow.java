package com.zero.tianmao.pojo;

import java.sql.Timestamp;

// 店铺关注
public class Follow {
    // id
    String followId;

    // 关注者用户id
    String followerId;

    // 被关注的店铺id
    String storeId;

    // 关注时间
    Timestamp followTime;
}
