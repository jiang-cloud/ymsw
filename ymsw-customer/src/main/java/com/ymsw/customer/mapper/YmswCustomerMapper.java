package com.ymsw.customer.mapper;

import com.ymsw.customer.domain.YmswCustomer;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 客户信息表Mapper接口
 * 
 * @author ymsw
 * @date 2020-05-18
 */
public interface YmswCustomerMapper 
{
    /**
     * 查询客户信息表
     * 
     * @param customerId 客户信息表ID
     * @return 客户信息表
     */
    public YmswCustomer selectYmswCustomerById(Long customerId);

    /**
     * 我的客户查询客户列表（通过userId查询）
     * 数据范围：仅查询个人的客户
     * @param ymswCustomer 客户信息表
     * @return 客户信息表集合
     */
    public List<YmswCustomer> selectYmswCustomerList(YmswCustomer ymswCustomer);

    /**
     * 客户管理 -→ 客户列表页面 查询客户列表（使用数据范围）
     * 数据范围：查询客户表里的所有客户（但不包括收藏夹里的客户，而包括公共池里的客户）
     * @param ymswCustomer 客户信息表
     * @return 客户信息表集合
     */
    public List<YmswCustomer> selectManageList(YmswCustomer ymswCustomer);

    /**
     * 新增客户信息表
     * 
     * @param ymswCustomer 客户信息表
     * @return 结果
     */
    public int insertYmswCustomer(YmswCustomer ymswCustomer);

    /**
     * 修改客户信息表
     * 
     * @param ymswCustomer 客户信息表
     * @return 结果
     */
    public int updateYmswCustomer(YmswCustomer ymswCustomer);

    /**
     * 删除客户信息表
     * 
     * @param customerId 客户信息表ID
     * @return 结果
     */
    public int deleteYmswCustomerById(Long customerId);

    /**
     * 批量删除客户信息表
     * 
     * @param customerIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteYmswCustomerByIds(String[] customerIds);

    /**
     * 通过手机号码查询该客户最后一次申请时间的记录
     *
     * @param customerPhone 客户手机号码
     * @return 结果
     */
    public YmswCustomer selectLastYmswCustomerByPhone(String customerPhone);

    //批量修改客户的归属顾问为空
    public int updateUseridToNull(List<String> customerIds);

    /**
     * 根据客户电话号码查询客户信息（客户表里可能存在多条相同的电话号码，取最后一次添加的客户信息【即申请时间是最大的】）
     */
    public YmswCustomer getCustomerInfo(String customerPhone);

    /**
     * 查询出需要自动抽回的客户ids
     */
    List<YmswCustomer> selectAutoReallocIds(String days);

    int batchUpdateUserId(@Param("userId") Long userId, @Param("customerIds") List<Long> customerIds);

    /**
     * 按userId分组查询客户数（查询每个业务经理的客户数量）
     */
    List<Map<String, Long>> selectCount();
}
