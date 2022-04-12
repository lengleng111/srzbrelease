package org.jeecg.modules.srzb.shopgoods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.vo.Result;

import org.jeecg.modules.srzb.employee.dao.entity.SrEmployeeEntity;
import org.jeecg.modules.srzb.shopgoods.dao.dto.ShopGoodsDetailDto;
import org.jeecg.modules.srzb.shopgoods.dao.dto.ShopGoodsDto;
import org.jeecg.modules.srzb.shopgoods.dao.entity.SrShopGoodsEntity;
import org.jeecg.modules.srzb.shopgoods.dao.entity.SrShopGoosImageEntity;
import org.jeecg.modules.srzb.shopgoods.mapper.SrShopGoodsImageMapper;
import org.jeecg.modules.srzb.shopgoods.mapper.SrShopGoodsMapper;
import org.jeecg.modules.srzb.shopgoods.service.ISrShopGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.srzb.utils.PictureUtils;
import org.jeecg.modules.srzb.yuyue.dao.entity.SrAppointmentStoreEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 商品信息表 服务实现类
 * </p>
 *
 * @author LZQ
 * @since 2022-03-06
 */
@Service
@Slf4j
public class SrShopGoodsServiceImpl extends ServiceImpl<SrShopGoodsMapper, SrShopGoodsEntity> implements ISrShopGoodsService {
    @Autowired
    SrShopGoodsMapper srShopGoodsMapper;
    @Autowired
    private SrShopGoodsImageMapper srShopGoodsImageMapper;

    @Override
    public Result<?> queryGoods(int pageNo, int pageSize, String goodsName, String typeId, String saleType) {
        LambdaQueryWrapper<SrShopGoodsEntity> wrapper=new LambdaQueryWrapper<>();
        List<SrShopGoodsEntity> list = this.list(wrapper);
        if (!goodsName.equals("")&& !StringUtils.isEmpty(goodsName)){
            wrapper.like(SrShopGoodsEntity::getName,goodsName);
        }
        if (!saleType.equals("")&& !StringUtils.isEmpty(saleType)){
            wrapper.eq(SrShopGoodsEntity::getSaleType,saleType);
        }
        if (!typeId.equals("")&& !StringUtils.isEmpty(typeId)){
            wrapper.eq(SrShopGoodsEntity::getTypeId,Integer.parseInt(typeId));
        }
        Page<SrShopGoodsEntity> page=new Page<>(pageNo,pageSize);
        Page<SrShopGoodsEntity> entitypage=this.page(page,wrapper);

        return Result.OK(entitypage);
    }

    @Override
    public Result<?> deleteGoods(Long id) {
        srShopGoodsMapper.deleteById(id);
        log.info("删除商品成功");
        return Result.OK();
    }

    @Override
    public Result<?> insertGoods(ShopGoodsDto shopGoodsDto) {
        SrShopGoodsEntity srShopGoodsEntity=new SrShopGoodsEntity();
        BeanUtils.copyProperties(shopGoodsDto,srShopGoodsEntity);
        srShopGoodsEntity.setCreateBy("fdsf");
        srShopGoodsEntity.setUpdateBy("fds");
        srShopGoodsEntity.setCreateTime(new Date());
        srShopGoodsEntity.setUpdateTime(new Date());
        srShopGoodsMapper.insert(srShopGoodsEntity);
        log.info("保存商品信息成功");
        return Result.OK(srShopGoodsEntity.getId());
    }

    @Override
    public Result<?> updateGoods(ShopGoodsDto shopGoodsDto) {
        LambdaQueryWrapper<SrShopGoodsEntity> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(SrShopGoodsEntity::getId,shopGoodsDto.getId());
        SrShopGoodsEntity srShopGoodsEntity=this.getOne(wrapper);
        BeanUtils.copyProperties(shopGoodsDto,srShopGoodsEntity);
        srShopGoodsMapper.updateById(srShopGoodsEntity);
        log.info("修改商品成功");
        return Result.OK();
    }

    @Override
    public Result<?> selectOne(Long id) {
        LambdaQueryWrapper<SrShopGoodsEntity> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(SrShopGoodsEntity::getId,id);
        SrShopGoodsEntity srShopGoodsEntity=this.getOne(wrapper);
        log.info("查询一件商品成功");
        return Result.OK(srShopGoodsEntity);
    }

    @Override
    public Result<?> selectByType(Long typeId,int pageNo,int pageSize) {
        try {
            LambdaQueryWrapper<SrShopGoodsEntity> wrapper=new LambdaQueryWrapper<>();
            wrapper.eq(SrShopGoodsEntity::getTypeId,typeId);
            Page<SrShopGoodsEntity> page=new Page<>(pageNo,pageSize);
            Page<SrShopGoodsEntity> entitypage=this.page(page,wrapper);
            List<SrShopGoodsEntity> records = entitypage.getRecords();
            List<ShopGoodsDetailDto> dtos=new ArrayList<>();
            for (SrShopGoodsEntity srShopGoodsEntity:records){
                ShopGoodsDetailDto dto=new ShopGoodsDetailDto();
                BeanUtils.copyProperties(srShopGoodsEntity,dto);
                LambdaQueryWrapper<SrShopGoosImageEntity> wrapper2=new LambdaQueryWrapper<>();
                wrapper2.eq(SrShopGoosImageEntity::getGoodsId,srShopGoodsEntity.getId());
                wrapper2.eq(SrShopGoosImageEntity::getType,"1");
                List<SrShopGoosImageEntity> srShopGoosImageEntities = srShopGoodsImageMapper.selectList(wrapper2);
                String path = srShopGoosImageEntities.get(0).getPath();
                dto.setPath(PictureUtils.convertPictureAddress(path));
                dtos.add(dto);
            }
            return Result.OK(dtos);
        }catch (Exception e){
            log.info("根据类型查询商品失败"+e.getMessage());
            return Result.error("根据类型查询商品失败");
        }
    }

    @Override
    public Result<?> selectByDisplayType(String dispalyType) {
        try {
            LambdaQueryWrapper<SrShopGoodsEntity> wrapper=new LambdaQueryWrapper<>();
            wrapper.eq(SrShopGoodsEntity::getDisplayType,dispalyType);
            wrapper.orderByDesc(SrShopGoodsEntity::getCreateTime);
            Page<SrShopGoodsEntity> page=new Page<>(1,4);
            Page<SrShopGoodsEntity> entitypage=this.page(page,wrapper);
            List<SrShopGoodsEntity> records = entitypage.getRecords();
            List<ShopGoodsDetailDto> dtos=new ArrayList<>();
            for (SrShopGoodsEntity srShopGoodsEntity:records){
                ShopGoodsDetailDto dto=new ShopGoodsDetailDto();
                BeanUtils.copyProperties(srShopGoodsEntity,dto);
                LambdaQueryWrapper<SrShopGoosImageEntity> wrapper2=new LambdaQueryWrapper<>();
                wrapper2.eq(SrShopGoosImageEntity::getGoodsId,srShopGoodsEntity.getId());
                wrapper2.eq(SrShopGoosImageEntity::getType,"1");
                List<SrShopGoosImageEntity> srShopGoosImageEntities = srShopGoodsImageMapper.selectList(wrapper2);
                String path = srShopGoosImageEntities.get(0).getPath();
                dto.setPath(PictureUtils.convertPictureAddress(path));
                dtos.add(dto);
            }
            return Result.OK(dtos);
        }catch (Exception e){
            log.info("根据展示类型查询商品失败"+e.getMessage());
            return Result.error("根据展示类型查询商品失败");
        }
    }

    @Override
    public Result<?> selectKeyword(String keyword,int pageNo,int pageSize) {
        try {
            LambdaQueryWrapper<SrShopGoodsEntity> wrapper=new LambdaQueryWrapper<>();
            wrapper.like(SrShopGoodsEntity::getName,keyword).or().like(SrShopGoodsEntity::getContents,keyword).or()
                    .like(SrShopGoodsEntity::getMaterial,keyword).or().like(SrShopGoodsEntity::getTypeName,keyword);
            wrapper.orderByDesc(SrShopGoodsEntity::getCreateTime);
            Page<SrShopGoodsEntity> page=new Page<>(pageNo,pageSize);
            Page<SrShopGoodsEntity> entitypage=this.page(page,wrapper);
            List<SrShopGoodsEntity> records = entitypage.getRecords();
            List<ShopGoodsDetailDto> dtos=new ArrayList<>();
            for (SrShopGoodsEntity srShopGoodsEntity:records){
                ShopGoodsDetailDto dto=new ShopGoodsDetailDto();
                BeanUtils.copyProperties(srShopGoodsEntity,dto);
                LambdaQueryWrapper<SrShopGoosImageEntity> wrapper2=new LambdaQueryWrapper<>();
                wrapper2.eq(SrShopGoosImageEntity::getGoodsId,srShopGoodsEntity.getId());
                wrapper2.eq(SrShopGoosImageEntity::getType,"1");
                List<SrShopGoosImageEntity> srShopGoosImageEntities = srShopGoodsImageMapper.selectList(wrapper2);
                String path = srShopGoosImageEntities.get(0).getPath();
                dto.setPath(PictureUtils.convertPictureAddress(path));
                dtos.add(dto);
            }
            return Result.OK(dtos);
        }catch (Exception e){
            log.info("首页根据关键字查询商品失败"+e.getMessage());
            return Result.error("首页根据关键字查询商品失败");
        }
    }
}
