package org.jeecg.modules.srzb.shopgoods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.srzb.shopgoods.dao.entity.SrShopGoodsTypeEntity;
import org.jeecg.modules.srzb.shopgoods.dao.entity.SrShopGoosImageEntity;
import org.jeecg.modules.srzb.shopgoods.mapper.SrShopGoodsImageMapper;
import org.jeecg.modules.srzb.shopgoods.mapper.SrShopGoodsMapper;
import org.jeecg.modules.srzb.shopgoods.mapper.SrShopGoodsTypeMapper;
import org.jeecg.modules.srzb.shopgoods.service.ISrShopGoodsImageService;
import org.jeecg.modules.srzb.shopgoods.service.ISrShopGoodsTypeService;
import org.jeecg.modules.srzb.utils.StreamCompressionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import org.apache.commons.codec.binary.Base64;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class SrShopGoodsImageServiceImpl extends ServiceImpl<SrShopGoodsImageMapper, SrShopGoosImageEntity> implements ISrShopGoodsImageService {
    @Autowired
    private SrShopGoodsImageMapper srShopGoodsImageMapper;
    @Override
    public Result<?> saveImage(MultipartHttpServletRequest multipartRequest, SrShopGoosImageEntity srShopGoosImageEntity) {
        try {
            // 获取上传的文件
            MultipartFile multiFile = multipartRequest.getFile("file");
            InputStream inputStream = multiFile.getInputStream();
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            inputStream.close();
            byte[] data = outStream.toByteArray();
            Base64 base64 = new Base64();
            //先压缩再base64
            byte[] zip = StreamCompressionUtils.zip(data);
            String picture = base64.encodeToString(zip);
            srShopGoosImageEntity.setPath(picture);
            srShopGoosImageEntity.setCreateBy("ds");
            srShopGoosImageEntity.setUpdateBy("fd");
            srShopGoosImageEntity.setCreateTime(new Date());
            srShopGoosImageEntity.setUpdateTime(new Date());
            srShopGoodsImageMapper.insert(srShopGoosImageEntity);
            return Result.OK("保存成功");
        } catch (Exception e) {
            log.info("saveImage：",e.getMessage());
            e.printStackTrace();
            return Result.error("系统错误"+e.getMessage());
        }
    }

    @Override
    public Result<?> queryImage(String businessId,String type) {
        try{
            Base64 base64 = new Base64();
            LambdaQueryWrapper<SrShopGoosImageEntity> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SrShopGoosImageEntity::getGoodsId,businessId);
            wrapper.eq(SrShopGoosImageEntity::getType,type);
            List<SrShopGoosImageEntity> list = this.list(wrapper);
            for (SrShopGoosImageEntity srShopGoosImageEntity:list){
                byte[] decompression = base64.decode(srShopGoosImageEntity.getPath());
                byte[] unzip = StreamCompressionUtils.unZip(decompression);
                String picture = base64.encodeToString(unzip);
                srShopGoosImageEntity.setPath(picture);
            }
            return Result.OK(list);
        }catch (Exception e){
            log.info("queryImage：",e.getMessage());
            e.printStackTrace();
            return Result.error("系统错误"+e.getMessage());
        }


    }
    /**
     * @Author fuwang
     * @Description 图片删除
     * @param
     * @Return
     * @Date 2022/3/18 14:15
     */
    @Override
    public Result<?> deleteImage(Long id) {
        LambdaQueryWrapper<SrShopGoosImageEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SrShopGoosImageEntity::getId,id);
        SrShopGoosImageEntity srShopGoosImageEntity = this.getOne(wrapper);
        srShopGoodsImageMapper.deleteById(srShopGoosImageEntity);
        return Result.OK("商品图片删除成功");
    }
}
