package org.jeecg.modules.srzb.employee.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.srzb.employee.dao.dto.EmployeeDto;
import org.jeecg.modules.srzb.employee.dao.entity.SrEmployeeEntity;
import org.jeecg.modules.srzb.employee.mapper.SrEmployeeMapper;
import org.jeecg.modules.srzb.employee.service.ISrEmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 员工信息表 服务实现类
 * </p>
 *
 * @author LZQ
 * @since 2022-03-14
 */
@Slf4j
@Service
public class SrEmployeeServiceImpl extends ServiceImpl<SrEmployeeMapper, SrEmployeeEntity> implements ISrEmployeeService {

    @Autowired
    SrEmployeeMapper srEmployeeMapper;
    @Override
    public Result<?> queryAllEmp(int pageNo, int pageSize, String name, String idcard, String branch) {
        LambdaQueryWrapper<SrEmployeeEntity> wrapper=new LambdaQueryWrapper<>();
        List<SrEmployeeEntity> list = this.list(wrapper);
        if (!name.equals("")&& !StringUtils.isEmpty(name)){
            wrapper.like(SrEmployeeEntity::getName,name);
        }
        if (!idcard.equals("")&& !StringUtils.isEmpty(idcard)){
            wrapper.like(SrEmployeeEntity::getIdcard,idcard);
        }
        if (!branch.equals("")&& !StringUtils.isEmpty(branch)){
            wrapper.like(SrEmployeeEntity::getBranch,branch);
        }
        Page<SrEmployeeEntity> page=new Page<>(pageNo,pageSize);
        Page<SrEmployeeEntity> entitypage=this.page(page,wrapper);
        return Result.OK(entitypage);

    }

    @Override
    public Result<?> insertEmp(EmployeeDto employeeDto) {
        SrEmployeeEntity srEmployeeEntity=new SrEmployeeEntity();
        BeanUtils.copyProperties(employeeDto,srEmployeeEntity);
        srEmployeeEntity.setCreateTime(new Date());
        srEmployeeEntity.setCreateBy("admin");
        srEmployeeEntity.setUpdateTime(new Date());
        srEmployeeEntity.setUpdateBy("admin");
        srEmployeeMapper.insert(srEmployeeEntity);
        return Result.OK();
    }

    @Override
    public Result<?> deleteEmp(Long id) {
        srEmployeeMapper.deleteById(id);
        log.info("删除员工信息成功");
        return Result.OK();
    }

    @Override
    public Result<?> updateEmp(EmployeeDto employeeDto) {
        LambdaQueryWrapper<SrEmployeeEntity> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(SrEmployeeEntity::getId,employeeDto.getId());
        SrEmployeeEntity srEmployeeEntity=this.getOne(wrapper);
        BeanUtils.copyProperties(employeeDto,srEmployeeEntity);
        srEmployeeMapper.updateById(srEmployeeEntity);
        log.info("员工信息修改成功");
        return Result.OK();
    }

    @Override
    public Result<?> selectOne(Long id) {
        LambdaQueryWrapper<SrEmployeeEntity> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(SrEmployeeEntity::getId,id);
        SrEmployeeEntity srEmployeeEntity=this.getOne(wrapper);
        log.info("查询员工信息成功");
        return Result.OK(srEmployeeEntity);
    }
}
