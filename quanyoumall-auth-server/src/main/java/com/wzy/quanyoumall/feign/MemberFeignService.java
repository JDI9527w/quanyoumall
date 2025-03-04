package com.wzy.quanyoumall.feign;

import com.wzy.quanyoumall.common.utils.R;
import com.wzy.quanyoumall.vo.UserLoginVo;
import com.wzy.quanyoumall.vo.UserRegisterVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("quanyoumall-member")
public interface MemberFeignService {
    @PostMapping("member/member/register")
    R registerMember(@RequestBody UserRegisterVo userRegisterVo);

    @PostMapping("member/member/memberLogin")
    R checkMember(@RequestBody UserLoginVo userLoginVo);
}
