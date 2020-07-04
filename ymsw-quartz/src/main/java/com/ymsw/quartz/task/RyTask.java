package com.ymsw.quartz.task;

import com.ymsw.common.utils.DateUtils;
import com.ymsw.common.utils.StringUtils;
import com.ymsw.customer.domain.YmswCustomer;
import com.ymsw.customer.mapper.YmswCollectionPoolMapper;
import com.ymsw.customer.mapper.YmswCustomerMapper;
import com.ymsw.customer.service.impl.YmswCollectionPoolServiceImpl;
import com.ymsw.quota.domain.QuotaManager;
import com.ymsw.quota.mapper.QuotaManagerMapper;
import com.ymsw.ranking.domain.YmswPerformanceRanking;
import com.ymsw.ranking.mapper.YmswPerformanceRankingMapper;
import com.ymsw.system.domain.SysConfig;
import com.ymsw.system.domain.SysUser;
import com.ymsw.system.mapper.SysUserMapper;
import com.ymsw.system.service.ISysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 定时任务调度测试
 * 
 * @author ymsw
 */
@Component("ryTask")
public class RyTask
{
    @Autowired
    private ISysConfigService configService;

    @Autowired
    private YmswCustomerMapper ymswCustomerMapper;

    @Autowired
    private YmswCollectionPoolMapper ymswCollectionPoolMapper;

    @Autowired
    private QuotaManagerMapper quotaManagerMapper;

    @Autowired
    private YmswPerformanceRankingMapper ymswPerFormanceRankingMapper;

    @Autowired
    private SysUserMapper userMapper;

    public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i)
    {
        System.out.println(StringUtils.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
    }

    public void ryParams(String params)
    {
        System.out.println("执行有参方法：" + params);
    }

    public void ryNoParams()
    {
        System.out.println("执行无参方法");
    }

    //自动抽回定时任务
    @Transactional
    public void autorealloc()
    {
        SysConfig config = new SysConfig();
        config.setConfigId(4L);// 4 为自动抽回设置的configId，固定不变，注意：不能修改。
        SysConfig sysConfig = configService.autoReallocConfig(config);//查询自动抽回设置
        if (StringUtils.isNotNull(sysConfig)){
            String status = sysConfig.getConfigKey();//获取自动抽回状态
            String days = sysConfig.getConfigValue();//获取自动抽回的天数
            //如果开启了自动抽回，就进行抽回
            if ("1".equals(status)){    //状态 0 关闭  1  开启
                List<YmswCustomer> customerList = ymswCustomerMapper.selectAutoReallocIds(days);   //查询出需要自动抽回的客户ids
                if (StringUtils.isNotEmpty(customerList)){
                    List<String> customerIds = new ArrayList<>();
                    for (YmswCustomer ymswCustomer : customerList) {
                        customerIds.add(String.valueOf(ymswCustomer.getCustomerId()));
                    }
                    ymswCustomerMapper.updateUseridToNull(customerIds); //1、将客户ids对应的user_id批量设置为null
                    //2、同时批量添加到公共池  参数“2”是公共池，操作人id设置为null
                    ymswCollectionPoolMapper.batchInsertYmswCollectionPool(YmswCollectionPoolServiceImpl.getAddList(customerList,"2",null));
                }
            }
        }
        //自动抽回完成后更新当前客户数
        List<Map<String, Long>> list = ymswCustomerMapper.selectCount();//按userId分组查询客户数（查询每个业务经理的客户数量）
        for (Map<String, Long> map : list) {
            Long userId = map.get("userId");//获取userId
            Long totalCount = map.get("totalCount");//获取该userId的客户数量
            QuotaManager quotaManager = quotaManagerMapper.selectQuotaManagerByUserId(userId);//查询该userId的配额信息
            //配额信息如果存在，就修改当前客户数，如果不存在，就添加一条配额信息
            if (StringUtils.isNotNull(quotaManager)){
                quotaManager.setNowTotalCount(totalCount.intValue());
                quotaManagerMapper.updateQuotaManager(quotaManager);
            }else {
                quotaManager = new QuotaManager();
                quotaManager.setUserId(userId.intValue());
                quotaManager.setNowTotalCount(totalCount.intValue());//当前客户数
                quotaManager.setAllowTotalCount(500);   //总限额数
                quotaManager.setAllowTodayCount(5); //今日配额数
                quotaManager.setNowTodayCount(0);//今日已分配客户数
                quotaManager.setQuotaStatus("1");   //配额状态 0 关闭 1开启
                quotaManagerMapper.insertQuotaManager(quotaManager);
            }
        }
    }

    /**
     * 每日统计
     * 1、当月累计创收 2、当月累计进件数 3、当月累计收款笔数 4、当月累计批款总金额(万元) 5、平均费率
     * 如果当前日期是本月第一天，就就查询出在职且分配客户的所有员工，在业绩排行表里添加一天员工记录
     */
    @Transactional
    public void performanceRanking()
    {
        Date nowDate = DateUtils.getNowDate();//当前时间
        String today = DateUtils.parseDateToStr("yyyy-MM-dd", nowDate);
        String[] split = today.split("-");
        String firstDay;
        String lastDay;
        String dataYearMonth;
        //如果当前日期是本月第一天，firstDay为上月第一天，lastDay就为上月最后一天。否则firstDay为本月第一天，lastDay为本月最后一天
        if ("01".equals(split[2])){
            firstDay = DateUtils.getFirstDayOfLastMonth();//获取上月的第一天
            lastDay = DateUtils.getLastDayOfLastMonth();//获取上月的最后一天
            dataYearMonth = DateUtils.getLastMonth();//获取上月的年月（yyyy-MM）
            //如果当前日期是本月第一天，就查询出在职且分配客户的所有员工，添加到业绩排行表里
            List<SysUser> sysUsers = userMapper.selectIsDistributeUsers();//查询在职的且分配客户的所有员工
            for (SysUser sysUser : sysUsers) {
                //在业绩排行表里添加员工排行记录
                insertYmswPerformanceRanking(sysUser.getUserId(),DateUtils.parseDateToStr("yyyy-MM",nowDate));
            }
        }else {
            firstDay = DateUtils.getFirstDayOfMonth(Integer.parseInt(split[0]), Integer.parseInt(split[1]));//获取本月份的第一天
            lastDay = DateUtils.getLastDayOfMonth(Integer.parseInt(split[0]), Integer.parseInt(split[1]));//获取本月份的最后一天
            dataYearMonth = DateUtils.parseDateToStr("yyyy-MM", nowDate);//获取本月分的年月（yyyy-MM）
        }
        //存放需要更新的业绩排行记录  key是userId
        Map<Long,YmswPerformanceRanking> map = new HashMap<>();
        //1、统计月总进件笔数
        List<YmswPerformanceRanking> incomingRankings = ymswPerFormanceRankingMapper.selectTotalIncomingCount(firstDay,lastDay);//查询月总进件笔数
        for (YmswPerformanceRanking ymswPerformanceRanking : incomingRankings) {
            Long userId = ymswPerformanceRanking.getUserId();
            doMap(map,userId,dataYearMonth);//查询该userId的排行记录并put到map里
            YmswPerformanceRanking ranking = map.get(userId);//从map里取出该userId的排行记录
            ranking.setTotalIncomingCount(ymswPerformanceRanking.getTotalIncomingCount());//设置月累计进件数
            ranking.setTodayIncomingCount(0L);//设置今日进件数为0
            map.put(userId,ranking);
        }

        //2、统计月总收款笔数
        List<YmswPerformanceRanking> collectionRankings = ymswPerFormanceRankingMapper.selectTotalCollectionCount(firstDay,lastDay);//查询月总收款笔数
        for (YmswPerformanceRanking ymswPerformanceRanking : collectionRankings) {
            Long userId = ymswPerformanceRanking.getUserId();
            doMap(map,userId,dataYearMonth);//查询该userId的排行记录并put到map里
            YmswPerformanceRanking ranking = map.get(userId);//从map里取出该userId的排行记录
            ranking.setTotalCollectionCount(ymswPerformanceRanking.getTotalCollectionCount());//设置月累计收款数
            ranking.setTodayCollectionCount(0L);//设置今日收款数为0
            map.put(userId,ranking);
        }

        //3、统计月批款总金额
        List<YmswPerformanceRanking> allowAmountRankings = ymswPerFormanceRankingMapper.selectTotalAllowAmount(firstDay,lastDay);//查询月总批款总金额
        for (YmswPerformanceRanking ymswPerformanceRanking : allowAmountRankings) {
            Long userId = ymswPerformanceRanking.getUserId();
            doMap(map,userId,dataYearMonth);//查询该userId的排行记录并put到map里
            YmswPerformanceRanking ranking = map.get(userId);//从map里取出该userId的排行记录
            ranking.setTotalAllowAmount(ymswPerformanceRanking.getTotalAllowAmount());//设置月批款总金额
            map.put(userId,ranking);
        }

        //4、统计月总创收
        List<YmswPerformanceRanking> totalGenerationRankings = ymswPerFormanceRankingMapper.selectTotalGeneration(firstDay,lastDay);//查询月总创收
        for (YmswPerformanceRanking ymswPerformanceRanking : totalGenerationRankings) {
            Long userId = ymswPerformanceRanking.getUserId();
            doMap(map,userId,dataYearMonth);//查询该userId的排行记录并put到map里
            YmswPerformanceRanking ranking = map.get(userId);//从map里取出该userId的排行记录
            ranking.setTotalGeneration(ymswPerformanceRanking.getTotalGeneration());//设置月总创收
            ranking.setTodayGeneration(0D);//设置今日创收为0
            map.put(userId,ranking);
        }

        //5、统计平均费率
        List<YmswPerformanceRanking> orderRateRankings = ymswPerFormanceRankingMapper.selectOrderRate(firstDay,lastDay);//查询平均费率
        for (YmswPerformanceRanking ymswPerformanceRanking : orderRateRankings) {
            Long userId = ymswPerformanceRanking.getUserId();
            doMap(map,userId,dataYearMonth);//查询该userId的排行记录并put到map里
            YmswPerformanceRanking ranking = map.get(userId);//从map里取出该userId的排行记录
            ranking.setAvgOrderRate(ymswPerformanceRanking.getAvgOrderRate());//设置平均费率
            map.put(userId,ranking);
        }

        //6、更新业绩排行记录
        for (YmswPerformanceRanking value : map.values()) {
            ymswPerFormanceRankingMapper.updateYmswPerformanceRanking(value);
        }

    }

    //查询map里是否存在该员工的业绩排行记录，如果不存在，就查询业绩排行表里该员工的记录，如果表里也不存在，就insert一条该员工的排行记录，最后将该排行记录put到map里
    private void doMap(Map<Long,YmswPerformanceRanking> map,Long userId,String dataYearMonth){
        if (!map.containsKey(userId)){
            YmswPerformanceRanking ranking = new YmswPerformanceRanking();
            ranking.setUserId(userId);
            ranking.setDataYearMonth(dataYearMonth);
            YmswPerformanceRanking dbRanking = ymswPerFormanceRankingMapper.selectYmswPerformanceRanking(ranking);//通过userId和dataYearMonth查询该业务经理该月份的统计信息
            if (StringUtils.isNull(dbRanking)){
                dbRanking = insertYmswPerformanceRanking(userId,dataYearMonth);//如果dbRanking不存在，就添加一条该员工的业绩排行记录
            }
            map.put(userId,dbRanking);
        }
    }

    //添加员工业绩排行记录
    private YmswPerformanceRanking insertYmswPerformanceRanking(Long UserId,String dataYearMonth){
        YmswPerformanceRanking ranking = new YmswPerformanceRanking();
        ranking.setUserId(UserId);
        ranking.setDataYearMonth(dataYearMonth);
        ranking.setTotalIncomingCount(0L);
        ranking.setTodayIncomingCount(0L);
        ranking.setTotalCollectionCount(0L);
        ranking.setTodayCollectionCount(0L);
        ranking.setTotalGeneration(0D);
        ranking.setTodayGeneration(0D);
        ranking.setTotalAllowAmount(0);
        ranking.setAvgOrderRate(0D);
        ymswPerFormanceRankingMapper.insertYmswPerformanceRanking(ranking);
        return ranking;
    }
}
