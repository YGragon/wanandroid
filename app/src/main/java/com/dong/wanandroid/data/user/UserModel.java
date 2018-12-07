package com.dong.wanandroid.data.user;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

import java.util.List;

/**
 * Created by macmini002 on 18/6/4.
 */
@Entity
public class UserModel {
    /**
     * collectIds : [1,2]
     * email :
     * icon :
     * id : 3339
     * password : 123456
     * type : 0
     * username : dongxi2018
     */
    private String email;
    private String icon;
    private int id;
    private String password;
    private int type;
    private String username;

    @Convert(columnType = String.class, converter = IntegerConverter.class)
    private List<Integer> collectIds;
    @Generated(hash = 673423515)
    public UserModel(String email, String icon, int id, String password, int type,
            String username, List<Integer> collectIds) {
        this.email = email;
        this.icon = icon;
        this.id = id;
        this.password = password;
        this.type = type;
        this.username = username;
        this.collectIds = collectIds;
    }
    @Generated(hash = 782181818)
    public UserModel() {
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getIcon() {
        return this.icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public int getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public List<Integer> getCollectIds() {
        return this.collectIds;
    }
    public void setCollectIds(List<Integer> collectIds) {
        this.collectIds = collectIds;
    }

}
