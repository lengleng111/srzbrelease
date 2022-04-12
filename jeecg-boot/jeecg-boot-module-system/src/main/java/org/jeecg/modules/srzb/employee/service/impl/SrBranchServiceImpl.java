package org.jeecg.modules.srzb.employee.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.srzb.employee.dao.dto.BranchDto;
import org.jeecg.modules.srzb.employee.dao.entity.SrBranchEntity;
import org.jeecg.modules.srzb.employee.dao.entity.SrEmployeeEntity;
import org.jeecg.modules.srzb.employee.mapper.SrBranchMapper;
import org.jeecg.modules.srzb.employee.service.ISrBranchService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 店铺信息表 服务实现类
 * </p>
 *
 * @author LZQ
 * @since 2022-03-16
 */
@Slf4j
@Service
public class SrBranchServiceImpl extends ServiceImpl<SrBranchMapper, SrBranchEntity> implements ISrBranchService {

    @Autowired
    private SrBranchMapper srBranchMapper;


    @Override
    public Result<?> queryAllBranch(int pageNo, int pageSize, String branchName) {
        LambdaQueryWrapper<SrBranchEntity> wrapper=new LambdaQueryWrapper<>();
        List<SrBranchEntity> list = this.list(wrapper);
        if (!branchName.equals("")&& !StringUtils.isEmpty(branchName)){
            wrapper.like(SrBranchEntity::getBranchName,branchName);
        }
        Page<SrBranchEntity> page=new Page<>(pageNo,pageSize);
        Page<SrBranchEntity> entitypage=this.page(page,wrapper);
        return Result.OK(entitypage);

    }

    @Override
    public Result<?> insertBranch(BranchDto branchDto) {
        SrBranchEntity srBranchEntity=new SrBranchEntity();
        BeanUtils.copyProperties(branchDto,srBranchEntity);
        srBranchEntity.setCreateTime(new Date());
        srBranchEntity.setCreateBy("admin");
        srBranchEntity.setUpdateTime(new Date());
        srBranchEntity.setUpdateBy("admin");
        srBranchMapper.insert(srBranchEntity);
        return Result.OK();

    }

    @Override
    public Result<?> deleteBranch(Long id) {
        srBranchMapper.deleteById(id);
        log.info("删除员工信息成功");
        return Result.OK();
    }

    @Override
    public Result<?> updateBranch(BranchDto branchDto) {
        LambdaQueryWrapper<SrBranchEntity> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(SrBranchEntity::getId,branchDto.getId());
        SrBranchEntity srBranchEntity=this.getOne(wrapper);
        BeanUtils.copyProperties(branchDto,srBranchEntity);
        srBranchMapper.updateById(srBranchEntity);
        log.info("员工信息修改成功");
        return Result.OK();
    }

    @Override
    public Result<?> selectOne(Long id) {
        LambdaQueryWrapper<SrBranchEntity> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(SrBranchEntity::getId,id);
        SrBranchEntity srBranchEntity=this.getOne(wrapper);
        log.info("查询一件商品成功");
        return Result.OK(srBranchEntity);
    }
}
