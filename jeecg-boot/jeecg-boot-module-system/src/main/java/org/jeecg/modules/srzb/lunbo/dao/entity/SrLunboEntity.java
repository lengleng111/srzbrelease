package org.jeecg.modules.srzb.lunbo.dao.entity;

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
 * 轮播图信息表
 * </p>
 *
 * @author LZQ
 * @since 2022-03-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sr_lunbo")
@ApiModel(value="SrLunboEntity对象", description="轮播图信息表")
public class SrLunboEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "轮播图id")
    @TableId(value = "lunbo_id", type = IdType.AUTO)
    private Long lunboId;

    @ApiModelProperty(value = "图片路径")
    private String lunboUrl;

    @ApiModelProperty(value = "轮播图片名称")
    private String lunboName;

    private String blank1;

    private String blank2;

    private String blank3;

    private String blank4;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新人")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "是否有效（0无效 1有效）")
    private String isvalid;


}
