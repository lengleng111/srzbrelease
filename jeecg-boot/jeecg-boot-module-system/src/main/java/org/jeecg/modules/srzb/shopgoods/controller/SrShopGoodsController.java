package org.jeecg.modules.srzb.shopgoods.controller;



import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.srzb.shopgoods.dao.dto.ShopGoodsDto;
import org.jeecg.modules.srzb.shopgoods.service.impl.SrShopGoodsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 商品信息表 前端控制器
 * </p>
 *
 * @author LZQ
 * @since 2022-03-06
 */
@RestController
@RequestMapping("/shopgoods")
@Api(description = "商品管理页面", tags = "商品管理页面")
public class SrShopGoodsController {
    @Autowired
    private SrShopGoodsServiceImpl srShopGoodsService;

    @ApiOperation(value = "查询全部商品信息", notes = "查询全部商品信息")
    @GetMapping("/queryall")
    public Result<?> queryGoods(@RequestParam(name="pageNo", defaultValue="1")int pageNo,
                                @RequestParam(name="pageSize", defaultValue="10")int pageSize,
                                @RequestParam(required = false,defaultValue = "") String goodsName,
                                @RequestParam(required = false,defaultValue = "") String typeId,
                                @RequestParam(required = false,defaultValue = "") String saleType){
        return srShopGoodsService.queryGoods(pageNo, pageSize, goodsName, typeId, saleType);
    }

    @DeleteMapping ("/deletegoods")
    public Result<?> deleteGoods(@RequestParam Long id){
        return srShopGoodsService.deleteGoods(id);
    }

    @PostMapping("/insertgoods")
    public Result<?> insertGoods(@RequestBody ShopGoodsDto shopGoodsDto){
        return srShopGoodsService.insertGoods(shopGoodsDto);
    }

    @PostMapping("/updategoods")
    public Result<?> updateGoods(@RequestBody ShopGoodsDto shopGoodsDto){
        return srShopGoodsService.updateGoods(shopGoodsDto);
    }

    @GetMapping("/selectone")
    public Result<?> selectOneGoods(@RequestParam Long id){
        return srShopGoodsService.selectOne(id);
    }

    @ApiOperation(value = "根据类型查询商品", notes = "根据类型查询商品")
    @GetMapping("/selectbytype")
    public Result<?> selectByType(@RequestParam Long typeId,@RequestParam int pageNo,@RequestParam int pageSize){
        return srShopGoodsService.selectByType(typeId,pageNo,pageSize);
    }

    @ApiOperation(value = "根据展示类型查询商品", notes = "根据展示类型查询商品")
    @GetMapping("/selectbydisplaytype")
    public Result<?> selectByDisplayType(@RequestParam String displayType){
        return srShopGoodsService.selectByDisplayType(displayType);
    }


    @ApiOperation(value = "首页搜索商品", notes = "首页搜索商品")
    @GetMapping("/selectkeyword")
    public Result<?> selectKeyword(@RequestParam String keyword,@RequestParam int pageNo,@RequestParam int pageSize){
        return srShopGoodsService.selectKeyword(keyword,pageNo,pageSize);
    }

}
