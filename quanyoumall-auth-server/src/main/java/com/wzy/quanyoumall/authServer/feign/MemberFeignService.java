package com.wzy.quanyoumall.authServer.feign;

import com.wzy.quanyoumall.authServer.vo.GiteeUserVo;
import com.wzy.quanyoumall.authServer.vo.UserLoginVo;
import com.wzy.quanyoumall.authServer.vo.UserRegisterVo;
import com.wzy.quanyoumall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("quanyoumall-member")
public interface MemberFeignService {
    @PostMapping("member/member/register")
    R registerMember(@RequestBody UserRegisterVo userRegisterVo);

    @PostMapping("member/member/memberLogin")
    R login(@RequestBody UserLoginVo userLoginVo);

    @PostMapping("member/member/loginBySocial")
    R loginBySocialAccount(@RequestBody GiteeUserVo giteeUserVo);
}
