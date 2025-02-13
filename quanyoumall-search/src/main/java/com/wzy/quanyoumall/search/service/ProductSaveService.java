package com.wzy.quanyoumall.search.service;

import com.wzy.quanyoumall.search.vo.SkuEsVo;

import java.io.IOException;
import java.util.List;

public interface ProductSaveService {

    boolean productUp(List<SkuEsVo> skuEsVoList) throws IOException;
}
