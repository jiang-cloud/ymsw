package com.ymsw.quartz.service;

import com.ymsw.quartz.domain.TaskManage;
import java.util.List;

/**
 * 定时提醒Service接口
 * 
 * @author ymsw
 * @date 2020-06-10
 */
public interface ITaskManageService 
{
    /**
     * 查询定时提醒
     * 
     * @param taskId 定时提醒ID
     * @return 定时提醒
     */
    public TaskManage selectTaskManageById(Long taskId);

    /**
     * 查询定时提醒列表
     * 
     * @param taskManage 定时提醒
     * @return 定时提醒集合
     */
    public List<TaskManage> selectTaskManageList(TaskManage taskManage);

    /**
     * 新增定时提醒
     * 
     * @param taskManage 定时提醒
     * @return 结果
     */
    public int insertTaskManage(TaskManage taskManage);

    /**
     * 修改定时提醒
     * 
     * @param taskManage 定时提醒
     * @return 结果
     */
    public int updateTaskManage(TaskManage taskManage);

    /**
     * 批量删除定时提醒
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTaskManageByIds(String ids);

    /**
     * 删除定时提醒信息
     * 
     * @param taskId 定时提醒ID
     * @return 结果
     */
    public int deleteTaskManageById(Long taskId);

    /**
     * 定时提醒(提前2分钟提醒)
     * 在sql语句查询时当前时间加2分钟，如果大于task_time，且task_status是0（未提醒）的记录查询出来
     * 同时将查询出来的结果集的且task_status改为1（已提醒）
     */
    List<TaskManage> selectTaskManage();
}
