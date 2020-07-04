package com.ymsw.customer.mapper;

import com.ymsw.customer.domain.YmswCollectionPool;
import com.ymsw.customer.domain.YmswCustomer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 收藏夹-公共池Mapper接口
 * 
 * @author ymsw
 * @date 2020-05-18
 */
public interface YmswCollectionPoolMapper 
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
     * 删除收藏夹-公共池
     * 
     * @param cpId 收藏夹-公共池ID
     * @return 结果
     */
    public int deleteYmswCollectionPoolById(Long cpId);

    /**
     * 批量删除收藏夹-公共池
     * 
     * @param cpIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteYmswCollectionPoolByIds(String[] cpIds);

    /**
     * 批量添加收藏夹-公共池
     *
     * @param list 需要添加的集合
     * @return 结果
     */
    public int batchInsertYmswCollectionPool (List<YmswCollectionPool> list);

    /**
     * 查询某个user的收藏数量
     *
     * @param userId 收藏人id
     * @return 结果
     */
    public int selectCountByUserId (Long userId);

    int selectIsInCollectionPool(@Param("customerId") Long customerId, @Param("cpType")String cpType);

    //抽回重分配时，批量从公共池里删除数据
    int batchDeleteByCustomerIds(List<Long> customerIds);
}
