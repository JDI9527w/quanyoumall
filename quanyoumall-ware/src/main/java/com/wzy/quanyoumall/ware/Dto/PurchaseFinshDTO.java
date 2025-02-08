package com.wzy.quanyoumall.ware.Dto;

import lombok.Data;

import java.util.List;

@Data
public class PurchaseFinshDTO {
    private Long purchaseId;
    private List<PurchaseFinshDetailDTO> finshDetailDTOList;
}
