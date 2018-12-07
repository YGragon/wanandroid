package com.dong.wanandroid.model.search;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by macmini002 on 18/3/1.
 */
@Entity
public class SearchModel {
    /**
     * id : 6
     * link :
     * name : 面试
     * order : 1
     * visible : 1
     */

    private int id;
    private String link;
    private String name;
    private int order;
    private int visible;
    @Generated(hash = 1832365793)
    public SearchModel(int id, String link, String name, int order, int visible) {
        this.id = id;
        this.link = link;
        this.name = name;
        this.order = order;
        this.visible = visible;
    }
    @Generated(hash = 506184495)
    public SearchModel() {
    }
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getLink() {
        return this.link;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getOrder() {
        return this.order;
    }
    public void setOrder(int order) {
        this.order = order;
    }
    public int getVisible() {
        return this.visible;
    }
    public void setVisible(int visible) {
        this.visible = visible;
    }

}
