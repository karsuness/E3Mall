package com.e3mall.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @author WJX
 * @since 2018/5/2 20:17
 */

/**
 * solr中搜索结果
 */
public class SearchResult implements Serializable {
    /**
     * 总记录数
     */
    private long recordCount;
    /**
     * 总页数
     */
    private int totalPages;
    /**
     * item list
     */
    private List<SearchItem> itemList;

    public long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(long recordCount) {
        this.recordCount = recordCount;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<SearchItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<SearchItem> itemList) {
        this.itemList = itemList;
    }
}
