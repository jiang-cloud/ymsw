package com.ymsw.customer.vo;

import com.ymsw.customer.domain.YmswCustomer;
import com.ymsw.system.domain.SysUser;

import java.util.List;

//抽回重分配页面用于封装提交到后台的参数
public class YmswReallocationVo {

    private List<SysUser> userList;

    private List<YmswCustomer> ymswCustomers;

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<SysUser> getUserList() {
        return userList;
    }

    public void setUserList(List<SysUser> userList) {
        this.userList = userList;
    }

    public List<YmswCustomer> getYmswCustomers() {
        return ymswCustomers;
    }

    public void setIds(List<YmswCustomer> ymswCustomers) {
        this.ymswCustomers = ymswCustomers;
    }
}
