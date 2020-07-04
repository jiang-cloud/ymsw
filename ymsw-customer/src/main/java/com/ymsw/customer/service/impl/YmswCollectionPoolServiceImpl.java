package com.ymsw.customer.service.impl;

import com.ymsw.common.annotation.DataScope;
import com.ymsw.common.core.domain.AjaxResult;
import com.ymsw.common.core.text.Convert;
import com.ymsw.common.utils.DateUtils;
import com.ymsw.common.utils.StringUtils;
import com.ymsw.customer.domain.YmswCollectionPool;
import com.ymsw.customer.domain.YmswCustomer;
import com.ymsw.customer.mapper.YmswCollectionPoolMapper;
import com.ymsw.customer.mapper.YmswCustomerMapper;
import com.ymsw.customer.service.IYmswCollectionPoolService;
import com.ymsw.customer.vo.YmswReallocationVo;
import com.ymsw.framework.util.ShiroUtils;
import com.ymsw.quota.domain.QuotaManager;
import com.ymsw.quota.mapper.QuotaManagerMapper;
import com.ymsw.system.domain.SysDictData;
import com.ymsw.system.mapper.SysDictDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 收藏夹-公共池Service业务层处理
 *
 * @author ymsw
 * @date 2020-05-18
 */
@Service
public class YmswCollectionPoolServiceImpl implements IYmswCollectionPoolService {
    @Autowired
    private YmswCollectionPoolMapper ymswCollectionPoolMapper;
    @Autowired
    private YmswCustomerMapper ymswCustomerMapper;
    @Autowired
    private SysDictDataMapper sysDictDataMapper;
    @Autowired
    private QuotaManagerMapper quotaManagerMapper;

    /**
     * 查询收藏夹-公共池
     *
     * @param cpId 收藏夹-公共池ID
     * @return 收藏夹-公共池
     */
    @Override
    public YmswCollectionPool selectYmswCollectionPoolById(Long cpId) {
        return ymswCollectionPoolMapper.selectYmswCollectionPoolById(cpId);
    }

    /**
     * 查询收藏夹或公共池列表(根据数据范围查询)
     *
     * @param ymswCustomer 查询条件
     * @return 收藏夹集合
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<YmswCustomer> selectYmswCollectionPoolList(YmswCustomer ymswCustomer) {
        return ymswCollectionPoolMapper.selectYmswCollectionPoolList(ymswCustomer);
    }

    /**
     * 新增收藏夹-公共池
     *
     * @param ymswCollectionPool 收藏夹-公共池
     * @return 结果
     */
    @Override
    public int insertYmswCollectionPool(YmswCollectionPool ymswCollectionPool) {
        return ymswCollectionPoolMapper.insertYmswCollectionPool(ymswCollectionPool);
    }

    /**
     * 修改收藏夹-公共池
     *
     * @param ymswCollectionPool 收藏夹-公共池
     * @return 结果
     */
    @Override
    public int updateYmswCollectionPool(YmswCollectionPool ymswCollectionPool) {
        return ymswCollectionPoolMapper.updateYmswCollectionPool(ymswCollectionPool);
    }

    /**
     * 删除收藏夹-公共池对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteYmswCollectionPoolByIds(String ids) {
        return ymswCollectionPoolMapper.deleteYmswCollectionPoolByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除收藏夹-公共池信息
     *
     * @param cpId 收藏夹-公共池ID
     * @return 结果
     */
    @Override
    public int deleteYmswCollectionPoolById(Long cpId) {
        return ymswCollectionPoolMapper.deleteYmswCollectionPoolById(cpId);
    }

    /**
     * 加入公共池，放入收藏夹
     * @return 结果
     */
    @Override
    @Transactional
    public AjaxResult addToCollectionPool(YmswReallocationVo reallocationVo) {
        String type = reallocationVo.getType();//类型  1 加入收藏夹  2 加入公共池
        List<YmswCustomer> ymswCustomers = reallocationVo.getYmswCustomers();//将被加入的客户集合
        Long userId = ShiroUtils.getUserId();//当前userId
        List<YmswCustomer> addCustomerList = new ArrayList<>();    //实际将被加入公共池收藏夹表的客户集合
        List<String> ids = new ArrayList<>();   //实际将被加入公共池收藏夹表的客户ids
        Map<Long,Integer> maps = new HashMap<>();   //存放原业务经理和其被加入公共池的客户条数  key是userId，value是被公共池的客户条数
        StringBuffer msg = new StringBuffer();  //返回的消息
        int errCollectCount = 0;
        int errPoolCount = 0;
        //过滤客户，不在公共池也不再收藏夹的客户放入addCustomerList 和 ids里
        for (YmswCustomer ymswCustomer : ymswCustomers) {
            Long customerId = ymswCustomer.getCustomerId();
            if (isInCollectionPool(Long.valueOf(customerId),"1")){//是否在收藏夹，如果在，就errCollectCount++
                errCollectCount ++;
            }else if (isInCollectionPool(Long.valueOf(customerId),"2")){//是否在公共池，如果在就errPoolCount++
                errPoolCount ++;
            }else {
                addCustomerList.add(ymswCustomer); //如果既不在收藏夹，也不在公共池，就add到addCustomerList，用于加入公共池时计算当前客户数
                ids.add(customerId.toString()); //如果既不在收藏夹，也不在公共池，就add到ids，用于添加到公共池收藏夹表
            }
        }
        //如果ids不为空，就进行加入收藏夹或公共池
        if (StringUtils.isNotEmpty(ids)){
            if ("1".equals(type)) {  // 1是批量添加到收藏夹
                SysDictData sysDictData = new SysDictData();
                sysDictData.setDictType("ymsw_config");
                sysDictData.setDictLabel("collection_count");
                List<SysDictData> sysDictDataList = sysDictDataMapper.selectDictDataList(sysDictData);//查询设置的允许收藏的条数
                if (StringUtils.isNotEmpty(sysDictDataList)) {
                    String collectionCount = sysDictDataList.get(0).getDictValue(); //设置的允许收藏的条数
                    int count = ymswCollectionPoolMapper.selectCountByUserId(userId);//查询该用户已经收藏的条数
                    if (Integer.valueOf(collectionCount) <= count) { //如果已经收藏的条数大于等于允许收藏的条数，就不能收藏。
                        return AjaxResult.error("收藏的客户数量不能超过" + collectionCount + "条！");
                    } else if (count + ids.size() > Integer.valueOf(collectionCount)) { //如果已经收藏的条数+将要收藏的条数大于允许收藏的条数，就不能收藏。
                        return AjaxResult.error("还可收藏" + (Integer.valueOf(collectionCount) - count) + "条！");
                    }
                }
            } else if ("2".equals(type)) {    // 2是批量添加到公共池
                ymswCustomerMapper.updateUseridToNull(ids);//批量修改客户的归属顾问为空
                //处理maps
                for (YmswCustomer ymswCustomer : addCustomerList) {
                    Long oldUserId = ymswCustomer.getUserId();
                    if (!maps.containsKey(oldUserId)){
                        maps.put(oldUserId,0);  //如果maps里未存放该业务经理id，就put进去，并设置被分配条数为0
                    }
                    Integer integer = maps.get(oldUserId);
                    integer++;
                    maps.put(oldUserId,integer);
                }
                //加入公共池后修改配额表里对应业务经理的当前客户数
                for(Map.Entry<Long, Integer> entry : maps.entrySet()){
                    Long oldUserId = entry.getKey();
                    Integer mapValue = entry.getValue();
                    QuotaManager quotaManager = quotaManagerMapper.selectQuotaManagerByUserId(oldUserId); //通过userId查询改业务经理的配额信息
                    if (StringUtils.isNotNull(quotaManager)) {
                        Integer nowTotalCount = quotaManager.getNowTotalCount();    //当前客户数
                        quotaManager.setNowTotalCount(nowTotalCount - mapValue);
                        quotaManagerMapper.updateQuotaManager(quotaManager);//修改该客户对应当前客户数
                    }
                }
            }
            ymswCollectionPoolMapper.batchInsertYmswCollectionPool(getAddList(addCustomerList,type,userId));    //批量添加到收藏夹公共池表
        }
        if (errCollectCount > 0){
            msg.append(errCollectCount+"条已在收藏夹，");
        }
        if (errPoolCount > 0){
            msg.append(errPoolCount+"条已在公共池，");
        }
        if (StringUtils.isNotEmpty(msg.toString())){
            if ("1".equals(type)){
                msg.append("不可再次加入收藏夹！<br>");
            }else if ("2".equals(type)){
                msg.append("不可再次放入公共池！<br>");
            }
        }
        msg.append(ids.size()+"条操作成功！");
        return AjaxResult.success(msg.toString());
    }

    //    返回需要批量添加到收藏夹公共池的数据集合
    public static ArrayList<YmswCollectionPool> getAddList(List<YmswCustomer> addCustomerList, String type, Long userId) {
        ArrayList<YmswCollectionPool> list = new ArrayList<>();
        Date addTime = DateUtils.getNowDate();//当前时间
        for (YmswCustomer customer : addCustomerList) {
            YmswCollectionPool ymswCollectionPool = new YmswCollectionPool();
            ymswCollectionPool.setCustomerId(Long.valueOf(customer.getCustomerId()));//设置客户id
            ymswCollectionPool.setAddTime(addTime);//设置添加时间
            ymswCollectionPool.setCpType(type);//设置类型为收藏夹
            ymswCollectionPool.setOperUserId(userId);//设置操作人
            if ("1".equals(type)) {
                ymswCollectionPool.setUserId(userId);//如果添加到收藏夹就设置收藏人
            }else if ("2".equals(type)){
                ymswCollectionPool.setUserId(customer.getUserId());
            }
            list.add(ymswCollectionPool);
        }
        return list;
    }

    //  通过customerId和类型，查询是否已经在收藏夹公共池表
    private boolean isInCollectionPool(Long customerId,String cpType){
        int i = ymswCollectionPoolMapper.selectIsInCollectionPool(customerId, cpType);
        return i > 0 ? true : false;
    }

    //抽回重分配时，批量从公共池里删除数据
    @Override
    public int batchDeleteByCustomerIds(List<Long> customerIds) {
        return ymswCollectionPoolMapper.batchDeleteByCustomerIds(customerIds);
    }
}
