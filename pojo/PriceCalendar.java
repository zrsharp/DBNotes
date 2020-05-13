package com.zero.tianmao.pojo;

import java.math.BigDecimal;
import java.sql.Timestamp;

// 商品价格日历
public class PriceCalendar {
    // 价格日历id
    String pcId;

    // 商品id
    String commodityId;

    // 价格
    BigDecimal price;

    // 时间
    Timestamp time;
}
