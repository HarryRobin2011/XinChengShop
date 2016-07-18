package com.banksoft.XinChengShop.entity;

import com.banksoft.XinChengShop.entity.base.BaseEntity;
import com.banksoft.XinChengShop.model.base.BaseData;
import com.banksoft.XinChengShop.type.PushType;

/**
 * Created by admin on 2016/7/18.
 */
public class PushMessage extends BaseEntity {
    private String content;
    private String title;
    private String targetId;
    private PushType pushType;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public PushType getPushType() {
        return pushType;
    }

    public void setPushType(PushType pushType) {
        this.pushType = pushType;
    }
}
