package com.ymsw.ranking.mapper;

import com.ymsw.common.core.domain.BaseEntity;
import com.ymsw.ranking.domain.YmswPerformanceRanking;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 业绩排行Mapper接口
 * 
 * @author ymsw
 * @date 2020-06-22
 */
public interface YmswPerformanceRankingMapper 
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
     * 删除业绩排行
     * 
     * @param rankingId 业绩排行ID
     * @return 结果
     */
    public int deleteYmswPerformanceRankingById(Long rankingId);

    /**
     * 批量删除业绩排行
     * 
     * @param rankingIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteYmswPerformanceRankingByIds(String[] rankingIds);

    //业绩排行里，团队排名查询
    List<YmswPerformanceRanking> selectYmswPerformanceRankingListByMinister(YmswPerformanceRanking ymswPerformanceRanking);

    //业绩排行里，区部排名查询
    List<YmswPerformanceRanking> selectYmswPerformanceRankingListByDistrict(YmswPerformanceRanking ymswPerformanceRanking);

    //业绩排行里，门店排名查询
    List<YmswPerformanceRanking> selectYmswPerformanceRankingListByPrincipal(YmswPerformanceRanking ymswPerformanceRanking);

    //通过userId和dataYearMonth查询该业务经理该月份的统计信息
    YmswPerformanceRanking selectYmswPerformanceRanking(YmswPerformanceRanking ymswPerformanceRanking);

    //统计月总进件笔数
    List<YmswPerformanceRanking> selectTotalIncomingCount(@Param("firstDay")String firstDay, @Param("lastDay") String lastDay);

    //统计月总收款笔数
    List<YmswPerformanceRanking> selectTotalCollectionCount(@Param("firstDay")String firstDay, @Param("lastDay") String lastDay);

    //统计月批款总金额
    List<YmswPerformanceRanking> selectTotalAllowAmount(@Param("firstDay")String firstDay, @Param("lastDay") String lastDay);

    //统计月总创收（参考收款时间）
    List<YmswPerformanceRanking> selectTotalGeneration(@Param("firstDay")String firstDay, @Param("lastDay") String lastDay);

    //平均费率（参考收款时间）
    List<YmswPerformanceRanking> selectOrderRate(@Param("firstDay")String firstDay, @Param("lastDay") String lastDay);

    //进件银行占比页面的批款金额查询
    List<Map<String,Double>> allowAmount(BaseEntity baseEntity);

    //进件银行占比页面的创收金额查询
    List<Map<String,Double>> incomeGeneration(BaseEntity baseEntity);

    //进件银行占比页面的批款件数查询
    List<Map<String,Double>> allowCount(BaseEntity baseEntity);

    //进件银行占比页面的收款件数查询
    List<Map<String,Double>> generationCount(BaseEntity baseEntity);

    //月创收增长页面里查询从某某天到某某天的累计创收
    Double totalGeneration(BaseEntity baseEntity);
}
