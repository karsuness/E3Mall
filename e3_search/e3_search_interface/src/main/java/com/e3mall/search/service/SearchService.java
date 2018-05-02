package com.e3mall.search.service;

import com.e3mall.common.pojo.SearchResult;

/**
 * @author WJX
 * @since 2018/5/2 20:22
 */
public interface SearchService {
    /**
     * 根据关键字搜索
     * @param keyword 关键字
     * @param page 页号
     * @param rows 每行多少
     * @return 搜索的结果
     * @throws Exception
     */
    SearchResult search(String keyword, int page, int rows)  throws Exception;
}
