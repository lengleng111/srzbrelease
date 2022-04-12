package org.jeecg.modules.srzb.shopgoods.controller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.srzb.shopgoods.dao.entity.SrShopGoosImageEntity;
import org.jeecg.modules.srzb.shopgoods.service.impl.SrShopGoodsImageServiceImpl;
import org.jeecg.modules.srzb.shopgoods.service.impl.SrShopGoodsTypeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

@Service
@Slf4j
@RestController
@RequestMapping("/shopgoodsimage")
public class SrShopGoodsImageController {

    @Autowired
    private SrShopGoodsImageServiceImpl srShopGoodsImageService;
    @ApiOperation(value = "保存图片", notes = "保存图片")
    @RequestMapping(value = "/compressedImage",method = RequestMethod.POST)
    public Result<?> saveImage(HttpServletRequest multipartRequest){
        Result<?> result  =new Result<>();
        try{
            SrShopGoosImageEntity srShopGoosImageEntity=new SrShopGoosImageEntity();
            srShopGoosImageEntity.setGoodsId(Long.parseLong(multipartRequest.getParameter("businessId")));
            srShopGoosImageEntity.setType(multipartRequest.getParameter("pcitureType"));
            return srShopGoodsImageService.saveImage((MultipartHttpServletRequest)multipartRequest,srShopGoosImageEntity);
        }catch (Exception e){
            return result.error500("系统错误"+e.getMessage());
        }
    }


    @ApiOperation(value = "图片查询", notes = "图片查询")
    @RequestMapping(value = "/queryImage",method = RequestMethod.GET)
    public Result<?> queryImage(@RequestParam(name = "businessId") String businessId,@RequestParam String type){
        Result<String > result  =new Result<String>();
        try{
            return srShopGoodsImageService.queryImage(businessId,type);
        }catch (Exception e){
            return result.error500("系统错误"+e.getMessage());
        }
    }

    /**
     * @Author fuwang
     * @Description 图片删除
     * @param
     * @Return
     * @Date 2022/3/18 14:09
     */
    @ApiOperation(value = "图片删除", notes = "图片删除")
    @RequestMapping(value = "/deleteImage",method = RequestMethod.GET)
    public Result<?> deleteImage(@RequestParam Long id){
        Result<String > result  =new Result<String>();
        try{
            return srShopGoodsImageService.deleteImage(id);
        }catch (Exception e){
            return result.error500("系统错误"+e.getMessage());
        }
    }
}
