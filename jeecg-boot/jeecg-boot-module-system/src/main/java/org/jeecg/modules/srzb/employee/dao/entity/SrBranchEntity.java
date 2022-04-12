package org.jeecg.modules.srzb.employee.dao.entity;

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
 * 店铺信息表
 * </p>
 *
 * @author LZQ
 * @since 2022-03-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sr_branch")
@ApiModel(value="SrBranchEntity对象", description="店铺信息表")
public class SrBranchEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "创建日期")
    private Date createTime;

    @ApiModelProperty(value = "更新人")
    private String updateBy;

    @ApiModelProperty(value = "更新日期")
    private Date updateTime;

    @ApiModelProperty(value = "分店名称")
    private String branchName;

    @ApiModelProperty(value = "分店地址")
    private String branchAdress;

    @ApiModelProperty(value = "店铺联系电话")
    private String branchPhone;

    @ApiModelProperty(value = "空白字段1")
    private String black1;

    @ApiModelProperty(value = "空白字段2")
    private String black2;

    @ApiModelProperty(value = "空白字段3")
    private String black3;

    @ApiModelProperty(value = "空白字段4")
    private String black4;


}
