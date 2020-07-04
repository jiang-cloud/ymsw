package com.ymsw.quota.mapper;

import com.ymsw.quota.domain.QuotaManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 配额管理Mapper接口
 * 
 * @author ymsw
 * @date 2020-06-04
 */
public interface QuotaManagerMapper 
{
    /**
     * 查询配额管理
     * 
     * @param quotaId 配额管理ID
     * @return 配额管理
     */
    public QuotaManager selectQuotaManagerById(Long quotaId);

    /**
     * 查询配额管理列表
     * 
     * @param quotaManager 配额管理
     * @return 配额管理集合
     */
    public List<QuotaManager> selectQuotaManagerList(QuotaManager quotaManager);

    /**
     * 新增配额管理
     * 
     * @param quotaManager 配额管理
     * @return 结果
     */
    public int insertQuotaManager(QuotaManager quotaManager);

    /**
     * 修改配额管理
     * 
     * @param quotaManager 配额管理
     * @return 结果
     */
    public int updateQuotaManager(QuotaManager quotaManager);

    /**
     * 删除配额管理
     * 
     * @param quotaId 配额管理ID
     * @return 结果
     */
    public int deleteQuotaManagerById(Long quotaId);

    /**
     * 批量删除配额管理
     * 
     * @param quotaIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteQuotaManagerByIds(String[] quotaIds);

    /**
     * 批量修改配额状态
     */
    int changeStatus(Map<String, Object> params);

    /**
     * 回显配额总数
     */
    int countTotal();

    /**
     * 批量配额设置
     */
    int batchEdit(Map<String, Object> params);

    /**
     * 根据门店（用户标识）修改总限额数
     */
    int editAllowTotalCount(Map<String, Object> params);

    /**
     * 根据quotaIds批量修改总限额数
     */
    int editTotalCount(Map<String, Object> params);

    /**
     * 根据userId查询配额信息
     */
    QuotaManager selectQuotaManagerByUserId(Long userId);
}
