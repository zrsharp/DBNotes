package com.zero.tianmao.pojo;

import java.sql.Timestamp;

// 实名认证
public class RealNameAuth {
    // 认证id
    String authId;

    // 用户id
    String userId;

    // 真实姓名
    String realName;

    // 证件号码
    String idCardNumber;

    // 证件类型(比如居民身份证，台胞证，护照等)
    String idCardType;

    // 认证时间
    Timestamp authTime;
}
