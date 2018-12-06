package com.dong.wanandroid.flow;

import java.util.List;

/**
 * Created by macmini002 on 18/5/29.
 */

public class FlowGankModel {


    /**
     * error : false
     * results : [{"_id":"5b07b21e421aa97f0624f436","createdAt":"2018-05-25T14:50:06.666Z","desc":"一个非常方便的fragment页面框架","images":["http://img.gank.io/2f59296d-56b7-4fda-991d-0225983b133e"],"publishedAt":"2018-05-28T18:51:58.793Z","source":"chrome","type":"Android","url":"https://github.com/xuexiangjys/XPage","used":true,"who":"xuexiangjys"},{"_id":"5b0b72ab421aa97efda86531","createdAt":"2018-05-28T11:08:27.699Z","desc":"图片浏览框架,类似微信图片浏览,手势向下滑动关闭,支持长按和单击监听,支持图片加载引擎自定义,支持横竖屏方向设置,简单方便。","images":["http://img.gank.io/5f73be72-b674-4cd2-be35-2cb9435fe5c7","http://img.gank.io/2cf4d640-47dd-4469-bea7-0839be53aa3a","http://img.gank.io/f1ba4b11-8cc9-47a1-b4e6-c0bbd9788160","http://img.gank.io/afb3c3e7-fb2f-436a-8b8d-b0cf271eb2c7"],"publishedAt":"2018-05-28T18:51:58.793Z","source":"web","type":"Android","url":"https://github.com/maning0303/MNImageBrowser","used":true,"who":"maning0303"},{"_id":"5b0b9e62421aa97f0308834e","createdAt":"2018-05-28T14:14:58.964Z","desc":"一个非常方便实用的二维码扫描、解析、生成库","publishedAt":"2018-05-28T18:51:58.793Z","source":"chrome","type":"Android","url":"https://github.com/xuexiangjys/XQRCode","used":true,"who":"xuexiangjys"},{"_id":"5b0babaf421aa97f00f67c60","createdAt":"2018-05-28T15:11:43.661Z","desc":"一种优雅的方式来使用RecyclerView","images":["http://img.gank.io/9fdb6f48-8347-4aff-9fd7-50d0393d8c13","http://img.gank.io/7fcec691-daf9-45c7-b66b-1d42060a7f81"],"publishedAt":"2018-05-28T18:51:58.793Z","source":"web","type":"Android","url":"https://github.com/xuehuayous/DelegationAdapter","used":true,"who":"Kevin.zhou"},{"_id":"5b0bdb26421aa97efda86533","createdAt":"2018-05-28T18:34:14.262Z","desc":"今日头条点赞动画","images":["http://img.gank.io/fc42e1e2-d182-496f-b824-3c3deeb9e3cf","http://img.gank.io/e8dfa555-9bde-451e-93d9-22713290e53a"],"publishedAt":"2018-05-28T18:51:58.793Z","source":"chrome","type":"Android","url":"https://github.com/Qiu800820/SuperLike","used":true,"who":"lijinshanmx"},{"_id":"5b0bdb46421aa97f0624f444","createdAt":"2018-05-28T18:34:46.890Z","desc":"Material icon picker dialog for Android","images":["http://img.gank.io/50bbb41e-08fa-49a6-b011-ba20004125d1"],"publishedAt":"2018-05-28T18:51:58.793Z","source":"chrome","type":"Android","url":"https://github.com/maltaisn/icondialoglib","used":true,"who":"lijinshanmx"},{"_id":"5b0bdb6f421aa97f0624f445","createdAt":"2018-05-28T18:35:27.174Z","desc":"Android自定义View 雷达扫描效果","images":["http://img.gank.io/900b2071-0f3d-433d-b9ff-a5e83777ba84"],"publishedAt":"2018-05-28T18:51:58.793Z","source":"chrome","type":"Android","url":"https://github.com/donkingliang/RadarView","used":true,"who":"lijinshanmx"}]
     */

    private boolean error;
    private List<ResultsEntity> results;

    public void setError(boolean error) {
        this.error = error;
    }

    public void setResults(List<ResultsEntity> results) {
        this.results = results;
    }

    public boolean getError() {
        return error;
    }

    public List<ResultsEntity> getResults() {
        return results;
    }

    public static class ResultsEntity {
        /**
         * _id : 5b07b21e421aa97f0624f436
         * createdAt : 2018-05-25T14:50:06.666Z
         * desc : 一个非常方便的fragment页面框架
         * images : ["http://img.gank.io/2f59296d-56b7-4fda-991d-0225983b133e"]
         * publishedAt : 2018-05-28T18:51:58.793Z
         * source : chrome
         * type : Android
         * url : https://github.com/xuexiangjys/XPage
         * used : true
         * who : xuexiangjys
         */

        private String _id;
        private String createdAt;
        private String desc;
        private String publishedAt;
        private String source;
        private String type;
        private String url;
        private boolean used;
        private String who;
        private List<String> images;

        public void set_id(String _id) {
            this._id = _id;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public void setWho(String who) {
            this.who = who;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public String get_id() {
            return _id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public String getSource() {
            return source;
        }

        public String getType() {
            return type;
        }

        public String getUrl() {
            return url;
        }

        public boolean getUsed() {
            return used;
        }

        public String getWho() {
            return who;
        }

        public List<String> getImages() {
            return images;
        }
    }
}
