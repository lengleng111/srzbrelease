package org.jeecg.modules.srzb.employee.dao.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BranchDto {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "更新人")
    private String updateBy;

    @ApiModelProperty(value = "分店名称")
    private String branchName;

    @ApiModelProperty(value = "分店地址")
    private String branchAdress;

    @ApiModelProperty(value = "店铺联系电话")
    private String branchPhone;
}
