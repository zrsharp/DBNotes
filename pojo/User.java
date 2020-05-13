package com.zero.tianmao.pojo;

import java.sql.Date;
import java.sql.Timestamp;

public class User {
    // 用户id
    String userId;

    // 用户名(唯一)，用于登陆
    String username;

    // 昵称(可重复)
    String nickname;

    // 密码
    String password;

    // 加盐，用于密码加密
    String salt;

    // 支付宝的token
    String alipayToken;

    // 头像的 url
    String portrait;

    Date birthday;

    // 性别，false表示女，true表示男(对应数据库中的0和1)
    Boolean sex;

    String phoneNumber;

    String email;

    // 注册时间
    Timestamp registrationTime;

    // 最后一次登陆时间
    Timestamp lastLoginTime;

    // 是否实名认证
    Boolean isRealNameAuth;

    // 信誉积分
    Integer creditPoints;

    // 备注
    String remarks;

}
