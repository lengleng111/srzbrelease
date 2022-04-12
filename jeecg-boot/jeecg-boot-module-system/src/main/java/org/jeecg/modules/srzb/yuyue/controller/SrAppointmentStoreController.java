package org.jeecg.modules.srzb.yuyue.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.srzb.yuyue.dao.dto.SrYuyueDto;
import org.jeecg.modules.srzb.yuyue.service.impl.SrAppointmentStoreServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 预约到店信息表 前端控制器
 * </p>
 *
 * @author LZQ
 * @since 2022-04-09
 */
@RestController
@Slf4j
@RequestMapping("/yuyue")
@Api(description = "预约到店", tags = "预约到店")
public class SrAppointmentStoreController {

    @Autowired
    private SrAppointmentStoreServiceImpl srAppointmentStoreService;

    @ApiOperation(value = "顾客预约到店新增", notes = "顾客预约到店新增")
    @PostMapping("/customerstore")
    public Result<?>  customerAppointmentStore(@RequestBody SrYuyueDto srYuyueDto){
        return srAppointmentStoreService.customerAppointmentStore(srYuyueDto);
    }

    @ApiOperation(value = "查询该客户预约到店记录", notes = "查询该客户预约到店记录")
    @GetMapping("/queryappointmentrecord")
    public Result<?>  queryAppointmentRecord(@RequestParam Long userId,@RequestParam int pageNo,@RequestParam int pageSize){
        return srAppointmentStoreService.queryAppointmentRecord(userId,pageNo,pageSize);
    }

    @ApiOperation(value = "员工查询客户预约到店记录", notes = "员工查询客户预约到店记录")
    @GetMapping("/empquery")
    public Result<?>  employeeQuery(@RequestParam Long userId,@RequestParam(required = false,defaultValue = "") String userName,@RequestParam int pageNo,@RequestParam int pageSize){
        return srAppointmentStoreService.employeeQuery(userId,userName,pageNo,pageSize);
    }

    @ApiOperation(value = "管理端查询客户预约到店记录", notes = "管理端查询客户预约到店记录")
    @GetMapping("/pcquery")
    public Result<?>  pcQuery(@RequestParam(required = false,defaultValue = "") String storeName,@RequestParam(required = false,defaultValue = "") String userName,@RequestParam int pageNo,@RequestParam int pageSize){
        return srAppointmentStoreService.pcQuery(storeName, userName, pageNo, pageSize);
    }


    @ApiOperation(value = "管理端删除客户预约到店记录", notes = "管理端删除客户预约到店记录")
    @GetMapping("/pcdelete")
    public Result<?> pcDelete(@RequestParam Long id){
        return srAppointmentStoreService.pcDelete(id);
    }

    @ApiOperation(value = "客户接待完成修改状态", notes = "客户接待完成修改状态")
    @GetMapping("/modifyReceive")
    public Result<?> modifyReceive(@RequestParam Long id,@RequestParam Long receivestaffId,@RequestParam String receivestaffName){
        return srAppointmentStoreService.modifyReceive(id, receivestaffId, receivestaffName);
    }

    @ApiOperation(value = "查询一条预约到店信息", notes = "查询一条预约到店信息")
    @GetMapping("/selectone")
    public Result<?> selectone(@RequestParam Long id){
        return srAppointmentStoreService.selectone(id);
    }
}
