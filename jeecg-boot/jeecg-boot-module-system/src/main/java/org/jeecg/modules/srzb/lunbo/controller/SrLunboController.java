package org.jeecg.modules.srzb.lunbo.controller;


import io.swagger.annotations.Api;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.srzb.lunbo.dao.entity.SrLunboEntity;
import org.jeecg.modules.srzb.lunbo.service.impl.SrLunboServiceImpl;
import org.jeecg.modules.srzb.shopgoods.dao.entity.SrShopGoodsTypeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 轮播图信息表 前端控制器
 * </p>
 *
 * @author LZQ
 * @since 2022-03-23
 */
@RestController
@RequestMapping("/lunbo")
@Api(description = "轮播图页面", tags = "轮播图页面")
public class SrLunboController {

    @Autowired
    private SrLunboServiceImpl srLunboService;
    /**
     * 查询全部轮播图
     * @return
     */
    @GetMapping("/queryall")
    public Result<?> queryAllLunbo(@RequestParam(name="pageNo", defaultValue="1")int pageNo,
                                  @RequestParam(name="pageSize", defaultValue="10")int pageSize,
                                  @RequestParam(required = false,defaultValue = "") String lunboName,
                                   @RequestParam(required = false,defaultValue = "") String isvalid){
        return srLunboService.queryAllLunbo(pageNo,pageSize,lunboName,isvalid);
    }

    /**
     * 新增轮播图
     * @param multipartRequest
     * @return
     */
    @PostMapping("/insertlunbo")
    public Result<?> insertLunbo(HttpServletRequest multipartRequest){
        SrLunboEntity srLunboEntity=new SrLunboEntity();
        srLunboEntity.setLunboName(multipartRequest.getParameter("lunboName"));
        srLunboEntity.setIsvalid(multipartRequest.getParameter("isvalid"));
        return srLunboService.insertLunbo((MultipartHttpServletRequest)multipartRequest,srLunboEntity);
    }

    /**
     * 删除轮播图
     * @param id
     * @return
     */
    @DeleteMapping("/deletelunbo")
    public Result<?> deleteLunbo(@RequestParam Long id){
        return srLunboService.deleteLunbo(id);
    }

    /**
     * 修改轮播图
     * @param multipartRequest
     * @return
     */
    @PostMapping("/updatelunbo")
    public Result<?> updateLunbo(HttpServletRequest multipartRequest){
        String lunboId = multipartRequest.getParameter("lunboId");
        return srLunboService.updateLunbo((MultipartHttpServletRequest)multipartRequest,lunboId);
    }

    /**
     * 根据id查询轮播图
     * @param lunboId
     * @return
     */
    @GetMapping("/selectone")
    public Result<?> selectOne(@RequestParam Long lunboId){
        return srLunboService.seletcOne(lunboId);
    }

}
