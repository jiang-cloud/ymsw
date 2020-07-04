package com.ymsw.customer.controller;

import java.util.List;

import com.ymsw.common.annotation.RepeatSubmit;
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
import com.ymsw.customer.domain.YmswRemark;
import com.ymsw.customer.service.IYmswRemarkService;
import com.ymsw.common.core.controller.BaseController;
import com.ymsw.common.core.domain.AjaxResult;
import com.ymsw.common.utils.poi.ExcelUtil;
import com.ymsw.common.core.page.TableDataInfo;

/**
 * 备注Controller
 * 
 * @author ymsw
 * @date 2020-05-18
 */
@Controller
@RequestMapping("/customer/remark")
public class YmswRemarkController extends BaseController
{
    private String prefix = "customer/remark";

    @Autowired
    private IYmswRemarkService ymswRemarkService;

    @RequiresPermissions("customer:remark:view")
    @GetMapping()
    public String remark()
    {
        return prefix + "/remark";
    }

    /**
     * 查询备注列表
     */
    @RequiresPermissions("customer:remark:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(YmswRemark ymswRemark)
    {
        startPage();
        List<YmswRemark> list = ymswRemarkService.selectYmswRemarkList(ymswRemark);
        return getDataTable(list);
    }

    /**
     * 导出备注列表
     */
    @RequiresPermissions("customer:remark:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(YmswRemark ymswRemark)
    {
        List<YmswRemark> list = ymswRemarkService.selectYmswRemarkList(ymswRemark);
        ExcelUtil<YmswRemark> util = new ExcelUtil<YmswRemark>(YmswRemark.class);
        return util.exportExcel(list, "remark");
    }

    /**
     * 新增备注
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存备注
     */
    @RequiresPermissions("customer:main:chargeRemark")
    @Log(title = "备注", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    @RepeatSubmit
    public AjaxResult addSave(YmswRemark ymswRemark)
    {
        return toAjax(ymswRemarkService.insertYmswRemark(ymswRemark));
    }

    /**
     * 修改备注
     */
    @GetMapping("/edit/{remarkId}")
    public String edit(@PathVariable("remarkId") Long remarkId, ModelMap mmap)
    {
        YmswRemark ymswRemark = ymswRemarkService.selectYmswRemarkById(remarkId);
        mmap.put("ymswRemark", ymswRemark);
        return prefix + "/edit";
    }

    /**
     * 修改保存备注
     */
    @RequiresPermissions("customer:remark:edit")
    @Log(title = "备注", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(YmswRemark ymswRemark)
    {
        return toAjax(ymswRemarkService.updateYmswRemark(ymswRemark));
    }

    /**
     * 删除备注
     */
    @RequiresPermissions("customer:remark:remove")
    @Log(title = "备注", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(ymswRemarkService.deleteYmswRemarkByIds(ids));
    }
}
