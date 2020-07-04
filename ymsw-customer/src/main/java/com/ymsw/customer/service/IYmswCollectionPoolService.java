package com.ymsw.customer.service;

import com.ymsw.common.core.domain.AjaxResult;
import com.ymsw.customer.domain.YmswCollectionPool;
import com.ymsw.customer.domain.YmswCustomer;
import com.ymsw.customer.vo.YmswReallocationVo;

import java.util.List;

/**
 * 收藏夹-公共池Service接口
 * 
 * @author ymsw
 * @date 2020-05-18
 */
public interface IYmswCollectionPoolService 
{
    /**
     * 查询收藏夹-公共池
     * 
     * @param cpId 收藏夹-公共池ID
     * @return 收藏夹-公共池
     */
    public YmswCollectionPool selectYmswCollectionPoolById(Long cpId);

    /**
     * 查询收藏夹或公共池列表(根据数据范围查询)
     * 
     * @param ymswCustomer 查询条件
     * @return 收藏夹集合
     */
    public List<YmswCustomer> selectYmswCollectionPoolList(YmswCustomer ymswCustomer);

    /**
     * 新增收藏夹-公共池
     * 
     * @param ymswCollectionPool 收藏夹-公共池
     * @return 结果
     */
    public int insertYmswCollectionPool(YmswCollectionPool ymswCollectionPool);

    /**
     * 修改收藏夹-公共池
     * 
     * @param ymswCollectionPool 收藏夹-公共池
     * @return 结果
     */
    public int updateYmswCollectionPool(YmswCollectionPool ymswCollectionPool);

    /**
     * 批量删除收藏夹-公共池
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteYmswCollectionPoolByIds(String ids);

    /**
     * 删除收藏夹-公共池信息
     * 
     * @param cpId 收藏夹-公共池ID
     * @return 结果
     */
    public int deleteYmswCollectionPoolById(Long cpId);

    /**
     * 批量加入收藏夹-公共池
     */
    AjaxResult addToCollectionPool (YmswReallocationVo reallocationVo);

    //抽回重分配时，批量从公共池里删除数据
    int batchDeleteByCustomerIds(List<Long> customerIds);
}
