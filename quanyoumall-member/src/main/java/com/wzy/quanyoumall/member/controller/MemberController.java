package com.wzy.quanyoumall.member.controller;

import com.wzy.quanyoumall.common.constant.bizCodeEnum;
import com.wzy.quanyoumall.common.exception.PhoneExistException;
import com.wzy.quanyoumall.common.exception.UsernameExistException;
import com.wzy.quanyoumall.common.utils.R;
import com.wzy.quanyoumall.member.entity.MemberEntity;
import com.wzy.quanyoumall.member.service.MemberService;
import com.wzy.quanyoumall.member.vo.MemberLoginVo;
import com.wzy.quanyoumall.member.vo.MemberRegisterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


/**
 * 会员
 *
 * @author wzy
 * @email
 * @date 2025-01-05 21:40:52
 */
@RestController
@RequestMapping("member/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    /**
     * 列表
     */


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        MemberEntity member = memberService.getById(id);

        return R.ok().put("member", member);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody MemberEntity member) {
        memberService.save(member);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody MemberEntity member) {
        memberService.updateById(member);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        memberService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    @PostMapping("/register")
    public R registerMember(@RequestBody MemberRegisterVo memberRegisterVo) {
        try {
            memberService.register(memberRegisterVo);
        } catch (UsernameExistException e) {
            e.printStackTrace();
            return R.error(bizCodeEnum.USER_EXIST_EXCEPTION.getCode(), bizCodeEnum.USER_EXIST_EXCEPTION.getMsg());
        } catch (PhoneExistException e) {
            e.printStackTrace();
            return R.error(bizCodeEnum.PHONE_EXIST_EXCEPTION.getCode(), bizCodeEnum.PHONE_EXIST_EXCEPTION.getMsg());
        }
        return R.ok();
    }

    @PostMapping("/memberLogin")
    public R checkMember(@RequestBody MemberLoginVo memberLoginVo) {
        Boolean flag = memberService.checkMember(memberLoginVo);
        if (flag) {
            return R.ok();
        }
        return R.error(bizCodeEnum.CHECK_USER_ERROR_EXCEPTION.getCode(), bizCodeEnum.CHECK_USER_ERROR_EXCEPTION.getMsg());
    }


}
