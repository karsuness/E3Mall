package com.e3mall.common.pojo;

/**
 * @author WJX
 * @since 2018/5/1 15:49
 */

import java.io.Serializable;

/**
 * easyui节点类
 */
public class EasyUITreeNode implements Serializable {
    /**
     * 节点id
     */
    private long id;
    /**
     * 节点内容
     */
    private String text;
    /**
     * 节点状态，open或closed
     */
    private String state;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
