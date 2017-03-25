package com.fiction.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * by y on 2017/1/8.
 */

@Entity
public class SqlBean {

    @Id
    private Long id;
    private String title;

    @Generated(hash = 1213259225)
    public SqlBean(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    @Generated(hash = 2066760633)
    public SqlBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
