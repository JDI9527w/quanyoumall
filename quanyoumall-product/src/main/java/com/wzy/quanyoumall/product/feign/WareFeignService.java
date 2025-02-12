package com.wzy.quanyoumall.product.feign;

import com.wzy.quanyoumall.common.to.SkuStockTO;
import com.wzy.quanyoumall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("quanyoumall-ware")
public interface WareFeignService {
    /**
     * 通过 skuIds 查询是否有库存
     *
     * @param skuIds
     * @return
     */
    @PostMapping("/infoBySkuId")
    R<List<SkuStockTO>> infoBySkuId(@RequestBody List<Long> skuIds);
}
