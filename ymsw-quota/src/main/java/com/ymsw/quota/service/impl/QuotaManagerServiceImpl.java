package com.ymsw.quota.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ymsw.quota.mapper.QuotaManagerMapper;
import com.ymsw.quota.domain.QuotaManager;
import com.ymsw.quota.service.IQuotaManagerService;
import com.ymsw.common.core.text.Convert;

/**
 * 配额管理Service业务层处理
 * 
 * @author ymsw
 * @date 2020-06-04
 */
@Service
public class QuotaManagerServiceImpl implements IQuotaManagerService 
{
    @Autowired
    private QuotaManagerMapper quotaManagerMapper;

    /**
     * 查询配额管理
     * 
     * @param quotaId 配额管理ID
     * @return 配额管理
     */
    @Override
    public QuotaManager selectQuotaManagerById(Long quotaId)
    {
        return quotaManagerMapper.selectQuotaManagerById(quotaId);
    }

    /**
     * 查询配额管理列表
     * 
     * @param quotaManager 配额管理
     * @return 配额管理
     */
    @Override
    public List<QuotaManager> selectQuotaManagerList(QuotaManager quotaManager)
    {
        return quotaManagerMapper.selectQuotaManagerList(quotaManager);
    }

    /**
     * 新增配额管理
     * 
     * @param quotaManager 配额管理
     * @return 结果
     */
    @Override
    public int insertQuotaManager(QuotaManager quotaManager)
    {
        return quotaManagerMapper.insertQuotaManager(quotaManager);
    }

    /**
     * 修改配额管理
     * 
     * @param quotaManager 配额管理
     * @return 结果
     */
    @Override
    public int updateQuotaManager(QuotaManager quotaManager)
    {
        return quotaManagerMapper.updateQuotaManager(quotaManager);
    }

    /**
     * 删除配额管理对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteQuotaManagerByIds(String ids)
    {
        return quotaManagerMapper.deleteQuotaManagerByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除配额管理信息
     * 
     * @param quotaId 配额管理ID
     * @return 结果
     */
    @Override
    public int deleteQuotaManagerById(Long quotaId)
    {
        return quotaManagerMapper.deleteQuotaManagerById(quotaId);
    }

    /**
     * 批量修改配额状态
     */
    @Override
    public int changeStatus(Map<String, Object> params) {
        return quotaManagerMapper.changeStatus(params);
    }

    /**
     * 回显配额总数
     */
    @Override
    public int countTotal() {
        int total = quotaManagerMapper.countTotal();
        return total;
    }

    /**
     * 批量配额设置
     */
    @Override
    public int batchEdit(Map<String, Object> params) {
        return quotaManagerMapper.batchEdit(params);
    }

    /**
     * 根据门店（用户标识）修改总限额数
     */
    @Override
    public int editAllowTotalCount(Map<String, Object> params) {
        return quotaManagerMapper.editAllowTotalCount(params);
    }

    /**
     * 根据quotaIds批量修改总限额数
     */
    @Override
    public int editTotalCount(Map<String, Object> params) {
        return quotaManagerMapper.editTotalCount(params);
    }

    /**
     * 根据userId查询配额信息
     */
    @Override
    public QuotaManager selectQuotaManagerByUserId(Long userId) {
        return quotaManagerMapper.selectQuotaManagerByUserId(userId);
    }
}
