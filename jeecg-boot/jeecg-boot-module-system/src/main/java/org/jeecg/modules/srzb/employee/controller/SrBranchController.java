package org.jeecg.modules.srzb.employee.controller;


import io.swagger.annotations.Api;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.srzb.employee.dao.dto.BranchDto;
import org.jeecg.modules.srzb.employee.dao.dto.EmployeeDto;
import org.jeecg.modules.srzb.employee.service.impl.SrBranchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 店铺信息表 前端控制器
 * </p>
 *
 * @author LZQ
 * @since 2022-03-16
 */
@RestController
@RequestMapping("/employee/sr-branch-entity")
@Api(description = "店铺管理页面", tags = "店铺管理页面")
public class SrBranchController {

    @Autowired
    private SrBranchServiceImpl srBranchService;
    @GetMapping("/queryall")
    public Result<?> queryAllBranch(
            @RequestParam(name="pageNo", defaultValue="1")int pageNo,
            @RequestParam(name="pageSize", defaultValue="10")int pageSize,
            @RequestParam(required = false,defaultValue = "") String branchName
    ){
        return srBranchService.queryAllBranch(pageNo,pageSize,branchName);
    }

    @PostMapping("/insertbranch")
    public Result<?> insertEmp(@RequestBody BranchDto branchDto){
        return srBranchService.insertBranch(branchDto);
    }

    @DeleteMapping("/deletebranch")
    public Result<?> delete(@RequestParam Long id){
        return srBranchService.deleteBranch(id);
    }

    @PostMapping("/updatebranch")
    public Result<?> updateEmp(@RequestBody BranchDto branchDto){
        return srBranchService.updateBranch(branchDto);
    }

    @GetMapping("/selectone")
    public Result<?> selectOneGoods(@RequestParam Long id){
        return srBranchService.selectOne(id);
    }


}
