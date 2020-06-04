package com.blog.core.system.extend;

import com.blog.core.system.dto.BlogArticle;

public class MBlogArticle extends BlogArticle {
    private String userName;
    private String picUrl;
    private String typeName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
