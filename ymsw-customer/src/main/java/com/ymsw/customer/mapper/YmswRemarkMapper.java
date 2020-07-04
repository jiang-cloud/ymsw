package com.ymsw.customer.mapper;

import com.ymsw.customer.domain.YmswRemark;
import java.util.List;

/**
 * 备注Mapper接口
 * 
 * @author ymsw
 * @date 2020-05-18
 */
public interface YmswRemarkMapper 
{
    /**
     * 查询备注
     * 
     * @param remarkId 备注ID
     * @return 备注
     */
    public YmswRemark selectYmswRemarkById(Long remarkId);

    /**
     * 查询备注列表
     * 
     * @param ymswRemark 备注
     * @return 备注集合
     */
    public List<YmswRemark> selectYmswRemarkList(YmswRemark ymswRemark);

    /**
     * 新增备注
     * 
     * @param ymswRemark 备注
     * @return 结果
     */
    public int insertYmswRemark(YmswRemark ymswRemark);

    /**
     * 修改备注
     * 
     * @param ymswRemark 备注
     * @return 结果
     */
    public int updateYmswRemark(YmswRemark ymswRemark);

    /**
     * 删除备注
     * 
     * @param remarkId 备注ID
     * @return 结果
     */
    public int deleteYmswRemarkById(Long remarkId);

    /**
     * 批量删除备注
     * 
     * @param remarkIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteYmswRemarkByIds(String[] remarkIds);
}
