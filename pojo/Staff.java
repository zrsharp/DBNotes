package com.zero.tianmao.pojo;

// 员工客服
public class Staff {
    // 员工id(工号)
    String staffId;

    // 店铺id
    String storeId;

    // 员工用户名，用于登陆
    String staffUsername;

    // 员工密码
    String password;

    // 昵称
    String nickname;

    // 加盐，用于密码加密
    String salt;

    // 直接上级的员工id
    String directSuperiorId;

    // 员工真实姓名
    String realName;

    // 性别
    Boolean sex;

    // 照片
    String photo;

    // 员工手机号
    String phoneNumber;

    // 部门id
    String departmentId;

    // 职务id
    String jobTitleId;

    // 证件号码
    String idCardNumber;

    // 证件类型(比如居民身份证，台胞证，护照等)
    String idCardType;

    // 入职时间
    String entryTime;

    // 电子邮箱
    String email;

    // 办公地点
    String officeLocation;

    // 特长
    String specialty;
}
