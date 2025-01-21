package com.wzy.quanyoumall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wzy.quanyoumall.common.utils.PageUtils;
import com.wzy.quanyoumall.product.entity.SpuInfoEntity;
import com.wzy.quanyoumall.product.vo.SpuSaveVo;

import java.util.Map;

/**
 * spu信息
 *
 * @author wzy
 * @email
 * @date 2025-01-05 18:48:48
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveBySpuSaveVo(SpuSaveVo spuSaveVo);
}

