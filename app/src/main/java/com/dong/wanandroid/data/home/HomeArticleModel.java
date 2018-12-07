package com.dong.wanandroid.data.home;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2018/2/28.
 */
@Entity
public class HomeArticleModel {
    /**
     * apkLink :
     * author : xiaoyang
     * chapterId : 292
     * chapterName : pdf电子书
     * collect : false
     * courseId : 13
     * desc :
     * envelopePic :
     * id : 2421
     * link : http://www.wanandroid.com/blog/show/2057
     * niceDate : 22小时前
     * origin :
     * projectLink :
     * publishTime : 1519745961000
     * title : 2018年四大热门技术岗位跳槽指南 pdf
     * visible : 1
     * zan : 0
     */

    private String apkLink;
    private String author;
    private int chapterId;
    private String chapterName;
    private boolean collect;
    private int courseId;
    private String desc;
    private String envelopePic;
    private int id;
    private String link;
    private String niceDate;
    private String origin;
    private String projectLink;
    private long publishTime;
    private String title;
    private int visible;
    private int zan;
    @Generated(hash = 671185686)
    public HomeArticleModel(String apkLink, String author, int chapterId,
            String chapterName, boolean collect, int courseId, String desc,
            String envelopePic, int id, String link, String niceDate, String origin,
            String projectLink, long publishTime, String title, int visible,
            int zan) {
        this.apkLink = apkLink;
        this.author = author;
        this.chapterId = chapterId;
        this.chapterName = chapterName;
        this.collect = collect;
        this.courseId = courseId;
        this.desc = desc;
        this.envelopePic = envelopePic;
        this.id = id;
        this.link = link;
        this.niceDate = niceDate;
        this.origin = origin;
        this.projectLink = projectLink;
        this.publishTime = publishTime;
        this.title = title;
        this.visible = visible;
        this.zan = zan;
    }
    @Generated(hash = 1541200518)
    public HomeArticleModel() {
    }
    public String getApkLink() {
        return this.apkLink;
    }
    public void setApkLink(String apkLink) {
        this.apkLink = apkLink;
    }
    public String getAuthor() {
        return this.author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public int getChapterId() {
        return this.chapterId;
    }
    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }
    public String getChapterName() {
        return this.chapterName;
    }
    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }
    public boolean getCollect() {
        return this.collect;
    }
    public void setCollect(boolean collect) {
        this.collect = collect;
    }
    public int getCourseId() {
        return this.courseId;
    }
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
    public String getDesc() {
        return this.desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getEnvelopePic() {
        return this.envelopePic;
    }
    public void setEnvelopePic(String envelopePic) {
        this.envelopePic = envelopePic;
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
    public String getNiceDate() {
        return this.niceDate;
    }
    public void setNiceDate(String niceDate) {
        this.niceDate = niceDate;
    }
    public String getOrigin() {
        return this.origin;
    }
    public void setOrigin(String origin) {
        this.origin = origin;
    }
    public String getProjectLink() {
        return this.projectLink;
    }
    public void setProjectLink(String projectLink) {
        this.projectLink = projectLink;
    }
    public long getPublishTime() {
        return this.publishTime;
    }
    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getVisible() {
        return this.visible;
    }
    public void setVisible(int visible) {
        this.visible = visible;
    }
    public int getZan() {
        return this.zan;
    }
    public void setZan(int zan) {
        this.zan = zan;
    }

}
