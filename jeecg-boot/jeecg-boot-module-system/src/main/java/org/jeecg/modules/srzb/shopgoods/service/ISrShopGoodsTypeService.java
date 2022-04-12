package org.jeecg.modules.srzb.shopgoods.service;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.srzb.shopgoods.dao.dto.ShopTypeDto;
import org.jeecg.modules.srzb.shopgoods.dao.entity.SrShopGoodsTypeEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.srzb.shopgoods.dao.entity.SrShopGoosImageEntity;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LZQ
 * @since 2022-03-15
 */
public interface ISrShopGoodsTypeService extends IService<SrShopGoodsTypeEntity> {
    Result<?> queryAllType(int pageNo,int pageSize,String name);
    Result<?> insertType(MultipartHttpServletRequest multipartRequest, SrShopGoodsTypeEntity srShopGoodsTypeEntity);
    Result<?> deleteType(Long id);
    Result<?> updateType(MultipartHttpServletRequest multipartRequest, String typeId);
    Result<?> seletcOne(Long id);

}
