package com.ymsw.order.service.impl;

import com.ymsw.common.annotation.DataScope;
import com.ymsw.common.core.domain.AjaxResult;
import com.ymsw.common.core.text.Convert;
import com.ymsw.common.utils.DateUtils;
import com.ymsw.common.utils.StringUtils;
import com.ymsw.order.domain.YmswOrder;
import com.ymsw.order.mapper.YmswOrderMapper;
import com.ymsw.order.service.IYmswOrderService;
import com.ymsw.ranking.domain.YmswPerformanceRanking;
import com.ymsw.ranking.mapper.YmswPerformanceRankingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 订单信息表Service业务层处理
 * 
 * @author ymsw
 * @date 2020-05-22
 */
@Service
public class YmswOrderServiceImpl implements IYmswOrderService 
{
    @Autowired
    private YmswOrderMapper ymswOrderMapper;

    @Autowired
    private YmswPerformanceRankingMapper ymswPerFormanceRankingMapper;

    /**
     * 查询订单信息表
     * 
     * @param orderId 订单信息表ID
     * @return 订单信息表
     */
    @Override
    public YmswOrder selectYmswOrderById(Long orderId)
    {
        return ymswOrderMapper.selectYmswOrderById(orderId);
    }

    /**
     * 查询订单信息表列表
     * 
     * @param ymswOrder 订单信息表
     * @return 订单信息表
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<YmswOrder> selectYmswOrderList(YmswOrder ymswOrder)
    {
        return ymswOrderMapper.selectYmswOrderList(ymswOrder);
    }

    /**
     * 新增订单信息表
     * 
     * @param ymswOrder 订单信息表
     * @return 结果
     */
    @Override
    public int insertYmswOrder(YmswOrder ymswOrder)
    {
        ymswOrder.setOrderStatus("1");//设置订单状态为已签约  （1 已签约  2 已进件   3 已批款   4 已收款   5 已拒绝）
        ymswOrder.setAddTime(DateUtils.getNowDate());//设置添加签约日期为当前时间
        return ymswOrderMapper.insertYmswOrder(ymswOrder);
    }

    /**
     * 修改订单信息表
     * 
     * @param ymswOrder 订单信息表
     * @return 结果
     */
    @Override
    @Transactional
    public AjaxResult updateYmswOrder(YmswOrder ymswOrder)
    {
        Date nowDate = DateUtils.getNowDate();//当前时间
        Long orderId = ymswOrder.getOrderId();
        String orderStatus = ymswOrder.getOrderStatus();
        YmswOrder dbYmswOrder = ymswOrderMapper.selectYmswOrderById(orderId);//通过orderId查询数据库里该订单信息
        if (StringUtils.isNull(dbYmswOrder)){
            return AjaxResult.error("签约进件不存在！");
        }

        Long userId = dbYmswOrder.getUserId();//获取该订单的业务经理userId

        if (Integer.valueOf(orderStatus)<Integer.valueOf(dbYmswOrder.getOrderStatus())){//如果选择的订单状态小于数据库里的订单状态，就提示错误
            return AjaxResult.error("订单状态选择错误！");
        }

        //订单状态为 2 已进件 3 已批款 4 已收款时进件助理、进件渠道、进件类型不能为空。
        if (Integer.valueOf(orderStatus)>1 && Integer.valueOf(orderStatus)<5){
            if (StringUtils.isEmpty(ymswOrder.getIncomingAssistant())){
                return AjaxResult.error("请选择进件助理！");
            }
            if (StringUtils.isEmpty(ymswOrder.getIncomingChannel())){
                return AjaxResult.error("请选择进件渠道！");
            }
            if (StringUtils.isEmpty(ymswOrder.getIncomingType())){
                return AjaxResult.error("请选择进件类型！");
            }
        }

        YmswPerformanceRanking temp = new YmswPerformanceRanking();
        temp.setUserId(userId);
        temp.setDataYearMonth(DateUtils.parseDateToStr("yyyy-MM",nowDate));
        //通过userId和dataYearMonth查询该业务经理该月份的统计信息
        YmswPerformanceRanking ranking = ymswPerFormanceRankingMapper.selectYmswPerformanceRanking(temp);
        if (StringUtils.isNull(ranking)){
            ranking = new YmswPerformanceRanking();
            ranking.setUserId(userId);
            ranking.setDataYearMonth(DateUtils.parseDateToStr("yyyy-MM",nowDate));
            ranking.setTotalIncomingCount(0L);
            ranking.setTodayIncomingCount(0L);
            ranking.setTotalCollectionCount(0L);
            ranking.setTodayCollectionCount(0L);
            ranking.setTotalGeneration(0D);
            ranking.setTodayGeneration(0D);
            ranking.setTotalAllowAmount(0);
            ranking.setAvgOrderRate(0D);
            ymswPerFormanceRankingMapper.insertYmswPerformanceRanking(ranking);
        }

        if ("2".equals(orderStatus)){   //订单状态 1 已签约 2 已进件 3 已批款 4 已收款 5 已拒绝
            if (StringUtils.isNull(dbYmswOrder.getIncomingTime())){//如果进件日期为空，就设置当前时间为进件日期
                ymswOrder.setIncomingTime(nowDate);//设置进件日期
                ranking.setTotalIncomingCount(ranking.getTotalIncomingCount() + 1);//累计进件笔数+1
                ranking.setTodayIncomingCount(ranking.getTodayIncomingCount() + 1);//当日进件笔数+1
            }
        }else if ("3".equals(orderStatus)){
            if (StringUtils.isNull(dbYmswOrder.getAllowTime())){//如果批款日期为空，就进行批款总额度累加
                ymswOrder.setAllowTime(nowDate);//设置批款日期
                ranking.setTotalAllowAmount(ranking.getTotalAllowAmount() + ymswOrder.getAllowAmount());
            }
        } else if ("4".equals(orderStatus)){
            if (StringUtils.isNull(dbYmswOrder.getCollectionTime())){//如果收款日期为空，就设置当前时间为收款日期
                ymswOrder.setCollectionTime(nowDate);//设置收款日期
                ranking.setTotalCollectionCount(ranking.getTotalCollectionCount() + 1);//累计收款笔数+1
                ranking.setTodayCollectionCount(ranking.getTodayCollectionCount() + 1);//当日收款笔数+1
                ranking.setTotalGeneration(ranking.getTotalGeneration() + ymswOrder.getIncomeGeneration());//累计创收增加
                ranking.setTodayGeneration(ranking.getTodayGeneration() + ymswOrder.getIncomeGeneration());//今日创收增加
            }
        }
        ymswOrder.setUpdateTime(DateUtils.getNowDate());
        ymswPerFormanceRankingMapper.updateYmswPerformanceRanking(ranking);//更新
        ymswOrderMapper.updateYmswOrder(ymswOrder);
        return AjaxResult.success();
    }

    /**
     * 删除订单信息表对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteYmswOrderByIds(String ids)
    {
        return ymswOrderMapper.deleteYmswOrderByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除订单信息表信息
     * 
     * @param orderId 订单信息表ID
     * @return 结果
     */
    @Override
    public int deleteYmswOrderById(Long orderId)
    {
        return ymswOrderMapper.deleteYmswOrderById(orderId);
    }
}
