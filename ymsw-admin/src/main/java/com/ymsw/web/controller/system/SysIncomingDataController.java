package com.ymsw.web.controller.system;

import com.ymsw.common.core.controller.BaseController;
import com.ymsw.common.core.domain.AjaxResult;
import com.ymsw.common.json.JSONObject;
import com.ymsw.common.utils.StringUtils;
import com.ymsw.customer.domain.YmswCustomer;
import com.ymsw.customer.service.IYmswCustomerService;
import com.ymsw.framework.config.AccessPhoneConfig;
import com.ymsw.framework.web.service.DictService;
import com.ymsw.quota.domain.QuotaManager;
import com.ymsw.quota.mapper.QuotaManagerMapper;
import com.ymsw.system.domain.SysDictData;
import com.ymsw.system.domain.SysUser;
import com.ymsw.system.service.ISysDictDataService;
import com.ymsw.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 引流数据控制器
 * @author Administrator on 2020/5/25.
 * @version 1.0
 */
@RestController
@RequestMapping("/incomingData/main")
public class SysIncomingDataController extends BaseController {
    @Autowired
    IYmswCustomerService iYmswCustomerService;
    @Autowired
    AccessPhoneConfig accessPhoneConfig;
    @Autowired
    ISysUserService iSysUserService;
    @Autowired
    ISysDictDataService sysDictDataService;
    @Autowired
    private QuotaManagerMapper quotaManagerMapper;

    @RequestMapping("addData")
    public AjaxResult incomingFromOther(HttpServletRequest request){
        SysDictData sysDictData = new SysDictData();
        sysDictData.setDictLabel("incoming_data_status");
        sysDictData.setDictType("ymsw_config");
        List<SysDictData> sysDictDataList = sysDictDataService.selectDictDataList(sysDictData);
        sysDictData = sysDictDataList.get(0);

        JSONObject resInfo = new JSONObject();
        resInfo.put("resStatus","0");
        resInfo.put("resMsg", "数据引流接口已暂停");
        if("0".equals(sysDictData.getDictValue()) ){

            return  AjaxResult.error("接口数据已暂停，暂不引入数据",resInfo);
        }
        String customerName = request.getParameter("customerName");
        String customerSex = request.getParameter("customerSex");
        String customerPhone = request.getParameter("customerPhone");
        String customerQuota = request.getParameter("customerQuota");
        YmswCustomer ymswCustomer =new YmswCustomer();
        //获取能分配的用户列表
        List<SysUser> sysUserList = iSysUserService.selectIsDistributeUsers();
        Long userId;
        //当userId不为null时停止循环
        do {
            userId = getUserId(sysUserList);
        }while (StringUtils.isNull(userId));
        ymswCustomer.setUserId(userId);
        ymswCustomer.setCustomerName(customerName);
        ymswCustomer.setCustomerSex(customerSex);
        ymswCustomer.setCustomerPhone(customerPhone);
        ymswCustomer.setCustomerQuota(Integer.valueOf(customerQuota));
        QuotaManager quotaManager = quotaManagerMapper.selectQuotaManagerByUserId(userId);// 查询该userId的配额信息
        return  iYmswCustomerService.insertCustomer(ymswCustomer, quotaManager);
    }

    //获取可以分配客户的userId
    private Long getUserId(List<SysUser> sysUserList){
        //随机进行分配
        int i = (int)(Math.random()*sysUserList.size())+1;
        SysUser sysUser = sysUserList.get(i-1);
        Long userId = sysUser.getUserId();
        QuotaManager quotaManager = quotaManagerMapper.selectQuotaManagerByUserId(userId);//查询该userId的配额信息
        //如果配额信息存在，就进行判断是否允许添加客户，如果不存在，就添加一条配额信息，并返回userId
        if (StringUtils.isNotNull(quotaManager)){
            String quotaStatus = quotaManager.getQuotaStatus();
            Integer allowTodayCount = quotaManager.getAllowTodayCount();//今日配额数
            Integer nowTodayCount = quotaManager.getNowTodayCount();//今日已分配客户数
            Integer allowTotalCount = quotaManager.getAllowTotalCount();    //总限额数
            Integer nowTotalCount = quotaManager.getNowTotalCount();//当前客户数
            boolean notAllow = nowTodayCount + 1 > allowTodayCount ? true : false;
            boolean notAllow2 = nowTotalCount + 1 > allowTotalCount ? true : false;
            // 如果配额状态是关闭，就不分配客户  配额状态 0 关闭 1开启
            if ("0".equals(quotaStatus)){
                return null;
            }
            if (notAllow || notAllow2){
                return null;
            }
        }else {
            quotaManager = new QuotaManager();
            quotaManager.setUserId(userId.intValue());
            quotaManager.setAllowTotalCount(500);//总限额数
            quotaManager.setNowTotalCount(0);//当前客户数
            quotaManager.setAllowTodayCount(5);//今日配额数
            quotaManager.setNowTodayCount(0);//今日已分配客户数
            quotaManager.setQuotaStatus("1");//配额状态0 关闭 1开启
            quotaManager.setUserId(userId.intValue());
            quotaManagerMapper.insertQuotaManager(quotaManager);//添加一条配额信息
        }
        return userId;
    }
}
