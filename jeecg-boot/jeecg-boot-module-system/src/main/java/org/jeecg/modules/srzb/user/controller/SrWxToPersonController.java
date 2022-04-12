package org.jeecg.modules.srzb.user.controller;


import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.srzb.user.dao.dto.RegisterDto;
import org.jeecg.modules.srzb.user.service.impl.SrWxToPersonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 微信和人员信息关联表 前端控制器
 * </p>
 *
 * @author LZQ
 * @since 2022-03-26
 */
@RestController
@RequestMapping("/user/weixin")
public class SrWxToPersonController {
    @Autowired
    private SrWxToPersonServiceImpl srWxToPersonService;
    @ApiOperation(value = "微信获取unionid")
    @GetMapping("/getUnionId")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "用户登陆code", paramType = "query", required = true),
    })
    public Result<?> getUnionId(@RequestParam String code) throws Exception {
        return srWxToPersonService.getUnionId(code);
    }

    @GetMapping("/getUserInfo")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "idCardNo", value = "证件号", paramType = "query", required = true)
    })
    public Result<?> getByIdCardNoAndIdCardType( String idCardNo){
        return srWxToPersonService.getUserInfo(idCardNo);
    }

    @PostMapping("saveRegister")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "registerDto", value = "RegisterDto", required = true, paramType = "body", dataType = "RegisterDto")
    })
    public Result<?> saveRegister(@RequestBody RegisterDto registerDto){
        return srWxToPersonService.saveRegister(registerDto);
    }

    @PostMapping("/updateInfo")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "registerDto", value = "RegisterDto", required = true, paramType = "body", dataType = "RegisterDto")
    })
    public Result<?> updateInfo(@RequestBody RegisterDto registerDto){
        return srWxToPersonService.updateInfo(registerDto);
    }

    @ApiOperation("小程序获取token")
    @GetMapping("/wxGetToken")
    public Result<JSONObject> wxLogin(@RequestParam String unionId) {
        return srWxToPersonService.wxLogin(unionId);
    }
}
