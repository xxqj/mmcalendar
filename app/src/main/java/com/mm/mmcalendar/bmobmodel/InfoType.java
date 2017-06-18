package com.mm.mmcalendar.bmobmodel;

/**
 * Created by Administrator on 2016/11/5.
 */
public class InfoType extends BaseBmob {
    public static final String restApiUrl="https://api.bmob.cn/1/classes/InfoType";

    public static final String ITEM_KEY="infoType";

    protected Integer postionOrder;

    protected String infoTypeId;

    protected String infoTypeName;

    protected String iconUrl;

    protected String subCategoryId;

    protected String subCategoryName;

    private SubCategoryBean subCategoryBean;

    public Integer getPostionOrder() {
        return postionOrder;
    }

    public void setPostionOrder(Integer postionOrder) {
        this.postionOrder = postionOrder;
    }

    public String getInfoTypeId() {
        return infoTypeId;
    }

    public void setInfoTypeId(String infoTypeId) {
        this.infoTypeId = infoTypeId;
    }

    public String getInfoTypeName() {
        return infoTypeName;
    }

    public void setInfoTypeName(String infoTypeName) {
        this.infoTypeName = infoTypeName;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public SubCategoryBean getSubCategoryBean() {
        return subCategoryBean;
    }

    public void setSubCategoryBean(SubCategoryBean subCategoryBean) {
        this.subCategoryBean = subCategoryBean;
    }
}
