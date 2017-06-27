package com.magnetic.mvp.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * by y on 27/06/2017.
 */
@Entity
public class SearchModel {
    @Id
    private String searchContent;

    @Generated(hash = 947289422)
    public SearchModel(String searchContent) {
        this.searchContent = searchContent;
    }

    @Generated(hash = 506184495)
    public SearchModel() {
    }

    public String getSearchContent() {
        return this.searchContent;
    }

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }

}
