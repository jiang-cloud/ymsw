package com.ymsw.customer.controller;

import java.util.List;

import com.ymsw.customer.domain.YmswCustomer;
import com.ymsw.customer.vo.YmswReallocationVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ymsw.common.annotation.Log;
import com.ymsw.common.enums.BusinessType;
import com.ymsw.customer.domain.YmswCollectionPool;
import com.ymsw.customer.service.IYmswCollectionPoolService;
import com.ymsw.common.core.controller.BaseController;
import com.ymsw.common.core.domain.AjaxResult;
import com.ymsw.common.utils.poi.ExcelUtil;
import com.ymsw.common.core.page.TableDataInfo;

/**
 * 收藏夹-公共池Controller
 * 
 * @author ymsw
 * @date 2020-05-18
 */
@Controller
@RequestMapping("/customer/pool")
public class YmswCollectionPoolController extends BaseController
{
    private String prefix = "customer/pool";

    @Autowired
    private IYmswCollectionPoolService ymswCollectionPoolService;

    /**
     * 跳转到收藏夹列表
     */
    @RequiresPermissions("customer:pool:view")
    @GetMapping("/collection")
    public String collection()
    {
        return prefix + "/collection";
    }

    /**
     * 跳转到公共池列表
     */
    @RequiresPermissions("customer:pool:view")
    @GetMapping("/pool")
    public String pool()
    {
        return prefix + "/pool";
    }

    /**
     * 查询收藏夹列表(根据数据范围查询)
     */
    @RequiresPermissions("customer:pool:collectList")
    @PostMapping("/collectList")
    @ResponseBody
    public TableDataInfo collectList(YmswCustomer ymswCustomer)
    {
        startPage();
        List<YmswCustomer> list = ymswCollectionPoolService.selectYmswCollectionPoolList(ymswCustomer);
        return getDataTable(list);
    }

    /**
     * 查询公共池列表(做数据范围限制)
     */
    @RequiresPermissions("customer:pool:poolList")
    @PostMapping("/poolList")
    @ResponseBody
    public TableDataInfo poolList(YmswCustomer ymswCustomer)
    {
        startPage();
        List<YmswCustomer> list = ymswCollectionPoolService.selectYmswCollectionPoolList(ymswCustomer);
        return getDataTable(list);
    }

    /**
     * 导出收藏夹-公共池列表
     */
    /*@RequiresPermissions("customer:pool:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(YmswCollectionPool ymswCollectionPool)
    {
        List<YmswCollectionPool> list = ymswCollectionPoolService.selectYmswCollectionPoolList(ymswCollectionPool);
        ExcelUtil<YmswCollectionPool> util = new ExcelUtil<YmswCollectionPool>(YmswCollectionPool.class);
        return util.exportExcel(list, "pool");
    }*/

    /**
     * 新增收藏夹-公共池
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存收藏夹-公共池
     */
    @RequiresPermissions("customer:pool:add")
    @Log(title = "收藏夹-公共池", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(YmswCollectionPool ymswCollectionPool)
    {
        return toAjax(ymswCollectionPoolService.insertYmswCollectionPool(ymswCollectionPool));
    }

    /**
     * 修改收藏夹-公共池
     */
    @GetMapping("/edit/{cpId}")
    public String edit(@PathVariable("cpId") Long cpId, ModelMap mmap)
    {
        YmswCollectionPool ymswCollectionPool = ymswCollectionPoolService.selectYmswCollectionPoolById(cpId);
        mmap.put("ymswCollectionPool", ymswCollectionPool);
        return prefix + "/edit";
    }

    /**
     * 修改保存收藏夹-公共池
     */
    @RequiresPermissions("customer:pool:edit")
    @Log(title = "收藏夹-公共池", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(YmswCollectionPool ymswCollectionPool)
    {
        return toAjax(ymswCollectionPoolService.updateYmswCollectionPool(ymswCollectionPool));
    }

    /**
     * 删除收藏夹-公共池
     */
    @RequiresPermissions("customer:pool:remove")
    @Log(title = "收藏夹-公共池", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(ymswCollectionPoolService.deleteYmswCollectionPoolByIds(ids));
    }

    /**
     * 批量加入收藏夹-公共池
     */
    @RequiresPermissions("customer:pool:addToCollectionPool")
    @Log(title = "收藏夹-公共池", businessType = BusinessType.INSERT)
    @PostMapping( "/addToCollectionPoll")
    @ResponseBody
    public AjaxResult addToCollectionPool(@RequestBody YmswReallocationVo reallocationVo)
    {
        return ymswCollectionPoolService.addToCollectionPool(reallocationVo);
    }
}
