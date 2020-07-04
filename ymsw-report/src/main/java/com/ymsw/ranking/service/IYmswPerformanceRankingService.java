package com.ymsw.ranking.service;

import com.ymsw.common.core.domain.AjaxResult;
import com.ymsw.common.core.domain.BaseEntity;
import com.ymsw.ranking.domain.YmswPerformanceRanking;
import java.util.List;

/**
 * 业绩排行Service接口
 * 
 * @author ymsw
 * @date 2020-06-22
 */
public interface IYmswPerformanceRankingService 
{
    /**
     * 查询业绩排行
     * 
     * @param rankingId 业绩排行ID
     * @return 业绩排行
     */
    public YmswPerformanceRanking selectYmswPerformanceRankingById(Long rankingId);

    /**
     * 查询业绩排行列表
     * 
     * @param ymswPerformanceRanking 业绩排行
     * @return 业绩排行集合
     */
    public List<YmswPerformanceRanking> selectYmswPerformanceRankingList(YmswPerformanceRanking ymswPerformanceRanking);

    /**
     * 新增业绩排行
     * 
     * @param ymswPerformanceRanking 业绩排行
     * @return 结果
     */
    public int insertYmswPerformanceRanking(YmswPerformanceRanking ymswPerformanceRanking);

    /**
     * 修改业绩排行
     * 
     * @param ymswPerformanceRanking 业绩排行
     * @return 结果
     */
    public int updateYmswPerformanceRanking(YmswPerformanceRanking ymswPerformanceRanking);

    /**
     * 批量删除业绩排行
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteYmswPerformanceRankingByIds(String ids);

    /**
     * 删除业绩排行信息
     * 
     * @param rankingId 业绩排行ID
     * @return 结果
     */
    public int deleteYmswPerformanceRankingById(Long rankingId);

    //业绩排行里，团队排名查询
    List<YmswPerformanceRanking> selectYmswPerformanceRankingListByMinister(YmswPerformanceRanking ymswPerformanceRanking);

    //业绩排行里，区部排名查询
    List<YmswPerformanceRanking> selectYmswPerformanceRankingListByDistrict(YmswPerformanceRanking ymswPerformanceRanking);

    //业绩排行里，门店排名查询
    List<YmswPerformanceRanking> selectYmswPerformanceRankingListByPrincipal(YmswPerformanceRanking ymswPerformanceRanking);

    /**
     * 进件银行占比查询
     */
    AjaxResult channelList(BaseEntity baseEntity, String type);

    /**
     * 月创收增长查询
     */
    AjaxResult generationList(BaseEntity baseEntity);
}
