package com.wzy.quanyoumall.vo;

import lombok.Data;


@Data
public class UserInfoVo {

    private Long userId;

    private String userKey;

    /**
     * 是否临时用户
     */
    private Boolean tempUser = false;

}