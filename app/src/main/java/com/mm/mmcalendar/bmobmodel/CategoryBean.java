package com.mm.mmcalendar.bmobmodel;

/**
 * Created by Administrator on 2016/10/30.
 */
public class CategoryBean extends BaseBmob{

    public static final String restApiUrl="https://api.bmob.cn/1/classes/Category";

    public static final String ITEM_KEY="category";

    protected Integer postionOrder;

    protected String categoryId;

    protected String categoryName;

    protected String iconUrl;



    public Integer getPostionOrder() {
        return postionOrder;
    }

    public void setPostionOrder(Integer postionOrder) {
        this.postionOrder = postionOrder;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() { return categoryName;  }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
}
