package com.wzy.quanyoumall.authServer.vo;

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
