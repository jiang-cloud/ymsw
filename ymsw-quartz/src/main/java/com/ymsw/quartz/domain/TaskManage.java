package com.ymsw.quartz.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ymsw.common.annotation.Excel;
import com.ymsw.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 定时提醒对象 task_manage
 * 
 * @author ymsw
 * @date 2020-06-10
 */
public class TaskManage extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long taskId;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 提醒时间 */
    @Excel(name = "提醒时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date taskTime;

    /** 提醒内容 */
    @Excel(name = "提醒内容")
    private String taskContent;

    /** 是否已提醒 0否 1是 */
    @Excel(name = "是否已提醒 0否 1是")
    private String taskStatus;

    public void setTaskId(Long taskId) 
    {
        this.taskId = taskId;
    }

    public Long getTaskId() 
    {
        return taskId;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setTaskTime(Date taskTime) 
    {
        this.taskTime = taskTime;
    }

    public Date getTaskTime() 
    {
        return taskTime;
    }
    public void setTaskContent(String taskContent) 
    {
        this.taskContent = taskContent;
    }

    public String getTaskContent() 
    {
        return taskContent;
    }
    public void setTaskStatus(String taskStatus) 
    {
        this.taskStatus = taskStatus;
    }

    public String getTaskStatus() 
    {
        return taskStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("taskId", getTaskId())
            .append("userId", getUserId())
            .append("taskTime", getTaskTime())
            .append("taskContent", getTaskContent())
            .append("taskStatus", getTaskStatus())
            .toString();
    }
}
