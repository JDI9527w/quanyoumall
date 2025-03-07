package com.wzy.quanyoumall.vo;

import lombok.Data;

@Data
public class GiteeUserResp {

    private String accessToken;

    private String tokenType;

    private String expiresI;

    private String refreshToken;

    private String scope;

    private String createdAt;
}
