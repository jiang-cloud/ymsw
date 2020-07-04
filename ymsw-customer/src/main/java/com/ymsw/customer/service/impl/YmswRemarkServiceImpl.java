package com.ymsw.customer.service.impl;

import java.util.List;

import com.ymsw.common.utils.DateUtils;
import com.ymsw.framework.util.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ymsw.customer.mapper.YmswRemarkMapper;
import com.ymsw.customer.domain.YmswRemark;
import com.ymsw.customer.service.IYmswRemarkService;
import com.ymsw.common.core.text.Convert;

/**
 * 备注Service业务层处理
 * 
 * @author ymsw
 * @date 2020-05-18
 */
@Service
public class YmswRemarkServiceImpl implements IYmswRemarkService 
{
    @Autowired
    private YmswRemarkMapper ymswRemarkMapper;

    /**
     * 查询备注
     * 
     * @param remarkId 备注ID
     * @return 备注
     */
    @Override
    public YmswRemark selectYmswRemarkById(Long remarkId)
    {
        return ymswRemarkMapper.selectYmswRemarkById(remarkId);
    }

    /**
     * 查询备注列表
     * 
     * @param ymswRemark 备注
     * @return 备注
     */
    @Override
    public List<YmswRemark> selectYmswRemarkList(YmswRemark ymswRemark)
    {
        return ymswRemarkMapper.selectYmswRemarkList(ymswRemark);
    }

    /**
     * 新增备注
     * 
     * @param ymswRemark 备注
     * @return 结果
     */
    @Override
    public int insertYmswRemark(YmswRemark ymswRemark)
    {
        ymswRemark.setRemarkTime(DateUtils.getNowDate());
        ymswRemark.setIsCharge("1");//设置为主管点评  是否主管 0否 1是
        ymswRemark.setUserId(ShiroUtils.getUserId());//设置操作人
        return ymswRemarkMapper.insertYmswRemark(ymswRemark);
    }

    /**
     * 修改备注
     * 
     * @param ymswRemark 备注
     * @return 结果
     */
    @Override
    public int updateYmswRemark(YmswRemark ymswRemark)
    {
        return ymswRemarkMapper.updateYmswRemark(ymswRemark);
    }

    /**
     * 删除备注对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteYmswRemarkByIds(String ids)
    {
        return ymswRemarkMapper.deleteYmswRemarkByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除备注信息
     * 
     * @param remarkId 备注ID
     * @return 结果
     */
    @Override
    public int deleteYmswRemarkById(Long remarkId)
    {
        return ymswRemarkMapper.deleteYmswRemarkById(remarkId);
    }
}
