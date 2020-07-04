package com.ymsw.order.service;

import com.ymsw.common.core.domain.AjaxResult;
import com.ymsw.order.domain.YmswOrder;
import java.util.List;

/**
 * 订单信息表Service接口
 * 
 * @author ymsw
 * @date 2020-05-22
 */
public interface IYmswOrderService 
{
    /**
     * 查询订单信息表
     * 
     * @param orderId 订单信息表ID
     * @return 订单信息表
     */
    public YmswOrder selectYmswOrderById(Long orderId);

    /**
     * 查询订单信息表列表
     * 
     * @param ymswOrder 订单信息表
     * @return 订单信息表集合
     */
    public List<YmswOrder> selectYmswOrderList(YmswOrder ymswOrder);

    /**
     * 新增订单信息表
     * 
     * @param ymswOrder 订单信息表
     * @return 结果
     */
    public int insertYmswOrder(YmswOrder ymswOrder);

    /**
     * 修改订单信息表
     * 
     * @param ymswOrder 订单信息表
     * @return 结果
     */
    public AjaxResult updateYmswOrder(YmswOrder ymswOrder);

    /**
     * 批量删除订单信息表
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteYmswOrderByIds(String ids);

    /**
     * 删除订单信息表信息
     * 
     * @param orderId 订单信息表ID
     * @return 结果
     */
    public int deleteYmswOrderById(Long orderId);
}
