package com.sky.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 购物车
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("用户购物车")
public class ShoppingCartVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    //名称
    @ApiModelProperty("名称")
    private String name;

    //用户id
    @ApiModelProperty("用户id")
    private Long userId;

    //菜品id
    @ApiModelProperty("菜品id")
    private Long dishId;

    //套餐id
    @ApiModelProperty("套餐id")
    private Long setmealId;

    //口味
    @ApiModelProperty("口味")
    private String dishFlavor;

    //数量
    @ApiModelProperty("数量")
    private Integer number;

    //金额
    @ApiModelProperty("金额")
    private BigDecimal amount;

    //图片
    @ApiModelProperty("图片")
    private String image;

    //创建时间
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;
}
