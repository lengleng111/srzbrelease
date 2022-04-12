package org.jeecg.modules.srzb.yuyue.service;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.srzb.yuyue.dao.dto.SrYuyueDto;
import org.jeecg.modules.srzb.yuyue.dao.entity.SrAppointmentStoreEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 预约到店信息表 服务类
 * </p>
 *
 * @author LZQ
 * @since 2022-04-09
 */
public interface ISrAppointmentStoreService extends IService<SrAppointmentStoreEntity> {
        Result<?> customerAppointmentStore(SrYuyueDto srYuyueDto);
        Result<?> queryAppointmentRecord(Long userId,int pageNo,int pageSize);
        Result<?> employeeQuery(Long userId,String userName,int pageNo,int pageSize);
        Result<?> pcQuery(String storeName,String userName,int pageNo,int pageSize);
        Result<?> pcDelete(Long id);
        Result<?> modifyReceive(Long id,Long receivestaffId,String receivestaffName);
        Result<?> selectone(Long id);
}
