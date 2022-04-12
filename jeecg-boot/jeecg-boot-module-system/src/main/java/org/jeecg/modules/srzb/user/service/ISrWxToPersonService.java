package org.jeecg.modules.srzb.user.service;

import com.alibaba.fastjson.JSONObject;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.srzb.user.dao.dto.RegisterDto;
import org.jeecg.modules.srzb.user.dao.entity.SrWxToPersonEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 微信和人员信息关联表 服务类
 * </p>
 *
 * @author LZQ
 * @since 2022-03-26
 */
public interface ISrWxToPersonService extends IService<SrWxToPersonEntity> {
    Result<?> getUnionId(String code);
    Result<?> getUserInfo(String idCardNo);
    Result<?> saveRegister(RegisterDto registerDto);
    Result<?> updateInfo(RegisterDto registerDto);
    Result<JSONObject> wxLogin(String unionId);
}
