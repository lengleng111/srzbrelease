package org.jeecg.modules.srzb.yuyue.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 预约到店信息表
 * </p>
 *
 * @author LZQ
 * @since 2022-04-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sr_appointment_store")
@ApiModel(value="SrAppointmentStoreEntity对象", description="预约到店信息表")
public class SrAppointmentStoreEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

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

    @ApiModelProperty(value = "接待员工id")
    private Long receivestaffId;

    @ApiModelProperty(value = "接待员工姓名")
    private String receivestaffName;

    @ApiModelProperty(value = "接待完成时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date receivestaffTime;

    @ApiModelProperty(value = "是否接待完成（1是 2否）")
    private String isReceiveover;

    @ApiModelProperty(value = "是否有效（1是 2否）")
    private String isEffective;

    @ApiModelProperty(value = "店铺地址")
    private String storeAddress;

    @ApiModelProperty(value = "空白字段2")
    private String blank2;

    @ApiModelProperty(value = "空白字段3")
    private String blank3;

    @ApiModelProperty(value = "空白字段4")
    private String blank4;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新人")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;


}
