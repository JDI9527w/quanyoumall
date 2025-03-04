package com.wzy.quanyoumall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wzy.quanyoumall.common.exception.PhoneExistException;
import com.wzy.quanyoumall.common.exception.UsernameExistException;
import com.wzy.quanyoumall.member.entity.MemberEntity;
import com.wzy.quanyoumall.member.vo.MemberLoginVo;
import com.wzy.quanyoumall.member.vo.MemberRegisterVo;

/**
 * 会员
 *
 * @author wzy
 * @email
 * @date 2025-01-05 21:40:52
 */
public interface MemberService extends IService<MemberEntity> {

    /**
     * 会员注册
     *
     * @param memberRegisterVo
     */
    void register(MemberRegisterVo memberRegisterVo) throws UsernameExistException, PhoneExistException;

    /**
     * 检测用户名占用
     *
     * @param username
     * @return
     */
    MemberEntity checkUserNameExist(String username);

    /**
     * 检测手机号占用
     *
     * @param phone
     * @return
     */
    MemberEntity checkPhoneExist(String phone);

    /**
     * 检测邮箱占用
     *
     * @param email
     * @return
     */
    MemberEntity checkEmailExist(String email);

    Boolean checkMember(MemberLoginVo memberLoginVo);

}

