package com.wzy.quanyoumall.search.service;

import com.wzy.quanyoumall.search.vo.SearchParam;
import com.wzy.quanyoumall.search.vo.SearchResult;

public interface MallSearchService {
    SearchResult search(SearchParam param);
}
