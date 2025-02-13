package com.wzy.quanyoumall.search.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import com.wzy.quanyoumall.search.constant.EsConstant;
import com.wzy.quanyoumall.search.service.ProductSaveService;
import com.wzy.quanyoumall.search.vo.SkuEsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class ProductSaveServiceImpl implements ProductSaveService {

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    @Override
    public boolean productUp(List<SkuEsVo> skuEsVoList) throws IOException {
        BulkRequest.Builder br = new BulkRequest.Builder();
        for (SkuEsVo skuEsVo : skuEsVoList) {
            br.operations(op -> op
                    .index(idx -> idx
                            .index(EsConstant.PRODUCT_INEDX)
                            .id(skuEsVo.getSkuId().toString())
                            .document(skuEsVo)
                    )
            );
        }
        BulkResponse result = elasticsearchClient.bulk(br.build());
        boolean flag = true;
        if (result.errors()) {
            log.error("Bulk had errors");
            for (BulkResponseItem item : result.items()) {
                if (item.error() != null) {
                    log.error("itemId:" + item.id() + "error" + item.error().reason());
                }
            }
            flag = false;
        }
        return flag;
    }
}
