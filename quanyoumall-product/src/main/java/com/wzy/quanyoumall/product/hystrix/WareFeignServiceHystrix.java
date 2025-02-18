package com.wzy.quanyoumall.product.hystrix;

import com.wzy.quanyoumall.common.to.SkuStockTO;
import com.wzy.quanyoumall.common.utils.R;
import com.wzy.quanyoumall.product.feign.WareFeignService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("WareFeignServiceHystrix")
public class WareFeignServiceHystrix implements WareFeignService {
    @Override
    public R infoBySkuId(List<Long> skuIds) {
        List<SkuStockTO> collect = skuIds.stream().map(skuId -> {
            SkuStockTO skuStockTO = new SkuStockTO();
            skuStockTO.setSkuId(skuId);
            skuStockTO.setHasStock(false);
            return skuStockTO;
        }).collect(Collectors.toList());
        return R.ok().put("data",collect);
    }
}
