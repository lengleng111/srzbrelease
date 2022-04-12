package org.jeecg.modules.srzb.shopgoods.dao.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sr_shop_goods_image")
@ApiModel(value="SrShopGoodsImageEntity对象", description="商品图片信息表")
public class SrShopGoosImageEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "图片展示类型(1封面2详情)")
    private String type;

    @ApiModelProperty(value = "图片资源")
    private String path;

    @ApiModelProperty(value = "商品关联主键")
    private Long goodsId;

    @ApiModelProperty(value = "空白字段2")
    private String blank2;

    @ApiModelProperty(value = "空白字段3")
    private String blank3;

    @ApiModelProperty(value = "空白字段4")
    private String blank4;


    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "创建日期")
    private Date createTime;

    @ApiModelProperty(value = "更新人")
    private String updateBy;

    @ApiModelProperty(value = "更新日期")
    private Date updateTime;

}
