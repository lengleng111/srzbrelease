package org.jeecg.modules.srzb.employee.dao.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EmployeeDto {
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "员工姓名")
    private String name;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "身份证号")
    private String idcard;

    @ApiModelProperty(value = "所在分店")
    private String branch;

    @ApiModelProperty(value = "分店id")
    private Long branchId;

    @ApiModelProperty(value = "员工等级")
    private String level;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "更新人")
    private String updateBy;
}
