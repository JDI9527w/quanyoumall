package com.wzy.quanyoumall.common.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberRespVo {

    /**
     * id
     */
    private Long id;
    /**
     * 会员等级id
     */
    private Long levelId = 1L;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 头像
     */
    private String header;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * 生日
     */
    private LocalDateTime birth;
    /**
     * 所在城市
     */
    private String city;
    /**
     * 职业
     */
    private String job;
    /**
     * 个性签名
     */
    private String sign;
    /**
     * 用户来源
     */
    private Integer sourceType;
    /**
     * 积分
     */
    private Integer integration = 0;
    /**
     * 成长值
     */
    private Integer growth = 0;
    /**
     * 启用状态
     */
    private Integer status;
    /**
     * 注册时间
     */
    private LocalDateTime createTime;

    /**
     * 第三方账号id.
     */
    private String thirdAccountId1;
}