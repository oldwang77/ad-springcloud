package com.imooc.ad.search;

import com.imooc.ad.search.vo.SearchRequest;
import com.imooc.ad.search.vo.SearchResponse;

/**
 * 传入一个服务请求，返回一个服务响应
 */
public interface ISearch {
    SearchResponse fetchAds(SearchRequest request);
}
