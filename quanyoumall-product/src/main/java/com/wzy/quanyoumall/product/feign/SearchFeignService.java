package com.wzy.quanyoumall.product.feign;

import com.wzy.quanyoumall.common.utils.R;
import com.wzy.quanyoumall.product.vo.es.SkuEsVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.List;

@FeignClient("quanyoumall-search")
public interface SearchFeignService {
    @PostMapping("/es/save/product")
    R productUp(@RequestBody List<SkuEsVo> skuEsVoList) throws IOException;
}
