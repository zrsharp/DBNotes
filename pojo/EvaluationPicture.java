package com.zero.tianmao.pojo;

import java.sql.Timestamp;

public class EvaluationPicture {
    // 评价图片id
    String evaluationPictureId;

    // 商品评价id
    String evaluationId;

    // 图片url
    String picture;

    // 图片的秩，用于排序
    Integer rank;

    // 上传时间
    Timestamp uploadTime;
}
