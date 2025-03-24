package com.wzy.quanyoumall.order.feign;

import com.wzy.quanyoumall.common.utils.R;
import com.wzy.quanyoumall.order.vo.FareVo;
import com.wzy.quanyoumall.order.vo.WareSkuLockVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("quanyoumall-ware")
public interface WareFeignService {
    /**
     * 通过收货地址id获取运费信息
     *
     * @param addrId
     * @return
     */
    @GetMapping("/ware/waresku/fare/{addrId}")
    FareVo getFareByAddrId(@PathVariable("addrId") Long addrId);

    /**
     * 锁定库存
     *
     * @param lockVo
     * @return
     */
    @PostMapping("/ware/waresku/lockStock")
    R lockStockByLockVo(@RequestBody WareSkuLockVo lockVo);
}
