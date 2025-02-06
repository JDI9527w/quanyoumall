package com.wzy.quanyoumall.ware.Dto;

import lombok.Data;

import java.util.List;

@Data
public class PurchaseMergeDTO {
    /**
     * 采购信息ID集合
     */
    private List<Long> purchaseDetailIdList;
    /**
     * 采购单id
     */
    private Long purchaseId;
}
