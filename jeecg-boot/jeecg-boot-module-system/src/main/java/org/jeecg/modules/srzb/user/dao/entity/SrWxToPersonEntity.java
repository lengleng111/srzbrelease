package org.jeecg.modules.srzb.user.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 微信和人员信息关联表
 * </p>
 *
 * @author LZQ
 * @since 2022-03-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sr_wx_to_person")
@ApiModel(value="SrWxToPersonEntity对象", description="微信和人员信息关联表")
public class SrWxToPersonEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "微信账号unionId")
    private String unionId;

    @ApiModelProperty(value = "二维码串")
    private String qrcode;

    @ApiModelProperty(value = "身份证号")
    private String idcard;

    @ApiModelProperty(value = "备用字段1")
    private String remark1;

    @ApiModelProperty(value = "备用字段2")
    private String remark2;

    @ApiModelProperty(value = "备用字段3")
    private String remark3;

    @ApiModelProperty(value = "备用字段4")
    private String remark4;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "创建日期")
    private Date createTime;

    @ApiModelProperty(value = "更新人")
    private String updateBy;

    @ApiModelProperty(value = "更新日期")
    private Date updateTime;


}
