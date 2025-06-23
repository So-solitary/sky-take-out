package com.sky.vo;

import com.sky.entity.OrderDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("订单信息返回")
public class OrdersVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("")
    private Long id;

    //订单号
    @ApiModelProperty("订单号")
    private String number;

    //订单状态 1待付款 2待接单 3已接单 4派送中 5已完成 6已取消 7退款
    @ApiModelProperty("订单状态 1待付款 2待接单 3已接单 4派送中 5已完成 6已取消 7退款")
    private Integer status;

    //下单用户id
    @ApiModelProperty("下单用户id")
    private Long userId;

    //地址id
    @ApiModelProperty("地址id")
    private Long addressBookId;

    //下单时间
    @ApiModelProperty("下单时间")
    private LocalDateTime orderTime;

    //结账时间
    @ApiModelProperty("结账时间")
    private LocalDateTime checkoutTime;

    //支付方式 1微信，2支付宝
    @ApiModelProperty("支付方式 1微信，2支付宝")
    private Integer payMethod;

    //支付状态 0未支付 1已支付 2退款
    @ApiModelProperty("支付状态 0未支付 1已支付 2退款")
    private Integer payStatus;

    //实收金额
    @ApiModelProperty("实收金额")
    private BigDecimal amount;

    //备注
    @ApiModelProperty("备注")
    private String remark;

    //用户名
    @ApiModelProperty("用户名")
    private String userName;

    //手机号
    @ApiModelProperty("手机号")
    private String phone;

    //地址
    @ApiModelProperty("地址")
    private String address;

    //收货人
    @ApiModelProperty("收货人")
    private String consignee;

    //订单取消原因
    @ApiModelProperty("订单取消原因")
    private String cancelReason;

    //订单拒绝原因
    @ApiModelProperty("订单拒绝原因")
    private String rejectionReason;

    //订单取消时间
    @ApiModelProperty("订单取消时间")
    private LocalDateTime cancelTime;

    //预计送达时间
    @ApiModelProperty("预计送达时间")
    private LocalDateTime estimatedDeliveryTime;

    //配送状态  1立即送出  0选择具体时间
    @ApiModelProperty("配送状态  1立即送出  0选择具体时间")
    private Integer deliveryStatus;

    //送达时间
    @ApiModelProperty("送达时间")
    private LocalDateTime deliveryTime;

    //打包费
    @ApiModelProperty("打包费")
    private int packAmount;

    //餐具数量
    @ApiModelProperty("餐具数量")
    private int tablewareNumber;

    //餐具数量状态  1按餐量提供  0选择具体数量
    @ApiModelProperty("餐具数量状态  1按餐量提供  0选择具体数量")
    private Integer tablewareStatus;

    @ApiModelProperty("订单明细列表")
    private List<OrderDetail> orderDetailList;
}
