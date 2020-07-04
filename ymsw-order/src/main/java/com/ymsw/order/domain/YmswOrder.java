package com.ymsw.order.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ymsw.common.annotation.Excel;
import com.ymsw.common.core.domain.BaseEntity;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 订单信息表对象 ymsw_order
 * 
 * @author ymsw
 * @date 2020-05-22
 */
public class YmswOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 订单号 */
    private Long orderId;

    /** 客户id */
    @Excel(name = "客户id")
    private Long customerId;

    /** 合同id */
    @Excel(name = "合同id")
    private Long contractId;

    /** 合同图片路径 */
    @Excel(name = "合同图片路径")
    private String contractPath;

    /** 添加签约日期 */
    @Excel(name = "添加签约日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date addTime;

    /** 订单状态1 已签约2 已进件3 已批款4 已收款5 已拒绝 */
    @Excel(name = "订单状态1 已签约2 已进件3 已批款4 已收款5 已拒绝")
    private String orderStatus;

    /** 批款额度(万元) */
    @Excel(name = "批款额度(万元)")
    private Integer allowAmount;

    /** 创收(元) */
    @Excel(name = "创收(元)")
    private Double incomeGeneration;

    /** 费率(%) */
    @Excel(name = "费率(%)")
    private Double orderRate;

    /** 诚意金(元) */
    @Excel(name = "诚意金(元)")
    private Integer sincerityMoney;

    /** 诚意金收款方式1 微信    2 支付宝  3 转账  4 现金 */
    @Excel(name = "诚意金收款方式1 微信    2 支付宝  3 转账  4 现金")
    private String sincerityPayMethod;

    /** 进件渠道 */
    @Excel(name = "进件渠道")
    private String incomingChannel;

    /** 渠道费(元) */
    @Excel(name = "渠道费(元)")
    private Double channelFee;

    /** 进件类型1信用贷2房抵押3车抵押4合作单 */
    @Excel(name = "进件类型1信用贷2房抵押3车抵押4合作单")
    private String incomingType;

    /** 进件助理1 邓娟2 黄文莎3 陈梦颖4 李紫馨5 其他 */
    @Excel(name = "进件助理1 邓娟2 黄文莎3 陈梦颖4 李紫馨5 其他")
    private String incomingAssistant;

    /** 银行经理 */
    @Excel(name = "银行经理")
    private String bankManager;

    /** 订单备注 */
    @Excel(name = "订单备注")
    private String orderRemark;

    /** 客户经理id */
    @Excel(name = "客户经理id")
    private Long userId;

    /** 客户经理名字 */
    @Excel(name = "客户经理名字")
    private String userName;

    /** 部门名字 */
    @Excel(name = "部门名字")
    private String deptName;

    /** 客户名字 */
    @Excel(name = "客户名字")
    private String customerName;

    /** 进件日期 */
    @Excel(name = "进件日期")
    private Date incomingTime;

    /** 收款日期 */
    @Excel(name = "收款日期")
    private Date collectionTime;

    /** 收款日期 */
    @Excel(name = "批款日期")
    private Date allowTime;

    public Date getAllowTime() {
        return allowTime;
    }

    public void setAllowTime(Date allowTime) {
        this.allowTime = allowTime;
    }

    public Date getCollectionTime() {
        return collectionTime;
    }

    public void setCollectionTime(Date collectionTime) {
        this.collectionTime = collectionTime;
    }

    public Date getIncomingTime() {
        return incomingTime;
    }

    public void setIncomingTime(Date incomingTime) {
        this.incomingTime = incomingTime;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setOrderId(Long orderId)
    {
        this.orderId = orderId;
    }

    public Long getOrderId() 
    {
        return orderId;
    }
    public void setCustomerId(Long customerId) 
    {
        this.customerId = customerId;
    }

    public Long getCustomerId() 
    {
        return customerId;
    }
    public void setContractId(Long contractId) 
    {
        this.contractId = contractId;
    }
    @Range(min=0,max=9999999,message = "合同编号错误")
    public Long getContractId() 
    {
        return contractId;
    }
    public void setContractPath(String contractPath) 
    {
        this.contractPath = contractPath;
    }

    public String getContractPath() 
    {
        return contractPath;
    }
    public void setAddTime(Date addTime) 
    {
        this.addTime = addTime;
    }

    public Date getAddTime() 
    {
        return addTime;
    }
    public void setOrderStatus(String orderStatus) 
    {
        this.orderStatus = orderStatus;
    }
    @Pattern(regexp = "[1-5]{1}", message = "订单状态错误")
    public String getOrderStatus() 
    {
        return orderStatus;
    }
    public void setAllowAmount(Integer allowAmount) 
    {
        this.allowAmount = allowAmount;
    }
    @Range(min=0,max=9999,message = "批款额度错误")
    public Integer getAllowAmount() 
    {
        return allowAmount;
    }
    public void setIncomeGeneration(Double incomeGeneration) 
    {
        this.incomeGeneration = incomeGeneration;
    }
    @Range(min=0,max=500000,message = "创收错误")
    public Double getIncomeGeneration() 
    {
        return incomeGeneration;
    }
    public void setOrderRate(Double orderRate) 
    {
        this.orderRate = orderRate;
    }
    @Digits(integer=3,fraction=3,message = "点数错误")
    public Double getOrderRate() 
    {
        return orderRate;
    }
    public void setSincerityMoney(Integer sincerityMoney) 
    {
        this.sincerityMoney = sincerityMoney;
    }
    @Range(min=0,max=500000,message = "诚意金错误")
    public Integer getSincerityMoney() 
    {
        return sincerityMoney;
    }
    public void setSincerityPayMethod(String sincerityPayMethod) 
    {
        this.sincerityPayMethod = sincerityPayMethod;
    }
    @Pattern(regexp = "[1-4]{1}", message = "诚意金付款方式错误")
    public String getSincerityPayMethod() 
    {
        return sincerityPayMethod;
    }
    public void setIncomingChannel(String incomingChannel) 
    {
        this.incomingChannel = incomingChannel;
    }

    public String getIncomingChannel() 
    {
        return incomingChannel;
    }
    public void setChannelFee(Double channelFee) 
    {
        this.channelFee = channelFee;
    }
    @Range(min=0,max=500000,message = "渠道费错误")
    public Double getChannelFee() 
    {
        return channelFee;
    }
    public void setIncomingType(String incomingType) 
    {
        this.incomingType = incomingType;
    }
    @Pattern(regexp = "[1-4]{1}", message = "进件类型错误")
    public String getIncomingType() 
    {
        return incomingType;
    }
    public void setIncomingAssistant(String incomingAssistant) 
    {
        this.incomingAssistant = incomingAssistant;
    }
    @Pattern(regexp = "[1-5]{1}", message = "进件助理错误")
    public String getIncomingAssistant() 
    {
        return incomingAssistant;
    }
    public void setBankManager(String bankManager) 
    {
        this.bankManager = bankManager;
    }
    @Size(min = 0, max = 10, message = "银行经理名字长度不能超过10个字符")
    public String getBankManager() 
    {
        return bankManager;
    }
    public void setOrderRemark(String orderRemark) 
    {
        this.orderRemark = orderRemark;
    }

    public String getOrderRemark() 
    {
        return orderRemark;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("orderId", getOrderId())
            .append("customerId", getCustomerId())
            .append("contractId", getContractId())
            .append("contractPath", getContractPath())
            .append("addTime", getAddTime())
            .append("updateTime", getUpdateTime())
            .append("orderStatus", getOrderStatus())
            .append("allowAmount", getAllowAmount())
            .append("incomeGeneration", getIncomeGeneration())
            .append("orderRate", getOrderRate())
            .append("sincerityMoney", getSincerityMoney())
            .append("sincerityPayMethod", getSincerityPayMethod())
            .append("incomingChannel", getIncomingChannel())
            .append("channelFee", getChannelFee())
            .append("incomingType", getIncomingType())
            .append("incomingAssistant", getIncomingAssistant())
            .append("bankManager", getBankManager())
            .append("orderRemark", getOrderRemark())
            .toString();
    }
}
