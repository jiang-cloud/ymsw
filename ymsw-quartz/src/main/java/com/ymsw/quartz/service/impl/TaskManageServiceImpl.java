package com.ymsw.quartz.service.impl;

import java.util.List;

import com.ymsw.common.utils.security.PermissionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ymsw.quartz.mapper.TaskManageMapper;
import com.ymsw.quartz.domain.TaskManage;
import com.ymsw.quartz.service.ITaskManageService;
import com.ymsw.common.core.text.Convert;

/**
 * 定时提醒Service业务层处理
 * 
 * @author ymsw
 * @date 2020-06-10
 */
@Service
public class TaskManageServiceImpl implements ITaskManageService 
{
    @Autowired
    private TaskManageMapper taskManageMapper;

    /**
     * 查询定时提醒
     * 
     * @param taskId 定时提醒ID
     * @return 定时提醒
     */
    @Override
    public TaskManage selectTaskManageById(Long taskId)
    {
        return taskManageMapper.selectTaskManageById(taskId);
    }

    /**
     * 查询定时提醒列表
     * 
     * @param taskManage 定时提醒
     * @return 定时提醒
     */
    @Override
    public List<TaskManage> selectTaskManageList(TaskManage taskManage)
    {
        return taskManageMapper.selectTaskManageList(taskManage);
    }

    /**
     * 新增定时提醒
     * 
     * @param taskManage 定时提醒
     * @return 结果
     */
    @Override
    public int insertTaskManage(TaskManage taskManage)
    {
        Long userId = (Long) PermissionUtils.getPrincipalProperty("userId");//子模块里获取登陆人的信息
        taskManage.setUserId(userId);//设置用户
        taskManage.setTaskStatus("0");  //设置是否已提醒 0否 1是
        return taskManageMapper.insertTaskManage(taskManage);
    }

    /**
     * 修改定时提醒
     * 
     * @param taskManage 定时提醒
     * @return 结果
     */
    @Override
    public int updateTaskManage(TaskManage taskManage)
    {
        return taskManageMapper.updateTaskManage(taskManage);
    }

    /**
     * 删除定时提醒对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTaskManageByIds(String ids)
    {
        return taskManageMapper.deleteTaskManageByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除定时提醒信息
     * 
     * @param taskId 定时提醒ID
     * @return 结果
     */
    @Override
    public int deleteTaskManageById(Long taskId)
    {
        return taskManageMapper.deleteTaskManageById(taskId);
    }

    /**
     * 定时提醒(提前2分钟提醒)
     * 在sql语句查询时当前时间加2分钟，如果大于task_time，且task_status是0（未提醒）的记录查询出来
     * 同时将查询出来的结果集的task_status改为1（已提醒）
     */
    @Override
    public List<TaskManage> selectTaskManage() {
        // 获取当前的用户名称
        Long userId = (Long) PermissionUtils.getPrincipalProperty("userId");
        List<TaskManage> taskManageList = taskManageMapper.selectTaskManages(userId);
        for (TaskManage taskManage : taskManageList) {
            taskManage.setTaskStatus("1");
            taskManageMapper.updateTaskManage(taskManage);//设置是否已提醒 0否 1是
        }
        return taskManageList;
    }
}
