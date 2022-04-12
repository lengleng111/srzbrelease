package org.jeecg.modules.srzb.lunbo.service;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.srzb.lunbo.dao.entity.SrLunboEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.srzb.shopgoods.dao.entity.SrShopGoodsTypeEntity;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * <p>
 * 轮播图信息表 服务类
 * </p>
 *
 * @author LZQ
 * @since 2022-03-23
 */
public interface ISrLunboService extends IService<SrLunboEntity> {
    Result<?> queryAllLunbo(int pageNo, int pageSize, String lunboName,String isvalid);
    Result<?> insertLunbo(MultipartHttpServletRequest multipartRequest, SrLunboEntity srLunboEntity);
    Result<?> deleteLunbo(Long id);
    Result<?> updateLunbo(MultipartHttpServletRequest multipartRequest, String lunboId);
    Result<?> seletcOne(Long lunboId);
}
