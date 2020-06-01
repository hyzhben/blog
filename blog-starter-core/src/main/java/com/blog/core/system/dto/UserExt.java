package com.blog.core.system.dto;

import java.util.List;

public class UserExt extends User {

    //权限信息
    private List<Menu> permissions;

    //企业信息
    private String companyId;

    public List<Menu> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Menu> permissions) {
        this.permissions = permissions;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
