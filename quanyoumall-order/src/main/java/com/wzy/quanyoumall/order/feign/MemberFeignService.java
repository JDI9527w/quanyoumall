package com.wzy.quanyoumall.order.feign;

import com.wzy.quanyoumall.common.utils.R;
import com.wzy.quanyoumall.order.vo.MemberAddressVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient("quanyoumall-member")
public interface MemberFeignService {

    @GetMapping("/member/memberreceiveaddress/listGetMemberReceiveAddr/{memberId}")
    List<MemberAddressVo> listGetMemberReceiveAddr(@PathVariable("memberId") Long memberId);

    @RequestMapping("/member/memberreceiveaddress/info/{id}")
    R info(@PathVariable("id") Long id);

}
