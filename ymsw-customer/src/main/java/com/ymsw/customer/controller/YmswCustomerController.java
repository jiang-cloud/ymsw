package com.ymsw.customer.controller;

import java.util.*;

import com.ymsw.common.annotation.RepeatSubmit;
import com.ymsw.common.core.domain.BaseEntity;
import com.ymsw.common.utils.StringUtils;
import com.ymsw.customer.domain.YmswRemark;
import com.ymsw.customer.service.IYmswCollectionPoolService;
import com.ymsw.customer.service.IYmswRemarkService;
import com.ymsw.customer.vo.YmswReallocationVo;
import com.ymsw.quota.domain.QuotaManager;
import com.ymsw.quota.service.IQuotaManagerService;
import com.ymsw.system.domain.SysConfig;
import com.ymsw.system.domain.SysDept;
import com.ymsw.system.domain.SysUser;
import com.ymsw.system.service.ISysConfigService;
import com.ymsw.system.service.ISysDeptService;
import com.ymsw.system.service.ISysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.ymsw.common.annotation.Log;
import com.ymsw.common.enums.BusinessType;
import com.ymsw.customer.domain.YmswCustomer;
import com.ymsw.customer.service.IYmswCustomerService;
import com.ymsw.common.core.controller.BaseController;
import com.ymsw.common.core.domain.AjaxResult;
import com.ymsw.common.utils.poi.ExcelUtil;
import com.ymsw.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 客户信息表Controller
 * 
 * @author ymsw
 * @date 2020-05-18
 */
@Controller
@RequestMapping("/customer/main")
public class YmswCustomerController extends BaseController
{
    private String prefix = "customer/main";

    @Autowired
    private IYmswCustomerService ymswCustomerService;
    @Autowired
    private IYmswRemarkService ymswRemarkService;
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ISysDeptService sysDeptService;
    @Autowired
    private IYmswCollectionPoolService ymswCollectionPoolService;
    @Autowired
    private ISysConfigService configService;
    @Autowired
    private IQuotaManagerService quotaManagerService;

    /**
     * 跳转到 所有客户 -→ 我的客户页面
     */
    @RequiresPermissions("customer:main:view")
    @GetMapping()
    public String main()
    {
        return prefix + "/main";
    }

    /**
     * 我的客户查询客户列表（通过userId查询）
     * 数据范围：仅查询个人的客户
     */
    @RequiresPermissions("customer:main:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(YmswCustomer ymswCustomer)
    {
        startPage();
        List<YmswCustomer> list = ymswCustomerService.selectYmswCustomerList(ymswCustomer);
        return getDataTable(list);
    }

    /**
     * 跳转到 客户管理 -→ 客户列表页面
     */
    @RequiresPermissions("customer:manage:view")
    @GetMapping("/manage")
    public String manage(ModelMap mmap)
    {
        BaseEntity baseEntity = new BaseEntity();
        List<SysUser> sysUsers = sysUserService.selectUsers(baseEntity);//根据数据范围查询所有在职员工列表，除了超级管理员
        List<SysDept> sysDepts = sysDeptService.selectDepts(baseEntity);//根据数据范围查询部门列表
        mmap.put("sysUsers", sysUsers);
        mmap.put("sysDepts", sysDepts);
        return prefix + "/manage";
    }

    /**
     * 客户管理 -→ 客户列表页面 查询客户列表（使用数据范围）
     * 数据范围：查询客户表里的所有客户（但不包括收藏夹里的客户，而包括公共池里的客户）
     */
    @RequiresPermissions("customer:manage:list")
    @PostMapping("/managelist")
    @ResponseBody
    public TableDataInfo managelist(YmswCustomer ymswCustomer)
    {
        startPage();
        //查询客户表里的所有客户（但不包括收藏夹里的客户，而包括公共池里的客户）
        List<YmswCustomer> list = ymswCustomerService.selectManageList(ymswCustomer);
        return getDataTable(list);
    }

    /**
     * 跳转到 客户管理 -→ 自动抽回设置页面
     */
    @RequiresPermissions("customer:autorealloc:view")
    @GetMapping("/autorealloc")
    public String autorealloc(ModelMap mmap)
    {
        SysConfig config = new SysConfig();
        config.setConfigId(4L);// 4 为自动抽回设置的configId，固定不变，注意：不能修改。
        SysConfig sysConfig = configService.autoReallocConfig(config);//查询自动抽回设置
        mmap.put("sysConfig",sysConfig);
        return prefix + "/autorealloc";
    }

    /**
     * 导出客户信息表列表
     */
    @RequiresPermissions("customer:main:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(YmswCustomer ymswCustomer)
    {
        //查询公共池里的客户列表，否则就查询客户表里的所有客户（但不包括收藏夹里的客户，而包括公共池里的客户）
        List<YmswCustomer> list = ymswCustomerService.selectManageList(ymswCustomer);
        ExcelUtil<YmswCustomer> util = new ExcelUtil<YmswCustomer>(YmswCustomer.class);
        return util.exportExcel(list, "客户数据");
    }

    /**
     * 新增客户信息表
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存客户信息表
     */
    @RequiresPermissions("customer:main:add")
    @Log(title = "客户信息表", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated YmswCustomer ymswCustomer)
    {
        return ymswCustomerService.insertYmswCustomer(ymswCustomer);
    }

    /**
     * 修改客户信息表
     */
    @GetMapping("/edit/{customerId}")
    public String edit(@PathVariable("customerId") Long customerId, ModelMap mmap)
    {
        //通过customerId查询客户信息
        YmswCustomer ymswCustomer = ymswCustomerService.selectYmswCustomerById(customerId);
        //计算年龄
        String customerBirth = ymswCustomer.getCustomerBirth();
        Calendar date = Calendar.getInstance();
        //当出生年份为空时，将今年的年份作为客户的出生年份，避免数据库里出生年份为空时报空指针错误
        if (StringUtils.isEmpty(customerBirth)){
            customerBirth = String.valueOf(date.get(Calendar.YEAR));
        }
        int age = Integer.valueOf(date.get(Calendar.YEAR))-Integer.valueOf(customerBirth);
        ymswCustomer.setCustomerBirth(String.valueOf(age));//赋值age为年龄
        //通过customerId查询该客户的业务经理添加的备注
        YmswRemark ymswRemark = new YmswRemark();
        ymswRemark.setCustomerId(customerId);
        ymswRemark.setIsCharge("0");//是否主管 0否 1是
        List<YmswRemark> ymswRemarks = ymswRemarkService.selectYmswRemarkList(ymswRemark);
        //查询该客户的主管添加的点评
        ymswRemark.setIsCharge("1");//是否主管 0否 1是
        List<YmswRemark> remarkList = ymswRemarkService.selectYmswRemarkList(ymswRemark);
        mmap.put("ymswRemarks",ymswRemarks);
        mmap.put("remarkList",remarkList);
        mmap.put("ymswCustomer", ymswCustomer);
        return prefix + "/edit";
    }

    /**
     * 修改保存客户信息表
     */
    @RequiresPermissions("customer:main:edit")
    @Log(title = "客户信息表", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    @RepeatSubmit
    public AjaxResult editSave(@Validated YmswCustomer ymswCustomer)
    {
        return toAjax(ymswCustomerService.updateYmswCustomer(ymswCustomer));
    }

    /**
     * 删除客户信息表
     */
    @RequiresPermissions("customer:main:remove")
    @Log(title = "客户信息表", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(ymswCustomerService.deleteYmswCustomerByIds(ids));
    }

    /**
     * 下载模板
     */
    @RequiresPermissions("customer:main:view")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate()
    {
        ExcelUtil<YmswCustomer> util = new ExcelUtil<YmswCustomer>(YmswCustomer.class);
        return util.importTemplateExcel("客户数据");
    }

    /**
     * 导入客户数据
     */
    @Log(title = "客户信息表", businessType = BusinessType.IMPORT)
    @RequiresPermissions("customer:main:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file) throws Exception
    {
        ExcelUtil<YmswCustomer> util = new ExcelUtil<>(YmswCustomer.class);
        List<YmswCustomer> ymswCustomerList = util.importExcel(file.getInputStream());
        String message = ymswCustomerService.importYmswCustomer(ymswCustomerList);
        return AjaxResult.success(message);
    }

    /**
     * 根据客户电话号码查询客户信息（客户表里可能存在多条相同的电话号码，取最后一次添加的客户信息【即申请时间是最大的】）
     */
//    @RequiresPermissions("customer:main:getCustomerInfo")
    @GetMapping("/getCustomerInfo/{customerPhone}")
    @ResponseBody
    public AjaxResult getCustomerInfo(@PathVariable("customerPhone") String customerPhone)
    {
        YmswCustomer ymswCustomer = ymswCustomerService.getCustomerInfo(customerPhone);
        return AjaxResult.success(ymswCustomer);
    }

    /**
     * 跳转到抽回重分配页面
     */
    @RequiresPermissions("customer:manage:reallocation")
    @GetMapping("/reallocation")
    public String reallocation()
    {
        //mmap.put("ids",ids);//需要重分配的customerIds，再传到reallocation.html页面
        return prefix + "/reallocation";
    }

    /**
     *抽回重分配业务
     */
    @Log(title = "客户信息表", businessType = BusinessType.UPDATE)
    @RequiresPermissions("customer:manage:reallocation")
    @PostMapping("/saveReallocation")
    @ResponseBody
    public AjaxResult saveReallocation(@RequestBody YmswReallocationVo ymswReallocationVo)
    {
        //存储了将要分配给的userId和对应的count
        List<SysUser> userList = ymswReallocationVo.getUserList();
        //存储了被分配的customerId和对应的业务经理userId，业务经理userId用来计算当前客户数。
        List<YmswCustomer> ymswCustomers = ymswReallocationVo.getYmswCustomers();
        int oldTotalCount = ymswCustomers.size();   //要被分配的总条数
        int totalcount = 0; //分配成功的条数
        List<Long> customerIds = new ArrayList<>(); //存放分配给新业务经理的customerId
        Map<Long,Integer> maps = new HashMap<>();   //存放原业务经理和其被分配的客户条数  key是userId，value是被分配的客户条数
        StringBuffer msg = new StringBuffer();
        for (SysUser user : userList) {
            Integer count = Integer.valueOf((String) user.getParams().get("count"));//分配数
            Long userId = user.getUserId(); //业务经理userId
            String userName = user.getUserName();
            QuotaManager quotaManager = quotaManagerService.selectQuotaManagerByUserId(userId); //通过userId查询改业务经理的配额信息
            if (StringUtils.isNotNull(quotaManager)){
                String quotaStatus = quotaManager.getQuotaStatus(); //配额状态 0 关闭 1开启
                Integer allowTotalCount = quotaManager.getAllowTotalCount();    //总限额数
                Integer nowTotalCount = quotaManager.getNowTotalCount();    //当前客户数
                //当配置状态是开启，且当前客户数+分配数<=总限额数，且今日已分配客户数+分配数<=今日配额数时才进行分配。
                if ("0".equals(quotaStatus)){
                    msg.append(userName +"分配失败，请检查配额状态；<br>");
                    continue;
                }
                if ((nowTotalCount + count) > allowTotalCount){
                    msg.append(userName +"分配失败，请检查总限额数；<br>");
                    continue;
                }
                for (int i = 0; i < count; i++) {
                    YmswCustomer ymswCustomer = ymswCustomers.get(0);//每次循环从ymswCustomers里获取第一条数据进行分配
                    Long oldUserId = ymswCustomer.getUserId();//获取原业务经理id
                    if (!maps.containsKey(oldUserId)){
                        maps.put(oldUserId,0);  //如果maps里未存放该业务经理id，就put进去，并设置被分配条数为0
                    }
                    Integer bfpCount = maps.get(oldUserId); //获取该业务经理被分配的条数
                    bfpCount++;
                    maps.put(oldUserId,bfpCount);   // 条数加1后再放入maps
                    customerIds.add(ymswCustomer.getCustomerId());  //被分配的customerId放入customerIds
                    ymswCustomers.remove(0);//清除ymswCustomers里的第一条数据
                    totalcount++;//分配后，将成功分配的条数 +1
                }
                //以上循环分配完成后
                ymswCustomerService.batchUpdateUserId(userId, customerIds);  // 1、批量修改客户的对应的user_id为userId,同时修改客户状态为再分配
                ymswCollectionPoolService.batchDeleteByCustomerIds(customerIds);    // 2、从公共池删除。不管customerId是否在公共池收藏夹表，都进行删除操作。
                quotaManager.setNowTotalCount(nowTotalCount+count);//设置当前客户数
                quotaManagerService.updateQuotaManager(quotaManager);// 3、修改该业务经理对应当前客户数
                customerIds.clear();//清空集合，用于下次循环
                msg.append(userName + "分配成功；<br>");
            }else {
                msg.append(userName + "无配额信息；<br>");
            }
        }
        //所有业务经理都分配完成后，修改被分配业务经理对应的当前客户数
        for(Map.Entry<Long, Integer> entry : maps.entrySet()){
            Long userId = entry.getKey();
            Integer mapValue = entry.getValue();
            QuotaManager quotaManager = quotaManagerService.selectQuotaManagerByUserId(userId); //通过userId查询改业务经理的配额信息
            if (StringUtils.isNotNull(quotaManager)) {
                Integer nowTotalCount = quotaManager.getNowTotalCount();    //当前客户数
                quotaManager.setNowTotalCount(nowTotalCount - mapValue);
                quotaManagerService.updateQuotaManager(quotaManager);//修改该客户对应当前客户数
            }
        }
        //如果成功分配的条数等于要被分配的总条数，返回码为0，否则返回码为500，用于页面提示不同的图标
        if (totalcount == oldTotalCount){
            return AjaxResult.success(msg.toString());
        }else {
            return AjaxResult.error(msg.toString());
        }

    }
}
