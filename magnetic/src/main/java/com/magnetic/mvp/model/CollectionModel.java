package com.magnetic.mvp.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;


@Entity
public class CollectionModel {
    private String magneticName;
    @Id
    private String url;

    @Generated(hash = 1882965261)
    public CollectionModel(String magneticName, String url) {
        this.magneticName = magneticName;
        this.url = url;
    }

    @Generated(hash = 1452055610)
    public CollectionModel() {
    }

    public String getMagneticName() {
        return this.magneticName;
    }

    public void setMagneticName(String magneticName) {
        this.magneticName = magneticName;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
