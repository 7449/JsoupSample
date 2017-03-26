package com.image.collection.sql;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;


@Entity
public class CollectionModel {
    @Id
    private String url;

    @Generated(hash = 2125418351)
    public CollectionModel(String url) {
        this.url = url;
    }

    @Generated(hash = 1452055610)
    public CollectionModel() {
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
