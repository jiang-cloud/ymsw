package com.ymsw.customer.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ymsw.common.annotation.Excel;
import com.ymsw.common.core.domain.BaseEntity;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;
import java.util.Date;

/**
 * 客户信息表对象 ymsw_customer
 * 
 * @author ymsw
 * @date 2020-05-18
 */
public class YmswCustomer extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id编号 */
//    @Excel(name = "编号", type = Excel.Type.EXPORT)
    private Long customerId;

    /** 申请时间 */
    @Excel(name = "导入时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date applyTime;

    /** 姓名 */
    @Excel(name = "姓名")
    private String customerName;

    /** 手机号 */
    @Excel(name = "电话")
    private String customerPhone;

    /** 性别 0男 1女 2未知 */
    @Excel(name = "性别", readConverterExp = "0=男,1=女,2=未知")
    private String customerSex;
    /** 通话状态 0未接 1已接通 */
    @Excel(name = "通话状态", readConverterExp = "0=未接,1=已接通",type = Excel.Type.EXPORT)
    private String phoneStatus;

    /** 出生日期 */
    @Excel(name = "出生年份")
    private String customerBirth;

    /** 额度(万) */
    @Excel(name = "贷款额度")
    private Integer customerQuota;

    /** 信用卡0无1有2未知 */
    @Excel(name = "信用卡", readConverterExp = "0=无,1=有,2=未知")
    private String hasCreditCard;

    /** 有无房0无1有2 未知 */
    @Excel(name = "是否有房", readConverterExp = "0=无,1=有,2=未知")
    private String hasHouse;

    /** 有无车0无1有2 未知 */
    @Excel(name = "是否有车", readConverterExp = "0=无,1=有,2=未知")
    private String hasCar;

    /** 职业1上班2 做生意3 未知 */
    @Excel(name = "职业", readConverterExp = "1=上班,2=做生意,3=未知")
    private String customerOccupation;

    /** 薪资方式1代发2转账3现金4未知 */
    @Excel(name = "发薪方式", readConverterExp = "1=代发,2=转账,3=现金,4=未知")
    private String customerSalary;

    /** 公积金0无1有2未知 */
    @Excel(name = "公积金", readConverterExp = "0=无,1=有,2=未知")
    private String hasAccumulation;

    /** 有无社保0无1有2未知 */
    @Excel(name = "社保", readConverterExp = "0=无,1=有,2=未知")
    private String hasSocial;

    /** 星级 */
    @Excel(name = "星级",readConverterExp = "0=0星,1=1星,2=2星,3=3星,4=4星")
    private String customerStar;

    /** 客户状态0新申请1待跟进2已邀约3已签约4已放款5已拒绝6资质不符7捣乱申请8外地申请9黑名单 */
    @Excel(name = "状态", readConverterExp = "0=新申请,1=待跟进,2=已邀约,3=已签约,4=已放款,5=已拒绝,6=资质不符,7=捣乱申请,8=外地申请,9=黑名单")
    private String customerStatus;

    /** 渠道 */
    @Excel(name = "渠道")
    private String channel;

    /** 归属顾问名字 */
    @Excel(name = "归属人")
    private String userName;

    /** 备注（修改客户信息页面的备注） */
    @Excel(name = "备注")
    private String remark;

    /** 用户id */
//    @Excel(name = "归属人", type = Excel.Type.IMPORT)
    private Long userId;

    /** 有无保单0无1有2 未知 */
    @Excel(name = "保单", readConverterExp = "0=无,1=有,2=未知", type = Excel.Type.EXPORT)
    private String hasWarranty;

    /** 微粒贷0无1有2未知 */
    @Excel(name = "微粒贷", readConverterExp = "0=无,1=有,2=未知", type = Excel.Type.EXPORT)
    private String hasWeilidai;

    /** 逾期0无1有2未知 */
    @Excel(name = "逾期", readConverterExp = "0=无,1=有,2=未知", type = Excel.Type.EXPORT)
    private String isOverdue;

    /** 客户类型1 新客户2 再分配客户 */
    @Excel(name = "客户类型", readConverterExp = "1=新客户,2=再分配", type = Excel.Type.EXPORT)
    private String customerType;

    /** 最后备注时间 */
    @Excel(name = "最后备注时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Excel.Type.EXPORT)
    private Date remarkTime;

    /** 分配时间 */
    @Excel(name = "分配时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Excel.Type.EXPORT)
    private Date distributeTime;

    /** 部门id */
//    @Excel(name = "部门id")
    private Long deptId;

    /** 部门名字 */
    private String deptName;

    /** 收藏夹公共池id */
    private Long cpId;

    public Long getCpId() {
        return cpId;
    }

    public void setCpId(Long cpId) {
        this.cpId = cpId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setCustomerId(Long customerId)
    {
        this.customerId = customerId;
    }

    public Long getCustomerId() 
    {
        return customerId;
    }
    public void setCustomerName(String customerName) 
    {
        this.customerName = customerName;
    }

    @NotBlank(message = "客户名字不能为空")
    @Size(min = 0, max = 10, message = "客户名字长度不能超过10个字符")
    public String getCustomerName() 
    {
        return customerName;
    }
    public void setCustomerSex(String customerSex) 
    {
        this.customerSex = customerSex;
    }

    @Pattern(regexp = "[0-2]{1}", message = "性别错误")
    public String getCustomerSex() 
    {
        return customerSex;
    }
    public void setCustomerPhone(String customerPhone) 
    {
        this.customerPhone = customerPhone;
    }
    @Range(min=0, max=4, message = "星级信息错误")
    public String getCustomerStar() {
        return customerStar;
    }

    public void setCustomerStar(String customerStar) {
        this.customerStar = customerStar;
    }

    @Pattern(regexp = "^[1][3,4,5,7,8,9][0-9]{9}$", message = "手机号码不正确")
    public String getCustomerPhone()
    {
        return customerPhone;
    }
    public void setCustomerStatus(String customerStatus) 
    {
        this.customerStatus = customerStatus;
    }
    @Pattern(regexp = "[0-9]{1}", message = "客户状态信息错误")
    public String getCustomerStatus() 
    {
        return customerStatus;
    }
    public void setCustomerBirth(String customerBirth)
    {
        this.customerBirth = customerBirth;
    }

    @Pattern(regexp = "^(19|20)\\d{2}$", message = "出生年份错误")
    public String getCustomerBirth()
    {
        return customerBirth;
    }
    public void setCustomerQuota(Integer customerQuota) 
    {
        this.customerQuota = customerQuota;
    }

    @Range(min=0,max=9999,message = "贷款额度错误")
    public Integer getCustomerQuota() 
    {
        return customerQuota;
    }
    public void setApplyTime(Date applyTime) 
    {
        this.applyTime = applyTime;
    }

    public Date getApplyTime() 
    {
        return applyTime;
    }
    public void setHasHouse(String hasHouse) 
    {
        this.hasHouse = hasHouse;
    }
    @Pattern(regexp = "[0-2]{1}", message = "房信息错误")
    public String getHasHouse() 
    {
        return hasHouse;
    }
    public void setHasCar(String hasCar) 
    {
        this.hasCar = hasCar;
    }
    @Pattern(regexp = "[0-2]{1}", message = "车信息错误")
    public String getHasCar() 
    {
        return hasCar;
    }
    public void setHasWarranty(String hasWarranty) 
    {
        this.hasWarranty = hasWarranty;
    }
    @Pattern(regexp = "[0-2]{1}", message = "保单信息错误")
    public String getHasWarranty() 
    {
        return hasWarranty;
    }
    public void setHasWeilidai(String hasWeilidai) 
    {
        this.hasWeilidai = hasWeilidai;
    }
    @Pattern(regexp = "[0-2]{1}", message = "微粒贷信息错误")
    public String getHasWeilidai() 
    {
        return hasWeilidai;
    }
    public void setHasCreditCard(String hasCreditCard) 
    {
        this.hasCreditCard = hasCreditCard;
    }
    @Pattern(regexp = "[0-2]{1}", message = "信用卡信息错误")
    public String getHasCreditCard() 
    {
        return hasCreditCard;
    }
    public void setHasAccumulation(String hasAccumulation) 
    {
        this.hasAccumulation = hasAccumulation;
    }
    @Pattern(regexp = "[0-2]{1}", message = "公积金信息错误")
    public String getHasAccumulation() 
    {
        return hasAccumulation;
    }
    public void setIsOverdue(String isOverdue) 
    {
        this.isOverdue = isOverdue;
    }
    @Pattern(regexp = "[0-2]{1}", message = "社保信息错误")
    public String getHasSocial() {
        return hasSocial;
    }
    public void setHasSocial(String hasSocial) {
        this.hasSocial = hasSocial;
    }
    @Pattern(regexp = "[0-2]{1}", message = "逾期信息错误")
    public String getIsOverdue()
    {
        return isOverdue;
    }
    public void setCustomerType(String customerType) 
    {
        this.customerType = customerType;
    }
    @Pattern(regexp = "[1-2]{1}", message = "客户类型错误")
    public String getCustomerType() 
    {
        return customerType;
    }
    public void setDeptId(Long deptId) 
    {
        this.deptId = deptId;
    }
    public Long getDeptId() 
    {
        return deptId;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }
    public Long getUserId() 
    {
        return userId;
    }
    public void setCustomerOccupation(String customerOccupation) 
    {
        this.customerOccupation = customerOccupation;
    }
    @Pattern(regexp = "[1-3]{1}", message = "职业错误")
    public String getCustomerOccupation() 
    {
        return customerOccupation;
    }
    public void setCustomerSalary(String customerSalary) 
    {
        this.customerSalary = customerSalary;
    }
    @Pattern(regexp = "[1-4]{1}", message = "薪资方式错误")
    public String getCustomerSalary() 
    {
        return customerSalary;
    }
    public void setChannel(String channel) 
    {
        this.channel = channel;
    }

    public String getChannel() 
    {
        return channel;
    }
    public void setRemarkTime(Date remarkTime) 
    {
        this.remarkTime = remarkTime;
    }

    public Date getRemarkTime() 
    {
        return remarkTime;
    }
    public void setDistributeTime(Date distributeTime) 
    {
        this.distributeTime = distributeTime;
    }

    public Date getDistributeTime() 
    {
        return distributeTime;
    }

    public String getPhoneStatus() {
        return phoneStatus;
    }

    public void setPhoneStatus(String phoneStatus) {
        this.phoneStatus = phoneStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("customerId", getCustomerId())
            .append("customerName", getCustomerName())
            .append("customerStar",getCustomerStar())
            .append("customerSex", getCustomerSex())
            .append("customerPhone", getCustomerPhone())
            .append("customerStatus", getCustomerStatus())
            .append("customerBirth", getCustomerBirth())
            .append("customerQuota", getCustomerQuota())
            .append("applyTime", getApplyTime())
            .append("hasHouse", getHasHouse())
            .append("hasCar", getHasCar())
            .append("hasWarranty", getHasWarranty())
            .append("hasWeilidai", getHasWeilidai())
            .append("hasCreditCard", getHasCreditCard())
            .append("hasAccumulation", getHasAccumulation())
            .append("isOverdue", getIsOverdue())
            .append("customerType", getCustomerType())
            .append("deptId", getDeptId())
            .append("userId", getUserId())
            .append("customerOccupation", getCustomerOccupation())
            .append("customerSalary", getCustomerSalary())
            .append("channel", getChannel())
            .append("remarkTime", getRemarkTime())
            .append("distributeTime", getDistributeTime())
            .append("phoneStatus",getPhoneStatus())
            .toString();
    }
}
