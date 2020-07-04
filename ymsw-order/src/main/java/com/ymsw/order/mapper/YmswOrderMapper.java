package com.ymsw.order.mapper;

import com.ymsw.order.domain.YmswOrder;
import java.util.List;

/**
 * 订单信息表Mapper接口
 * 
 * @author ymsw
 * @date 2020-05-22
 */
public interface YmswOrderMapper 
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
    public int updateYmswOrder(YmswOrder ymswOrder);

    /**
     * 删除订单信息表
     * 
     * @param orderId 订单信息表ID
     * @return 结果
     */
    public int deleteYmswOrderById(Long orderId);

    /**
     * 批量删除订单信息表
     * 
     * @param orderIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteYmswOrderByIds(String[] orderIds);
}
