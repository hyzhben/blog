package com.blog.core.system.common.enums;

public enum BlogArticleStatusEnums {
    TO_BE_PUBLISH("待发布",1000),
    PUBLISH("发布",2000),
    DISMOUNT("下架",3000);

    public static String dictType = "blog_status";
    /** 描述 */
    private String desc;
    /** 枚举值 */
    private int value;

    private BlogArticleStatusEnums(String desc, int value) {
        this.desc = desc;
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
