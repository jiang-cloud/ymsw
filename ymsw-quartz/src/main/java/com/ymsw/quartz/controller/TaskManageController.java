package com.ymsw.quartz.controller;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ymsw.common.annotation.Log;
import com.ymsw.common.enums.BusinessType;
import com.ymsw.quartz.domain.TaskManage;
import com.ymsw.quartz.service.ITaskManageService;
import com.ymsw.common.core.controller.BaseController;
import com.ymsw.common.core.domain.AjaxResult;
import com.ymsw.common.utils.poi.ExcelUtil;
import com.ymsw.common.core.page.TableDataInfo;

import javax.servlet.http.HttpServletResponse;

/**
 * 定时提醒Controller
 * 
 * @author ymsw
 * @date 2020-06-10
 */
@Controller
@RequestMapping("/task/main")
public class TaskManageController extends BaseController
{
    private String prefix = "task/main";

    @Autowired
    private ITaskManageService taskManageService;

    @RequiresPermissions("task:main:view")
    @GetMapping()
    public String main()
    {
        return prefix + "/main";
    }

    /**
     * 查询定时提醒列表
     */
    @RequiresPermissions("task:main:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TaskManage taskManage)
    {
        startPage();
        List<TaskManage> list = taskManageService.selectTaskManageList(taskManage);
        return getDataTable(list);
    }

    /**
     * 导出定时提醒列表
     */
    @RequiresPermissions("task:main:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TaskManage taskManage)
    {
        List<TaskManage> list = taskManageService.selectTaskManageList(taskManage);
        ExcelUtil<TaskManage> util = new ExcelUtil<TaskManage>(TaskManage.class);
        return util.exportExcel(list, "main");
    }

    /**
     * 新增定时提醒
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存定时提醒
     */
    @RequiresPermissions("task:main:add")
    @Log(title = "定时提醒", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TaskManage taskManage)
    {
        return toAjax(taskManageService.insertTaskManage(taskManage));
    }

    /**
     * 修改定时提醒
     */
    @GetMapping("/edit/{taskId}")
    public String edit(@PathVariable("taskId") Long taskId, ModelMap mmap)
    {
        TaskManage taskManage = taskManageService.selectTaskManageById(taskId);
        mmap.put("taskManage", taskManage);
        return prefix + "/edit";
    }

    /**
     * 修改保存定时提醒
     */
    @RequiresPermissions("task:main:edit")
    @Log(title = "定时提醒", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TaskManage taskManage)
    {
        return toAjax(taskManageService.updateTaskManage(taskManage));
    }

    /**
     * 删除定时提醒
     */
    @RequiresPermissions("task:main:remove")
    @Log(title = "定时提醒", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(taskManageService.deleteTaskManageByIds(ids));
    }

    /**
     * 定时提醒(提前2分钟提醒)
     * 在sql语句查询时当前时间加2分钟，如果大于task_time，且task_status是0（未提醒）的记录查询出来
     * 同时将查询出来的结果集的且task_status改为1（已提醒）
     */
    @PostMapping("/selectTaskManage")
    @ResponseBody
    public AjaxResult selectTaskManage()
    {
        List<TaskManage> taskManageList = taskManageService.selectTaskManage();
        return AjaxResult.success(taskManageList);
    }

}
