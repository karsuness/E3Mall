package com.e3mall.common.pojo;

import java.io.Serializable;

/**
 * @author WJX
 * @since 2018/5/2 20:18
 */

/**
 * solr中item属性
 */
public class SearchItem implements Serializable {
    /**
     * item id
     */
    private String id;

    /**
     * item 标题
     */
    private String title;

    /**
     * 卖点
     */
    private String sell_point;

    /**
     * 价格
     */
    private long price;

    /**
     * 图片
     */
    private String image;

    /**
     * 分类名
     */
    private String category_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSell_point() {
        return sell_point;
    }

    public void setSell_point(String sell_point) {
        this.sell_point = sell_point;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String[] getImages() {
        if (image != null && !"".equals(image)) {
            String[] strings = image.split(",");
            return strings;
        }
        return null;
    }
}
