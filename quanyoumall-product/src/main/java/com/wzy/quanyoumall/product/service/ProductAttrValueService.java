package com.wzy.quanyoumall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wzy.quanyoumall.common.utils.PageUtils;
import com.wzy.quanyoumall.product.entity.ProductAttrValueEntity;
import com.wzy.quanyoumall.product.vo.BaseAttrVo;

import java.util.List;
import java.util.Map;

/**
 * spu属性值
 *
 * @author wzy
 * @email 
 * @date 2025-01-05 18:48:48
 */
public interface ProductAttrValueService extends IService<ProductAttrValueEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveAttrBatch(Long spuId, List<BaseAttrVo> baseAttrs);
}

