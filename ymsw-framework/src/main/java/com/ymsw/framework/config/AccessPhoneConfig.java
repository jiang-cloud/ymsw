package com.ymsw.framework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "myconfig")
/*
配置类，
1 accessphones 读取application.yml里配置的无需验证码，可直接登录的手机号
2  userIds 读取无需分配客户的员工
 */
public class AccessPhoneConfig {

    private List<String> accessphones = new ArrayList<>();

    public List<String> getAccessphones() {
        return accessphones;
    }

    public void setAccessphones(List<String> accessphones) {
        this.accessphones = accessphones;
    }
}
