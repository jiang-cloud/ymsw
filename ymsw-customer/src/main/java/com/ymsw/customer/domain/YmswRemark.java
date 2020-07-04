package com.ymsw.customer.domain;

import com.sun.jna.platform.win32.Winspool;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ymsw.common.annotation.Excel;
import com.ymsw.common.core.domain.BaseEntity;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 备注对象 ymsw_remark
 * 
 * @author ymsw
 * @date 2020-05-18
 */
public class YmswRemark extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 备注id */
    private Long remarkId;

    /** 备注内容 */
    @Excel(name = "备注内容")
    private String remarkContent;

    /** 操作人id */
    @Excel(name = "操作人id")
    private Long userId;

    /** 备注时间 */
    @Excel(name = "备注时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date remarkTime;

    /** 客户id */
    @Excel(name = "客户id")
    private Long customerId;

    /** 是否主管0否1是 */
    @Excel(name = "是否主管0否1是")
    private String isCharge;

    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setRemarkId(Long remarkId)
    {
        this.remarkId = remarkId;
    }

    public Long getRemarkId() 
    {
        return remarkId;
    }
    public void setRemarkContent(String remarkContent) 
    {
        this.remarkContent = remarkContent;
    }
    @NotBlank(message = "备注不能为空")
    public String getRemarkContent() 
    {
        return remarkContent;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setRemarkTime(Date remarkTime) 
    {
        this.remarkTime = remarkTime;
    }

    public Date getRemarkTime() 
    {
        return remarkTime;
    }
    public void setCustomerId(Long customerId) 
    {
        this.customerId = customerId;
    }

    public Long getCustomerId() 
    {
        return customerId;
    }
    public void setIsCharge(String isCharge) 
    {
        this.isCharge = isCharge;
    }

    public String getIsCharge() 
    {
        return isCharge;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("remarkId", getRemarkId())
            .append("remarkContent", getRemarkContent())
            .append("userId", getUserId())
            .append("remarkTime", getRemarkTime())
            .append("customerId", getCustomerId())
            .append("isCharge", getIsCharge())
            .toString();
    }
}
