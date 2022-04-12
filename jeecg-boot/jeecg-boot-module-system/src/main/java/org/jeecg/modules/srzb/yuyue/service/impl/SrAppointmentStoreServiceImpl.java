package org.jeecg.modules.srzb.yuyue.service.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.srzb.employee.dao.entity.SrEmployeeEntity;
import org.jeecg.modules.srzb.employee.mapper.SrEmployeeMapper;
import org.jeecg.modules.srzb.shopgoods.dao.entity.SrShopGoodsEntity;
import org.jeecg.modules.srzb.shopgoods.dao.entity.SrShopGoodsTypeEntity;
import org.jeecg.modules.srzb.yuyue.dao.dto.SrYuyueDto;
import org.jeecg.modules.srzb.yuyue.dao.entity.SrAppointmentStoreEntity;
import org.jeecg.modules.srzb.yuyue.mapper.SrAppointmentStoreMapper;
import org.jeecg.modules.srzb.yuyue.service.ISrAppointmentStoreService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 预约到店信息表 服务实现类
 * </p>
 *
 * @author LZQ
 * @since 2022-04-09
 */
@Slf4j
@Service
public class SrAppointmentStoreServiceImpl extends ServiceImpl<SrAppointmentStoreMapper, SrAppointmentStoreEntity> implements ISrAppointmentStoreService {

    @Autowired
    private SrAppointmentStoreMapper srAppointmentStoreMapper;
    @Autowired
    private SrEmployeeMapper srEmployeeMapper;
    @Override
    public Result<?> customerAppointmentStore(SrYuyueDto srYuyueDto) {
        try {
            LambdaQueryWrapper<SrAppointmentStoreEntity> wrapper=new LambdaQueryWrapper<>();
            wrapper.eq(SrAppointmentStoreEntity::getUserId,srYuyueDto.getUserId());
            wrapper.eq(SrAppointmentStoreEntity::getIsEffective,"1");
            wrapper.eq(SrAppointmentStoreEntity::getIsReceiveover,"2");
            SrAppointmentStoreEntity appointmentStoreEntity = this.getOne(wrapper);
            if (appointmentStoreEntity == null) {
                SrAppointmentStoreEntity srAppointmentStoreEntity = new SrAppointmentStoreEntity();
                BeanUtils.copyProperties(srYuyueDto,srAppointmentStoreEntity);
                srAppointmentStoreEntity.setCreateBy(srYuyueDto.getUserName());
                srAppointmentStoreEntity.setCreateTime(new Date());
                srAppointmentStoreEntity.setUpdateBy(srYuyueDto.getUserName());
                srAppointmentStoreEntity.setUpdateTime(new Date());
                srAppointmentStoreEntity.setIsEffective("1");
                srAppointmentStoreEntity.setIsReceiveover("2");
                srAppointmentStoreMapper.insert(srAppointmentStoreEntity);
                log.info("新增预约记录成功");
                return Result.OK("新增预约记录成功");
            }else {
                return Result.error("您目前已有预约成功的记录，请勿重复预约");
            }
        }catch (Exception e){
            log.info("顾客预约到店新增接口失败"+e.getMessage());
            return Result.error("顾客预约到店新增接口失败");
        }
    }

    @Override
    public Result<?> queryAppointmentRecord(Long userId,int pageNo,int pageSize) {
        try {
            log.info("开始查询顾客预约记录");
            LambdaQueryWrapper<SrAppointmentStoreEntity> wrapper=new LambdaQueryWrapper<>();
            wrapper.eq(SrAppointmentStoreEntity::getUserId,userId);
            wrapper.orderByDesc(SrAppointmentStoreEntity::getAppointmentTime);
            Page<SrAppointmentStoreEntity> page=new Page<>(pageNo,pageSize);
            Page<SrAppointmentStoreEntity> entitypage=this.page(page,wrapper);
            List<SrAppointmentStoreEntity> records = entitypage.getRecords();
            for (SrAppointmentStoreEntity srAppointmentStoreEntity:records){
                if (srAppointmentStoreEntity.getIsEffective().equals("1")) {
                    Date time = srAppointmentStoreEntity.getAppointmentTime();
                    Date now = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date parse = sdf.parse(sdf.format(now));
                    if (parse.getTime() - time.getTime() > 0) {
                        if (srAppointmentStoreEntity.getIsReceiveover().equals("2")) {
                            srAppointmentStoreEntity.setIsEffective("2");
                            srAppointmentStoreMapper.updateById(srAppointmentStoreEntity);
                        }
                    }
                }
            }
            entitypage=entitypage.setRecords(records);
            return Result.OK(entitypage);
        }catch (Exception e){
            log.info("查询该客户预约到店记录失败"+e.getMessage());
            return Result.error("查询该客户预约到店记录失败");
        }
    }

    @Override
    public Result<?> employeeQuery(Long userId,String userName,int pageNo, int pageSize) {
        try {
            LambdaQueryWrapper<SrEmployeeEntity> wrapper=new LambdaQueryWrapper<>();
            wrapper.eq(SrEmployeeEntity::getId,userId);
            SrEmployeeEntity srEmployeeEntity = srEmployeeMapper.selectOne(wrapper);
            String level = srEmployeeEntity.getLevel();
            if (level.equals("3")){
                return Result.error("您没有查看权限");
            }else if(level.equals("2")){
                Long branch_id = srEmployeeEntity.getBranchId();
                LambdaQueryWrapper<SrAppointmentStoreEntity> wrapper2=new LambdaQueryWrapper<>();
                wrapper2.eq(SrAppointmentStoreEntity::getStoreId,branch_id);
                if (!userName.equals("")&& !StringUtils.isEmpty(userName)){
                    wrapper2.like(SrAppointmentStoreEntity::getUserName,userName);
                }
                wrapper2.orderByDesc(SrAppointmentStoreEntity::getAppointmentTime);
                Page<SrAppointmentStoreEntity> page=new Page<>(pageNo,pageSize);
                Page<SrAppointmentStoreEntity> entitypage=this.page(page,wrapper2);
                List<SrAppointmentStoreEntity> records = entitypage.getRecords();
                for (SrAppointmentStoreEntity srAppointmentStoreEntity:records){
                    if (srAppointmentStoreEntity.getIsEffective().equals("1")) {
                        Date time = srAppointmentStoreEntity.getAppointmentTime();
                        Date now = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date parse = sdf.parse(sdf.format(now));
                        if (parse.getTime() - time.getTime() > 0) {
                            if (srAppointmentStoreEntity.getIsReceiveover().equals("2")) {
                                srAppointmentStoreEntity.setIsEffective("2");
                                srAppointmentStoreMapper.updateById(srAppointmentStoreEntity);
                            }
                        }
                    }
                }
                entitypage=entitypage.setRecords(records);
                return Result.OK(entitypage);
            }else{
                LambdaQueryWrapper<SrAppointmentStoreEntity> wrapper2=new LambdaQueryWrapper<>();
                if (!userName.equals("")&& !StringUtils.isEmpty(userName)){
                    wrapper2.like(SrAppointmentStoreEntity::getUserName,userName);
                }
                wrapper2.orderByDesc(SrAppointmentStoreEntity::getAppointmentTime);
                Page<SrAppointmentStoreEntity> page=new Page<>(pageNo,pageSize);
                Page<SrAppointmentStoreEntity> entitypage=this.page(page,wrapper2);
                List<SrAppointmentStoreEntity> records = entitypage.getRecords();
                for (SrAppointmentStoreEntity srAppointmentStoreEntity:records){
                    if (srAppointmentStoreEntity.getIsEffective().equals("1")) {
                        Date time = srAppointmentStoreEntity.getAppointmentTime();
                        Date now = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date parse = sdf.parse(sdf.format(now));
                        if (parse.getTime() - time.getTime() > 0) {
                            if (srAppointmentStoreEntity.getIsReceiveover().equals("2")) {
                                srAppointmentStoreEntity.setIsEffective("2");
                                srAppointmentStoreMapper.updateById(srAppointmentStoreEntity);
                            }
                        }
                    }
                }
                entitypage=entitypage.setRecords(records);
                return Result.OK(entitypage);
            }
        }catch (Exception e){
            log.info("员工查询客户预约到店记录失败"+e.getMessage());
            return Result.error("员工查询客户预约到店记录失败");
        }
    }

    @Override
    public Result<?> pcQuery(String storeName, String userName, int pageNo, int pageSize) {
        try {
            LambdaQueryWrapper<SrAppointmentStoreEntity> wrapper=new LambdaQueryWrapper<>();
            if (!userName.equals("")&& !StringUtils.isEmpty(userName)){
                wrapper.like(SrAppointmentStoreEntity::getUserName,userName);
            }

            if (!storeName.equals("")&& !StringUtils.isEmpty(storeName)){
                wrapper.like(SrAppointmentStoreEntity::getStoreName,storeName);
            }
            Page<SrAppointmentStoreEntity> page=new Page<>(pageNo,pageSize);
            Page<SrAppointmentStoreEntity> entitypage=this.page(page,wrapper);
            List<SrAppointmentStoreEntity> records = entitypage.getRecords();
            for (SrAppointmentStoreEntity srAppointmentStoreEntity:records){
                if (srAppointmentStoreEntity.getIsEffective().equals("1")) {
                    Date time = srAppointmentStoreEntity.getAppointmentTime();
                    Date now = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date parse = sdf.parse(sdf.format(now));
                    if (parse.getTime() - time.getTime() > 0) {
                        if (srAppointmentStoreEntity.getIsReceiveover().equals("2")) {
                            srAppointmentStoreEntity.setIsEffective("2");
                            srAppointmentStoreMapper.updateById(srAppointmentStoreEntity);
                        }
                    }
                }
            }
            entitypage=entitypage.setRecords(records);
            return Result.OK(entitypage);
        }catch (Exception e){
            log.info("管理端查询客户预约到店记录失败"+e.getMessage());
            return Result.error("管理端查询客户预约到店记录失败");
        }

    }

    @Override
    public Result<?> pcDelete(Long id) {
        try {
            srAppointmentStoreMapper.deleteById(id);
            log.info("删除预约记录成功");
            return Result.OK();
        }catch (Exception e){
            log.info("管理端删除客户预约到店记录失败"+e.getMessage());
            return Result.error("管理端删除客户预约到店记录失败");
        }
    }

    @Override
    public Result<?> modifyReceive(Long id, Long receivestaffId, String receivestaffName) {
        try {
            LambdaQueryWrapper<SrAppointmentStoreEntity> wrapper=new LambdaQueryWrapper<>();
            wrapper.eq(SrAppointmentStoreEntity::getId,id);
            SrAppointmentStoreEntity srAppointmentStoreEntity = this.getOne(wrapper);
            srAppointmentStoreEntity.setReceivestaffId(receivestaffId);
            srAppointmentStoreEntity.setReceivestaffName(receivestaffName);
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            srAppointmentStoreEntity.setReceivestaffTime(sdf.parse(sdf.format(new Date())));
            srAppointmentStoreEntity.setIsReceiveover("1");
            srAppointmentStoreMapper.updateById(srAppointmentStoreEntity);
            log.info("修改接待状态完成");
            return Result.OK();
        }catch (Exception e){
            log.info("客户接待完成修改状态失败"+e.getMessage());
            return Result.error("客户接待完成修改状态失败");
        }
    }

    @Override
    public Result<?> selectone(Long id) {
        try {
            LambdaQueryWrapper<SrAppointmentStoreEntity> wrapper=new LambdaQueryWrapper<>();
            wrapper.eq(SrAppointmentStoreEntity::getId,id);
            SrAppointmentStoreEntity srAppointmentStoreEntity = this.getOne(wrapper);
            return Result.OK(srAppointmentStoreEntity);
        }catch (Exception e){
            log.info("查询一条预约到店信息失败"+e.getMessage());
            return Result.error("查询一条预约到店信息失败");
        }
    }
}
