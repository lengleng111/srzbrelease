package org.jeecg.modules.srzb.lunbo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.srzb.lunbo.dao.entity.SrLunboEntity;
import org.jeecg.modules.srzb.lunbo.mapper.SrLunboMapper;
import org.jeecg.modules.srzb.lunbo.service.ISrLunboService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.srzb.shopgoods.dao.entity.SrShopGoodsTypeEntity;
import org.jeecg.modules.srzb.utils.PictureUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 轮播图信息表 服务实现类
 * </p>
 *
 * @author LZQ
 * @since 2022-03-23
 */
@Service
@Slf4j
public class SrLunboServiceImpl extends ServiceImpl<SrLunboMapper, SrLunboEntity> implements ISrLunboService {


    @Autowired
    private SrLunboMapper srLunboMapper;
    @Override
    public Result<?> queryAllLunbo(int pageNo, int pageSize, String lunboName,String isvalid) {
        try {
            LambdaQueryWrapper<SrLunboEntity> wrapper=new LambdaQueryWrapper<>();
            if (!lunboName.equals("")&& !StringUtils.isEmpty(lunboName)){
                wrapper.like(SrLunboEntity::getLunboName,lunboName);
            }
            if (!isvalid.equals("")&& !StringUtils.isEmpty(isvalid)){
                wrapper.like(SrLunboEntity::getIsvalid,isvalid);
            }
            Page<SrLunboEntity> page=new Page<>(pageNo,pageSize);
            Page<SrLunboEntity> entitypage=this.page(page,wrapper);
            List<SrLunboEntity> records = entitypage.getRecords();
            for (SrLunboEntity srLunboEntity:records){
                srLunboEntity.setLunboUrl(PictureUtils.convertPictureAddress(srLunboEntity.getLunboUrl()));
            }
            entitypage=entitypage.setRecords(records);
            return Result.OK(entitypage);
        }catch (Exception e){
            log.info("queryAllLunbo：",e.getMessage());
            e.printStackTrace();
            return Result.error("系统错误"+e.getMessage());
        }

    }

    @Override
    public Result<?> insertLunbo(MultipartHttpServletRequest multipartRequest, SrLunboEntity srLunboEntity) {
        try {
            String pictureAddress = PictureUtils.getPictureAddress(multipartRequest);
            srLunboEntity.setLunboUrl(pictureAddress);
            srLunboEntity.setCreateTime(new Date());
            srLunboEntity.setUpdateTime(new Date());
            srLunboEntity.setCreateBy("gd");
            srLunboEntity.setUpdateBy("fgd");
            srLunboMapper.insert(srLunboEntity);
            log.info("保存轮播图成功");
            return Result.OK("保存轮播图成功");
        }catch (Exception e){
            log.info("insertLunbo：",e.getMessage());
            e.printStackTrace();
            return Result.error("系统错误"+e.getMessage());
        }

    }

    @Override
    public Result<?> deleteLunbo(Long id) {
        srLunboMapper.deleteById(id);
        log.info("删除轮播图成功");
        return Result.OK();
    }

    @Override
    public Result<?> updateLunbo(MultipartHttpServletRequest multipartRequest, String lunboId) {
        try {
            LambdaQueryWrapper<SrLunboEntity> wrapper=new LambdaQueryWrapper<>();
            wrapper.eq(SrLunboEntity::getLunboId,Long.parseLong(lunboId));
            SrLunboEntity srLunboEntity=this.getOne(wrapper);
            srLunboEntity.setLunboName(multipartRequest.getParameter("lunboName"));
            srLunboEntity.setLunboUrl(PictureUtils.getPictureAddress(multipartRequest));
            srLunboEntity.setUpdateTime(new Date());
            srLunboEntity.setUpdateBy("dsf");
            srLunboEntity.setIsvalid(multipartRequest.getParameter("isvalid"));
            srLunboMapper.updateById(srLunboEntity);
            log.info("修改轮播图成功");
            return Result.OK();
        }catch (Exception e){
            log.info("updateLunbo：",e.getMessage());
            e.printStackTrace();
            return Result.error("系统错误"+e.getMessage());
        }
    }

    @Override
    public Result<?> seletcOne(Long lunboId) {
        LambdaQueryWrapper<SrLunboEntity> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(SrLunboEntity::getLunboId,lunboId);
        SrLunboEntity srLunboEntity=this.getOne(wrapper);
        srLunboEntity.setLunboUrl(PictureUtils.convertPictureAddress(srLunboEntity.getLunboUrl()));
        log.info("根据id查询轮播图");
        return Result.OK(srLunboEntity);
    }
}
