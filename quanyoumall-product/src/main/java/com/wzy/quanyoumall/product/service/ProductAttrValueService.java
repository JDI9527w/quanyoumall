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

    /**
     * 通过spuid查询attr集合
     *
     * @param spuId
     * @return
     */
    List<ProductAttrValueEntity> getAttrListBySpuId(Long spuId);

    void updateSpuAttr(Long spuId, List<ProductAttrValueEntity> paveList);

    /**
     * 通过 spuid查询对应可供检索的属性
     *
     * @param spuId
     * @return
     */
    List<ProductAttrValueEntity> listGetNeedSearchAttrBySpuId(Long spuId);
}

