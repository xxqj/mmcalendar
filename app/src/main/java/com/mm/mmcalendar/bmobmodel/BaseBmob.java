package com.mm.mmcalendar.bmobmodel;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2016/11/5.
 */
public class BaseBmob implements Serializable{

    protected  String objectId;
    protected Date createdAt;
    protected Date updatedAt;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
