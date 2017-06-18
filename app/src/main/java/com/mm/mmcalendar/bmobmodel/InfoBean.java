package com.mm.mmcalendar.bmobmodel;

import com.mm.mmcalendar.bmobmodel.SubCategoryBean;

/**
 * Created by Administrator on 2016/10/30.
 */
public class InfoBean extends BaseBmob{

    public static final String restApiUrl="https://api.bmob.cn/1/classes/InfoBean";

    public static final String ITEM_KEY="infoBean";

    protected Integer postionOrder;
    private InfoType infoType;
    protected String infoTypeId;
    protected String infoTypeName;
    protected String infoId;
    protected String infoTitle;
    protected String infoDesc;
    protected String infoContent;
    protected String infoPic;
    protected String infoSourceUrl;

    public InfoType getInfoType() {
        return infoType;
    }

    public void setInfoType(InfoType infoType) {
        this.infoType = infoType;
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

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    public String getInfoTitle() {
        return infoTitle;
    }

    public void setInfoTitle(String infoTitle) {
        this.infoTitle = infoTitle;
    }

    public String getInfoDesc() {
        return infoDesc;
    }

    public void setInfoDesc(String infoDesc) {
        this.infoDesc = infoDesc;
    }

    public String getInfoContent() {
        return infoContent;
    }

    public void setInfoContent(String infoContent) {
        this.infoContent = infoContent;
    }

    public String getInfoPic() {
        return infoPic;
    }

    public void setInfoPic(String infoPic) {
        this.infoPic = infoPic;
    }

    public String getInfoSourceUrl() {
        return infoSourceUrl;
    }

    public void setInfoSourceUrl(String infoSourceUrl) {
        this.infoSourceUrl = infoSourceUrl;
    }

    public Integer getPostionOrder() {
        return postionOrder;
    }

    public void setPostionOrder(Integer postionOrder) {
        this.postionOrder = postionOrder;
    }
}
