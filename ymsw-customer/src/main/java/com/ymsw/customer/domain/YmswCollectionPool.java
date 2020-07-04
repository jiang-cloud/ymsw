package com.ymsw.customer.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ymsw.common.annotation.Excel;
import com.ymsw.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 收藏夹-公共池对象 ymsw_collection_pool
 * 
 * @author ymsw
 * @date 2020-05-18
 */
public class YmswCollectionPool extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 收藏-公共池id */
    private Long cpId;

    /** 客户id */
    @Excel(name = "客户id")
    private Long customerId;

    /** 业务员id */
    @Excel(name = "业务员id")
    private Long userId;

    /** 加入时间 */
    @Excel(name = "加入时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date addTime;

    /** 类型 1收藏夹 2 公共池 */
    @Excel(name = "类型 1收藏夹 2 公共池")
    private String cpType;

    /** 操作员id（加入公共池的user） */
    @Excel(name = "操作员id（加入公共池的user）")
    private Long operUserId;

    public Long getOperUserId() {
        return operUserId;
    }

    public void setOperUserId(Long operUserId) {
        this.operUserId = operUserId;
    }

    public void setCpId(Long cpId)
    {
        this.cpId = cpId;
    }

    public Long getCpId() 
    {
        return cpId;
    }
    public void setCustomerId(Long customerId) 
    {
        this.customerId = customerId;
    }

    public Long getCustomerId() 
    {
        return customerId;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setAddTime(Date addTime) 
    {
        this.addTime = addTime;
    }

    public Date getAddTime() 
    {
        return addTime;
    }
    public void setCpType(String cpType) 
    {
        this.cpType = cpType;
    }

    public String getCpType() 
    {
        return cpType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("cpId", getCpId())
            .append("customerId", getCustomerId())
            .append("userId", getUserId())
            .append("addTime", getAddTime())
            .append("cpType", getCpType())
            .toString();
    }
}
