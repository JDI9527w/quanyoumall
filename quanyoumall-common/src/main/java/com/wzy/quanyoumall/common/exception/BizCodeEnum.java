package com.wzy.quanyoumall.common.exception;

/***
 * 错误码和错误信息定义类
 * 1. 错误码定义规则为5为数字
 * 2. 前两位表示业务场景，最后三位表示错误码。例如：100001。10:通用 001:系统未知异常
 * 3. 维护错误码后需要维护错误描述，将他们定义为枚举形式
 * 错误码列表：
 *  10: 通用
 *      001：参数格式校验
 *      002: 验证码请求频繁
 *      003: 用户名占用
 *      004: 手机号已注册
 *  11: 商品
 *  12: 订单
 *  13: 购物车
 *  14: 物流
 *  15: 用户
 *  16: 仓储
 *
 */
public enum BizCodeEnum {
    UNKNOW_EXCEPTION(10000, "未知异常"),
    VALID_EXCEPTION(10001, "参数校验失败"),
    SMS_CODE_EXCEPTION(10002, "验证码发送失败,请求频繁"),
    PRODUCT_UP_EXCEPTION(11000, "商品上架失败"),
    PRODUCT_CATALOG_DEL_EXCEPTION(11100,"商品分类删除异常"),
    USER_EXIST_EXCEPTION(15001,"用户名已存在"),
    PHONE_EXIST_EXCEPTION(15002,"手机号已注册"),
    CHECK_USER_ERROR_EXCEPTION(15003,"用户名或密码错误"),
    OUT_OF_STOCK_EXCEPTION(16001,"商品库存不足");
    private final int code;
    private final String msg;

    BizCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
