package org.jeecg.modules.srzb.user.dao.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="RegisterDto")
public class RegisterDto {

    @ApiModelProperty(value = "微信唯一标识")
    private String unionId;

    @ApiModelProperty(value = "标志位（1员工 2顾客）")
    private String flag;

    @ApiModelProperty(value = "证件号")
    private String idcard;

    @ApiModelProperty(value = "联系方式")
    private String phone;

    @ApiModelProperty(value = "姓名")
    private String userName;
}
