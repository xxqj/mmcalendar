package com.mm.mmcalendar.bmobmodel;

import com.mm.mmcalendar.bmobmodel.CategoryBean;

/**
 * Created by Administrator on 2016/10/30.
 */
public class SubCategoryBean extends BaseBmob {

    public static final String restApiUrl="https://api.bmob.cn/1/classes/SubCategory";

    public static final String ITEM_KEY="subCategory";

    public static final String aqqId="wRViQQQZ";//经期护理

    public static final String ycqId="rvlW111C";//孕期护理

    public static final String yedyId="mSgEGGGa";//育儿电影

    public static final String shdyId="LW2cIIIY";//生活电影

    public static final String jkyyId="RKdXGGGL";//育婴健康

    public static final String shyyId="8BivAAAK";//育婴生活

    protected Integer postionOrder;

    protected String subCategoryId;

    protected String subCategoryName;

    protected String iconUrl;

    protected String categoryId;

    protected String categoryName;

    private CategoryBean categoryBean;

    public Integer getPostionOrder() {
        return postionOrder;
    }

    public void setPostionOrder(Integer postionOrder) {
        this.postionOrder = postionOrder;
    }

    public String getSubCategoryId() {return subCategoryId;}

    public void setSubCategoryId(String subCategoryId) {this.subCategoryId = subCategoryId;}

    public String getSubCategoryName() {return subCategoryName;}

    public void setSubCategoryName(String subCategoryName) {this.subCategoryName = subCategoryName;}

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public CategoryBean getCategoryBean() {
        return categoryBean;
    }

    public void setCategoryBean(CategoryBean categoryBean) {
        this.categoryBean = categoryBean;
    }

    public String getCategoryName() {return categoryName;}

    public void setCategoryName(String categoryName) {this.categoryName = categoryName;}

    public String getCategoryId() {return categoryId;}

    public void setCategoryId(String categoryId) {this.categoryId = categoryId;}
}
