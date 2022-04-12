package org.jeecg.modules.srzb.yuyue.dao.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class SrYuyueDto {
    @ApiModelProperty(value = "客户id")
    private Long userId;

    @ApiModelProperty(value = "客户姓名")
    private String userName;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "所选店铺id")
    private Long storeId;

    @ApiModelProperty(value = "所选店铺")
    private String storeName;

    @ApiModelProperty(value = "预约时间(yyyy-MM-dd）")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date appointmentTime;

    @ApiModelProperty(value = "店铺地址")
    private String storeAddress;
}
