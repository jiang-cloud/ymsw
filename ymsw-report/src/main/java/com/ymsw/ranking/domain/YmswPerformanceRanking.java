package com.ymsw.ranking.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ymsw.common.annotation.Excel;
import com.ymsw.common.core.domain.BaseEntity;

/**
 * 业绩排行对象 ymsw_performance_ranking
 * 
 * @author ymsw
 * @date 2020-06-22
 */
public class YmswPerformanceRanking extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long rankingId;

    /** 业务员id */
    @Excel(name = "业务员id")
    private Long userId;

    /** 当月累计创收 */
    @Excel(name = "当月累计创收")
    private Double totalGeneration;

    /** 当日创收 */
    @Excel(name = "当日创收")
    private Double todayGeneration;

    /** 当月累计进件数 */
    @Excel(name = "当月累计进件数")
    private Long totalIncomingCount;

    /** 当日进件数 */
    @Excel(name = "当日进件数")
    private Long todayIncomingCount;

    /** 当月累计收款笔数 */
    @Excel(name = "当月累计收款笔数")
    private Long totalCollectionCount;

    /** 当日收款笔数 */
    @Excel(name = "当日收款笔数")
    private Long todayCollectionCount;

    /** 当月累计批款总金额 */
    @Excel(name = "当月累计批款总金额")
    private Integer totalAllowAmount;

    /** 平均费率 */
    @Excel(name = "平均费率")
    private Double avgOrderRate;

    /** 年月 */
    @Excel(name = "年月")
    private String dataYearMonth;

    /** 业务经理名字 */
    private String userName;

    /** 部门名字 */
    private String deptName;

    /** 部门id */
    private String deptId;

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public void setRankingId(Long rankingId)
    {
        this.rankingId = rankingId;
    }

    public Long getRankingId() 
    {
        return rankingId;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setTotalGeneration(Double totalGeneration) 
    {
        this.totalGeneration = totalGeneration;
    }

    public Double getTotalGeneration() 
    {
        return totalGeneration;
    }
    public void setTodayGeneration(Double todayGeneration) 
    {
        this.todayGeneration = todayGeneration;
    }

    public Double getTodayGeneration() 
    {
        return todayGeneration;
    }
    public void setTotalIncomingCount(Long totalIncomingCount) 
    {
        this.totalIncomingCount = totalIncomingCount;
    }

    public Long getTotalIncomingCount() 
    {
        return totalIncomingCount;
    }
    public void setTodayIncomingCount(Long todayIncomingCount) 
    {
        this.todayIncomingCount = todayIncomingCount;
    }

    public Long getTodayIncomingCount() 
    {
        return todayIncomingCount;
    }
    public void setTotalCollectionCount(Long totalCollectionCount) 
    {
        this.totalCollectionCount = totalCollectionCount;
    }

    public Long getTotalCollectionCount() 
    {
        return totalCollectionCount;
    }
    public void setTodayCollectionCount(Long todayCollectionCount) 
    {
        this.todayCollectionCount = todayCollectionCount;
    }

    public Long getTodayCollectionCount() 
    {
        return todayCollectionCount;
    }
    public void setTotalAllowAmount(Integer totalAllowAmount) 
    {
        this.totalAllowAmount = totalAllowAmount;
    }

    public Integer getTotalAllowAmount() 
    {
        return totalAllowAmount;
    }
    public void setAvgOrderRate(Double avgOrderRate) 
    {
        this.avgOrderRate = avgOrderRate;
    }

    public Double getAvgOrderRate() 
    {
        return avgOrderRate;
    }
    public void setDataYearMonth(String dataYearMonth)
    {
        this.dataYearMonth = dataYearMonth;
    }

    public String getDataYearMonth()
    {
        return dataYearMonth;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("rankingId", getRankingId())
            .append("userId", getUserId())
            .append("totalGeneration", getTotalGeneration())
            .append("todayGeneration", getTodayGeneration())
            .append("totalIncomingCount", getTotalIncomingCount())
            .append("todayIncomingCount", getTodayIncomingCount())
            .append("totalCollectionCount", getTotalCollectionCount())
            .append("todayCollectionCount", getTodayCollectionCount())
            .append("totalAllowAmount", getTotalAllowAmount())
            .append("avgOrderRate", getAvgOrderRate())
            .append("yearMonth", getDataYearMonth())
            .toString();
    }
}
