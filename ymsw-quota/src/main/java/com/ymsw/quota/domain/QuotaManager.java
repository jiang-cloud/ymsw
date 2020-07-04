package com.ymsw.quota.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ymsw.common.annotation.Excel;
import com.ymsw.common.core.domain.BaseEntity;

/**
 * 配额管理对象 quota_manager
 * 
 * @author ymsw
 * @date 2020-06-04
 */
public class QuotaManager extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 配额管理id */
    private Long quotaId;

    /** 用户id */
    @Excel(name = "用户id")
    private Integer userId;

    /** 总限额数 */
    @Excel(name = "总限额数")
    private Integer allowTotalCount;

    /** 当前客户数 */
    @Excel(name = "当前客户数")
    private Integer nowTotalCount;

    /** 今日配额数 */
    @Excel(name = "今日配额数")
    private Integer allowTodayCount;

    /** 今日已分配客户数 */
    @Excel(name = "今日已分配客户数")
    private Integer nowTodayCount;

    /** 配额状态 0 关闭 1开启 */
    @Excel(name = "配额状态 0 关闭 1开启")
    private String quotaStatus;

    /** 员工姓名 */
    private String userName;

    /** 员工电话号码 */
    private String phonenumber;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public void setQuotaId(Long quotaId)
    {
        this.quotaId = quotaId;
    }

    public Long getQuotaId() 
    {
        return quotaId;
    }
    public void setUserId(Integer userId) 
    {
        this.userId = userId;
    }

    public Integer getUserId() 
    {
        return userId;
    }
    public void setAllowTotalCount(Integer allowTotalCount) 
    {
        this.allowTotalCount = allowTotalCount;
    }

    public Integer getAllowTotalCount() 
    {
        return allowTotalCount;
    }
    public void setNowTotalCount(Integer nowTotalCount) 
    {
        this.nowTotalCount = nowTotalCount;
    }

    public Integer getNowTotalCount() 
    {
        return nowTotalCount;
    }
    public void setAllowTodayCount(Integer allowTodayCount) 
    {
        this.allowTodayCount = allowTodayCount;
    }

    public Integer getAllowTodayCount() 
    {
        return allowTodayCount;
    }
    public void setNowTodayCount(Integer nowTodayCount) 
    {
        this.nowTodayCount = nowTodayCount;
    }

    public Integer getNowTodayCount() 
    {
        return nowTodayCount;
    }
    public void setQuotaStatus(String quotaStatus) 
    {
        this.quotaStatus = quotaStatus;
    }

    public String getQuotaStatus() 
    {
        return quotaStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("quotaId", getQuotaId())
            .append("userId", getUserId())
            .append("allowTotalCount", getAllowTotalCount())
            .append("nowTotalCount", getNowTotalCount())
            .append("allowTodayCount", getAllowTodayCount())
            .append("nowTodayCount", getNowTodayCount())
            .append("quotaStatus", getQuotaStatus())
            .toString();
    }
}
