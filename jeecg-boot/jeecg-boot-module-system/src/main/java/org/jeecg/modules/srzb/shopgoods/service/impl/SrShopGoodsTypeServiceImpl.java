package org.jeecg.modules.srzb.shopgoods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.srzb.employee.dao.entity.SrEmployeeEntity;
import org.jeecg.modules.srzb.shopgoods.dao.dto.ShopTypeDto;
import org.jeecg.modules.srzb.shopgoods.dao.entity.SrShopGoodsEntity;
import org.jeecg.modules.srzb.shopgoods.dao.entity.SrShopGoodsTypeEntity;
import org.jeecg.modules.srzb.shopgoods.dao.entity.SrShopGoosImageEntity;
import org.jeecg.modules.srzb.shopgoods.mapper.SrShopGoodsTypeMapper;
import org.jeecg.modules.srzb.shopgoods.service.ISrShopGoodsTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.srzb.utils.PictureUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LZQ
 * @since 2022-03-15
 */
@Service
@Slf4j
public class SrShopGoodsTypeServiceImpl extends ServiceImpl<SrShopGoodsTypeMapper, SrShopGoodsTypeEntity> implements ISrShopGoodsTypeService {
    @Autowired
    private SrShopGoodsTypeMapper srShopGoodsTypeMapper;
    @Override
    public Result<?> queryAllType(int pageNo,int pageSize,String name) {
        LambdaQueryWrapper<SrShopGoodsTypeEntity> wrapper=new LambdaQueryWrapper<>();
        if (!name.equals("")&& !StringUtils.isEmpty(name)){
            wrapper.like(SrShopGoodsTypeEntity::getName,name);
        }
        Page<SrShopGoodsTypeEntity> page=new Page<>(pageNo,pageSize);
        Page<SrShopGoodsTypeEntity> entitypage=this.page(page,wrapper);
        List<SrShopGoodsTypeEntity> records = entitypage.getRecords();
        for (SrShopGoodsTypeEntity srShopGoodsTypeEntity:records){
            srShopGoodsTypeEntity.setImgUrl(PictureUtils.convertPictureAddress(srShopGoodsTypeEntity.getImgUrl()));
        }
        entitypage=entitypage.setRecords(records);
        return Result.OK(entitypage);
    }

    @Override
    public Result<?> insertType(MultipartHttpServletRequest multipartRequest, SrShopGoodsTypeEntity srShopGoodsTypeEntity) {
        try {
            String pictureAddress = PictureUtils.getPictureAddress(multipartRequest);
            srShopGoodsTypeEntity.setImgUrl(pictureAddress);
            srShopGoodsTypeEntity.setCreateTime(new Date());
            srShopGoodsTypeEntity.setUpdateTime(new Date());
            srShopGoodsTypeEntity.setCreateBy("gd");
            srShopGoodsTypeEntity.setUpdateBy("fgd");
            srShopGoodsTypeMapper.insert(srShopGoodsTypeEntity);
            log.info("保存商品分类信息成功");
            return Result.OK("保存商品分类信息成功");
        }catch (Exception e){
            log.info("insertType：",e.getMessage());
            e.printStackTrace();
            return Result.error("系统错误"+e.getMessage());
        }

    }

    @Override
    public Result<?> deleteType(Long id) {
        srShopGoodsTypeMapper.deleteById(id);
        log.info("删除商品分类成功");
        return Result.OK();
    }

    @Override
    public Result<?> updateType(MultipartHttpServletRequest multipartRequest, String typeId) {
        try {
            LambdaQueryWrapper<SrShopGoodsTypeEntity> wrapper=new LambdaQueryWrapper<>();
            wrapper.eq(SrShopGoodsTypeEntity::getId,Long.parseLong(typeId));
            SrShopGoodsTypeEntity srShopGoodsTypeEntity=this.getOne(wrapper);
            srShopGoodsTypeEntity.setName(multipartRequest.getParameter("name"));
            srShopGoodsTypeEntity.setImgUrl(PictureUtils.getPictureAddress(multipartRequest));
            srShopGoodsTypeEntity.setUpdateTime(new Date());
            srShopGoodsTypeMapper.updateById(srShopGoodsTypeEntity);
            log.info("修改商品分类成功");
            return Result.OK();
        }catch (Exception e){
            log.info("updateType：",e.getMessage());
            e.printStackTrace();
            return Result.error("系统错误"+e.getMessage());
        }

    }

    @Override
    public Result<?> seletcOne(Long id) {
        LambdaQueryWrapper<SrShopGoodsTypeEntity> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(SrShopGoodsTypeEntity::getId,id);
        SrShopGoodsTypeEntity srShopGoodsTypeEntity=this.getOne(wrapper);
        srShopGoodsTypeEntity.setImgUrl(PictureUtils.convertPictureAddress(srShopGoodsTypeEntity.getImgUrl()));
        log.info("根据id查询商品分类");
        return Result.OK(srShopGoodsTypeEntity);
    }
}
