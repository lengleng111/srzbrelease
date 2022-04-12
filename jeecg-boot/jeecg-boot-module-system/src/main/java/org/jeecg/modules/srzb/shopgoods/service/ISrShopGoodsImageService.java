package org.jeecg.modules.srzb.shopgoods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.srzb.shopgoods.dao.entity.SrShopGoosImageEntity;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface ISrShopGoodsImageService extends IService<SrShopGoosImageEntity> {
    Result<?> saveImage(MultipartHttpServletRequest multipartRequest, SrShopGoosImageEntity srShopGoosImageEntity);
    Result<?> queryImage(String businessId,String type);

    Result<?> deleteImage(Long id);
}
