package com.wzy.quanyoumall.ware.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wzy.quanyoumall.common.to.SkuStockTO;
import com.wzy.quanyoumall.ware.entity.WareOrderTaskDetailEntity;
import com.wzy.quanyoumall.ware.entity.WareSkuEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.annotation.Nullable;
import java.util.List;

/**
 * 商品库存
 *
 * @author wzy
 * @email
 * @date 2025-01-05 21:45:59
 */
@Mapper
public interface WareSkuMapper extends BaseMapper<WareSkuEntity> {

    List<SkuStockTO> getStokeBySkuIds(@Param("skuIds") List<Long> skuIds);

    int lockStockByItem(@Param("skuId") Long skuId, @Param("count") Integer count, @Param("wareId") Long wareId);

    List<Long> listGetWareSkuIdByArgs(@Param("skuId") Long skuId, @Nullable @Param("num") Integer num);

    int rollbackSingle(@Param("wareOrderTaskDetailEntity") WareOrderTaskDetailEntity wareOrderTaskDetailEntity);
}
