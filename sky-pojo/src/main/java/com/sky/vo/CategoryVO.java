package com.sky.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "分类分页查询返回的数据格式")
public class CategoryVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    private Long id;

    //类型: 1菜品分类 2套餐分类
    @ApiModelProperty("类型")
    private Integer type;

    //分类名称
    @ApiModelProperty("分类名称")
    private String name;

    //顺序
    @ApiModelProperty("顺序")
    private Integer sort;

    //分类状态 0标识禁用 1表示启用
    @ApiModelProperty("分类状态 0标识禁用 1表示启用")
    private Integer status;

    //创建时间
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    //更新时间
    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    //创建人
    @ApiModelProperty("创建人")
    private Long createUser;

    //修改人
    @ApiModelProperty("修改人")
    private Long updateUser;
}
