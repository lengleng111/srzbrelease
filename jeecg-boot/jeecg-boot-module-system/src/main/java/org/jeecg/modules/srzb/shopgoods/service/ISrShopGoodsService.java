package org.jeecg.modules.srzb.shopgoods.service;

import org.apache.poi.ss.formula.functions.T;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.srzb.shopgoods.dao.dto.ShopGoodsDto;
import org.jeecg.modules.srzb.shopgoods.dao.entity.SrShopGoodsEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 商品信息表 服务类
 * </p>
 *
 * @author LZQ
 * @since 2022-03-06
 */
public interface ISrShopGoodsService extends IService<SrShopGoodsEntity> {
    Result<?> queryGoods(int pageNo, int pageSize, String goodsName, String typeId, String saleType);
    Result<?> deleteGoods(Long id);
    Result<?> insertGoods(ShopGoodsDto shopGoodsDto);
    Result<?> updateGoods(ShopGoodsDto shopGoodsDto);
    Result<?> selectOne(Long id);
    Result<?> selectByType(Long typeId,int pageNo,int pageSize);
    Result<?> selectByDisplayType(String dispalyType);
    Result<?> selectKeyword(String keyword,int pageNo,int pageSize);
}
