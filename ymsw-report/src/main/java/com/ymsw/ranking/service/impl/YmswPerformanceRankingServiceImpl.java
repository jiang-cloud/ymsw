package com.ymsw.ranking.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ymsw.common.core.domain.AjaxResult;
import com.ymsw.common.core.domain.BaseEntity;
import com.ymsw.common.utils.DateUtils;
import com.ymsw.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ymsw.ranking.mapper.YmswPerformanceRankingMapper;
import com.ymsw.ranking.domain.YmswPerformanceRanking;
import com.ymsw.ranking.service.IYmswPerformanceRankingService;
import com.ymsw.common.core.text.Convert;

/**
 * 业绩排行Service业务层处理
 * 
 * @author ymsw
 * @date 2020-06-22
 */
@Service
public class YmswPerformanceRankingServiceImpl implements IYmswPerformanceRankingService 
{
    @Autowired
    private YmswPerformanceRankingMapper ymswPerformanceRankingMapper;

    /**
     * 查询业绩排行
     * 
     * @param rankingId 业绩排行ID
     * @return 业绩排行
     */
    @Override
    public YmswPerformanceRanking selectYmswPerformanceRankingById(Long rankingId)
    {
        return ymswPerformanceRankingMapper.selectYmswPerformanceRankingById(rankingId);
    }

    /**
     * 查询业绩排行列表
     * 
     * @param ymswPerformanceRanking 业绩排行
     * @return 业绩排行
     */
    @Override
    public List<YmswPerformanceRanking> selectYmswPerformanceRankingList(YmswPerformanceRanking ymswPerformanceRanking)
    {
        return ymswPerformanceRankingMapper.selectYmswPerformanceRankingList(ymswPerformanceRanking);
    }

    /**
     * 新增业绩排行
     * 
     * @param ymswPerformanceRanking 业绩排行
     * @return 结果
     */
    @Override
    public int insertYmswPerformanceRanking(YmswPerformanceRanking ymswPerformanceRanking)
    {
        return ymswPerformanceRankingMapper.insertYmswPerformanceRanking(ymswPerformanceRanking);
    }

    /**
     * 修改业绩排行
     * 
     * @param ymswPerformanceRanking 业绩排行
     * @return 结果
     */
    @Override
    public int updateYmswPerformanceRanking(YmswPerformanceRanking ymswPerformanceRanking)
    {
        return ymswPerformanceRankingMapper.updateYmswPerformanceRanking(ymswPerformanceRanking);
    }

    /**
     * 删除业绩排行对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteYmswPerformanceRankingByIds(String ids)
    {
        return ymswPerformanceRankingMapper.deleteYmswPerformanceRankingByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除业绩排行信息
     * 
     * @param rankingId 业绩排行ID
     * @return 结果
     */
    @Override
    public int deleteYmswPerformanceRankingById(Long rankingId)
    {
        return ymswPerformanceRankingMapper.deleteYmswPerformanceRankingById(rankingId);
    }

    //业绩排行里，团队排名查询
    @Override
    public List<YmswPerformanceRanking> selectYmswPerformanceRankingListByMinister(YmswPerformanceRanking ymswPerformanceRanking) {
        return ymswPerformanceRankingMapper.selectYmswPerformanceRankingListByMinister(ymswPerformanceRanking);
    }

    //业绩排行里，区部排名查询
    @Override
    public List<YmswPerformanceRanking> selectYmswPerformanceRankingListByDistrict(YmswPerformanceRanking ymswPerformanceRanking) {
        return ymswPerformanceRankingMapper.selectYmswPerformanceRankingListByDistrict(ymswPerformanceRanking);
    }

    //业绩排行里，门店排名查询
    @Override
    public List<YmswPerformanceRanking> selectYmswPerformanceRankingListByPrincipal(YmswPerformanceRanking ymswPerformanceRanking) {
        return ymswPerformanceRankingMapper.selectYmswPerformanceRankingListByPrincipal(ymswPerformanceRanking);
    }

    /**
     * 进件银行占比查询
     */
    @Override
    public AjaxResult channelList(BaseEntity baseEntity, String type) {
        Map<String, Object> params = baseEntity.getParams();
        String beginTime = (String) params.get("beginTime");
        String endTime = (String) params.get("endTime");
        //如果开始时间和结束时间都为空，就设置开始时间为上月第一天，结束时间为上月最后一天，即默认查询上月的数据。
        if (StringUtils.isEmpty(beginTime) && StringUtils.isEmpty(endTime)){
            beginTime = DateUtils.getFirstDayOfLastMonth();//获取上月第一天
            endTime = DateUtils.getLastDayOfLastMonth();//获取上月最后一天
            params.put("beginTime",beginTime);
            params.put("endTime",endTime);
            baseEntity.setParams(params);
        }
        List<Map<String, Double>> pie = new ArrayList<>();//存储查询的数据
        Map<String, Object> data = new HashMap<>();//返回到前端的数据
        String text = "";
        String subtext = "数据范围："+beginTime+" -- "+endTime;
        if ("allowAmount".equals(type)){
            pie = ymswPerformanceRankingMapper.allowAmount(baseEntity);//进件银行占比页面的批款金额查询
            text = "进件银行批款金额占比图";
            subtext += " 单位（万元）";
        }else if ("incomeGeneration".equals(type)){
            pie = ymswPerformanceRankingMapper.incomeGeneration(baseEntity);//进件银行占比页面的收款金额查询
            text = "进件银行创收金额占比图";
            subtext += " 单位（元）";
        } else if ("allowCount".equals(type)){
            pie = ymswPerformanceRankingMapper.allowCount(baseEntity);//进件银行占比页面的批款件数查询
            text = "进件银行批款件数占比图";
            subtext += " 单位（件）";
        } else if ("generationCount".equals(type)){
            pie = ymswPerformanceRankingMapper.generationCount(baseEntity);//进件银行占比页面的收款件数查询
            text = "进件银行收款件数占比图";
            subtext += " 单位（件）";
        }
        data.put("ajaxdata",pie);
        data.put("text",text);
        data.put("subtext",subtext);
        return AjaxResult.success(data);
    }

    /**
     * 月创收增长查询
     */
    @Override
    public AjaxResult generationList(BaseEntity baseEntity) {
        Map<String, Object> params = baseEntity.getParams();
        String dataYearMonth = (String) params.get("dataYearMonth");//查询的年月份 如2020-06
        int lastday;
        //如果前端未传年月，就将当前月份赋值给dataYearMonth，即默认查询当前月份的数据
        if (StringUtils.isEmpty(dataYearMonth)){
            dataYearMonth = DateUtils.parseDateToStr("yyyy-MM",DateUtils.getNowDate());
        }
        //如果查询的年月份是当月，那么截至日期是当前日期，否则截至日期是该月的最后一天
        if (dataYearMonth.equals(DateUtils.parseDateToStr("yyyy-MM",DateUtils.getNowDate()))){
            lastday = Integer.valueOf(DateUtils.parseDateToStr("yyyy-MM-dd",DateUtils.getNowDate()).split("-")[2]);//当前日期的号数
        }else {
            String[] split = dataYearMonth.split("-");
            String lastDayOfMonth = DateUtils.getLastDayOfMonth(Integer.valueOf(split[0]), Integer.valueOf(split[1]));//获取该月份的最后一天，如2020-06-30
            String[] split1 = lastDayOfMonth.split("-");
            lastday = Integer.valueOf(split1[2]);//最后一天的号数
        }
        params.put("beginTime",dataYearMonth + "-01");
        Map<String,Object> data = new HashMap<>();
        List<String> days = new ArrayList<>();
        List<Double> values = new ArrayList<>();
        for (int i = 1; i <= lastday; i++){
            params.put("endTime",dataYearMonth+"-"+i);//查询截至日期
            baseEntity.setParams(params);
            Double totalGeneration = ymswPerformanceRankingMapper.totalGeneration(baseEntity);//查询从yyyy-MM-01到yyyy-MM-i天的累计创收
            days.add(dataYearMonth+"-"+i);
            values.add(totalGeneration);
        }
        data.put("xaxisdata",days);
        data.put("seriesfirstdata",values);
        return AjaxResult.success(data);
    }
}
