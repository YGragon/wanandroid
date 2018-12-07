package com.dong.wanandroid.model.banner;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by macmini002 on 18/2/27.
 */
@Entity
public class Banner {
    /**
     * desc : 一起来做个App吧
     * id : 10
     * imagePath : http://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png
     * isVisible : 1
     * order : 0
     * title : 一起来做个App吧
     * type : 0
     * url : http://www.wanandroid.com/blog/show/2
     */

    private String desc;
    private int id;
    private String imagePath;
    private int isVisible;
    private int order;
    private String title;
    private int type;
    private String url;
    @Generated(hash = 1918944577)
    public Banner(String desc, int id, String imagePath, int isVisible, int order,
            String title, int type, String url) {
        this.desc = desc;
        this.id = id;
        this.imagePath = imagePath;
        this.isVisible = isVisible;
        this.order = order;
        this.title = title;
        this.type = type;
        this.url = url;
    }
    @Generated(hash = 2026719322)
    public Banner() {
    }
    public String getDesc() {
        return this.desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getImagePath() {
        return this.imagePath;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    public int getIsVisible() {
        return this.isVisible;
    }
    public void setIsVisible(int isVisible) {
        this.isVisible = isVisible;
    }
    public int getOrder() {
        return this.order;
    }
    public void setOrder(int order) {
        this.order = order;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

}