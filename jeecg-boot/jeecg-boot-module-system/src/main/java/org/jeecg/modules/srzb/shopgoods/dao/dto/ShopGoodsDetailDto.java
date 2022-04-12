package org.jeecg.modules.srzb.shopgoods.dao.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ShopGoodsDetailDto {
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

    @ApiModelProperty(value = "商品名字")
    private String name;

    @ApiModelProperty(value = "商品原价")
    private BigDecimal price;

    @ApiModelProperty(value = "商品现价")
    private BigDecimal newPrice;

    @ApiModelProperty(value = "类型id")
    private Long typeId;

    @ApiModelProperty(value = "类型名称")
    private String typeName;

    @ApiModelProperty(value = "商品简介")
    private String contents;

    @ApiModelProperty(value = "库存")
    private Integer num;

    @ApiModelProperty(value = "销量")
    private Integer sales;

    @ApiModelProperty(value = "克重")
    private Integer weight;

    @ApiModelProperty(value = "材料")
    private String material;

    @ApiModelProperty(value = "销售状态(1在售 0不在售)")
    private String saleType;

    @ApiModelProperty(value = "商品编号")
    private String goodsCode;

    @ApiModelProperty(value = "展示类型")
    private String displayType;

    @ApiModelProperty(value = "商品封面地址")
    private String path;
}
