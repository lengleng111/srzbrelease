package org.jeecg.modules.srzb.shopgoods.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.srzb.employee.dao.dto.EmployeeDto;
import org.jeecg.modules.srzb.shopgoods.dao.dto.ShopGoodsDto;
import org.jeecg.modules.srzb.shopgoods.dao.dto.ShopTypeDto;
import org.jeecg.modules.srzb.shopgoods.dao.entity.SrShopGoodsTypeEntity;
import org.jeecg.modules.srzb.shopgoods.service.ISrShopGoodsTypeService;
import org.jeecg.modules.srzb.shopgoods.service.impl.SrShopGoodsTypeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LZQ
 * @since 2022-03-15
 */
@RestController
@RequestMapping("/shopgoods/type")
@Api(description = "商品分类页面", tags = "商品分类页面")
public class SrShopGoodsTypeController {

@Autowired
private SrShopGoodsTypeServiceImpl srShopGoodsTypeService;
    /**
     * 查询全部商品分类
     * @return
     */
    @GetMapping("/queryall")
    public Result<?> queryAllType(@RequestParam(name="pageNo", defaultValue="1")int pageNo,
                                  @RequestParam(name="pageSize", defaultValue="10")int pageSize,
                                  @RequestParam(required = false,defaultValue = "") String name){
        return srShopGoodsTypeService.queryAllType(pageNo,pageSize,name);
    }

    /**
     * 新增商品分类
     * @param multipartRequest
     * @return
     */
    @PostMapping("/inserttype")
    public Result<?> insertType(HttpServletRequest multipartRequest){
        SrShopGoodsTypeEntity srShopGoodsTypeEntity=new SrShopGoodsTypeEntity();
        srShopGoodsTypeEntity.setName(multipartRequest.getParameter("name"));
        return srShopGoodsTypeService.insertType((MultipartHttpServletRequest)multipartRequest,srShopGoodsTypeEntity);
    }

    /**
     * 删除商品分类
     * @param id
     * @return
     */
    @DeleteMapping("/deletetype")
    public Result<?> deleteType(@RequestParam Long id){
        return srShopGoodsTypeService.deleteType(id);
    }

    /**
     * 修改商品分类
     * @param multipartRequest
     * @return
     */
    @PostMapping("/updatetype")
    public Result<?> updateType(HttpServletRequest multipartRequest){
        String typeId = multipartRequest.getParameter("id");
        return srShopGoodsTypeService.updateType((MultipartHttpServletRequest)multipartRequest,typeId);
    }

    /**
     * 根据id查询商品分类
     * @param id
     * @return
     */
    @GetMapping("/selectone")
    public Result<?> selectOne(@RequestParam Long id){
        return srShopGoodsTypeService.seletcOne(id);
    }
}
