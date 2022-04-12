package org.jeecg.modules.srzb.employee.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.srzb.employee.dao.dto.EmployeeDto;
import org.jeecg.modules.srzb.employee.service.impl.SrEmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 员工信息表 前端控制器
 * </p>
 *
 * @author LZQ
 * @since 2022-03-14
 */
@RestController
@RequestMapping("/employee/sr-employee-entity")
@Api(description = "员工管理页面", tags = "员工管理页面")
public class SrEmployeeController {

    @Autowired
    private SrEmployeeServiceImpl srEmployeeService;
    @ApiOperation(value = "查询全部员工信息", notes = "查询全部员工信息")
    @GetMapping("/queryall")
    public Result<?> queryAllEmp(
            @RequestParam(name="pageNo", defaultValue="1")int pageNo,
            @RequestParam(name="pageSize", defaultValue="10")int pageSize,
            @RequestParam(required = false,defaultValue = "") String name,
            @RequestParam(required = false,defaultValue = "") String idcard,
            @RequestParam(required = false,defaultValue = "") String branch
    ){
        return srEmployeeService.queryAllEmp(pageNo,pageSize,name,idcard,branch);
    }

    @PostMapping("/insertemp")
    public Result<?> insertEmp(@RequestBody EmployeeDto employeeDto){
        return srEmployeeService.insertEmp(employeeDto);
    }

    @DeleteMapping ("/deleteemp")
    public Result<?> delete(@RequestParam Long id){
        return srEmployeeService.deleteEmp(id);
    }

    @PostMapping("/updateemp")
    public Result<?> updateEmp(@RequestBody EmployeeDto employeeDto){
        return srEmployeeService.updateEmp(employeeDto);
    }

    @GetMapping("/selectone")
    public Result<?> selectOneGoods(@RequestParam Long id){
        return srEmployeeService.selectOne(id);
    }

}
