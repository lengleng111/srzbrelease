package org.jeecg.modules.srzb.shopgoods.dao.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ShopTypeDto {

    @ApiModelProperty(value = "编号")
    private Integer id;

    @ApiModelProperty(value = "商品类型名称")
    private String name;

    @ApiModelProperty(value = "商品类型图片地址")
    private String imgUrl;

    @ApiModelProperty(value = "更新人")
    private String updateBy;

    @ApiModelProperty(value = "创建人")
    private String createBy;

}
