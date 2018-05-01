package com.e3mall.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @author WJX
 * @since 2018/4/30 22:37
 */

/**
 * 返回easyui数据的结果类
 */
public class EasyUIDataGridResult implements Serializable {
    /**
     * 数据总量
     */
    private long total;

    /**
     * 每页显示行数
     */
    private List rows;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}
