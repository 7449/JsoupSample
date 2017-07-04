package com.fiction.mvp.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * by y on 2017/6/21.
 */
@Entity
public class MarkModel {
    @Id
    private String fictionName;

    @Generated(hash = 97283554)
    public MarkModel(String fictionName) {
        this.fictionName = fictionName;
    }

    @Generated(hash = 460913686)
    public MarkModel() {
    }

    public String getFictionName() {
        return this.fictionName;
    }

    public void setFictionName(String fictionName) {
        this.fictionName = fictionName;
    }

}
