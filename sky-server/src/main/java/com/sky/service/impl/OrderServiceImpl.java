package com.sky.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.entity.OrderDetail;
import com.sky.entity.Orders;
import com.sky.entity.ShoppingCart;
import com.sky.entity.User;
import com.sky.exception.OrderBusinessException;
import com.sky.mapper.OrderDetailMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.AddressBookService;
import com.sky.service.OrderService;
import com.sky.service.ShoppingCartService;
import com.sky.utils.WeChatPayUtil;
import com.sky.vo.AddressBookVO;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private AddressBookService addressBookMapper;


    @Autowired
    private ShoppingCartService shoppingCartMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WeChatPayUtil weChatPayUtil;

    private Orders orders;
    /**
     * 用户下单
     * @param ordersSubmitDTO
     */
    @Override
    @Transactional
    public OrderSubmitVO submit(OrdersSubmitDTO ordersSubmitDTO) {
        Long addressBookId = ordersSubmitDTO.getAddressBookId();
        AddressBookVO addressBookVO = addressBookMapper.getById(addressBookId);
        if (addressBookVO == null) {
            throw new OrderBusinessException(MessageConstant.ADDRESS_BOOK_IS_NULL);
        }

        Long userId = BaseContext.getCurrentId();
        List<ShoppingCart> shoppingCartList = shoppingCartMapper.getByUserId(userId);
        if (shoppingCartList == null || shoppingCartList.isEmpty()) {
            throw new OrderBusinessException(MessageConstant.SHOPPING_CART_IS_NULL);
        }

        // 构造订单数据
        Orders orders = new Orders();
        BeanUtils.copyProperties(ordersSubmitDTO, orders);
        orders.setNumber(String.valueOf(System.currentTimeMillis()));
        orders.setStatus(Orders.PENDING_PAYMENT);
        orders.setUserId(BaseContext.getCurrentId());
        orders.setOrderTime(LocalDateTime.now());
        orders.setPayStatus(Orders.UN_PAID);
        orders.setPhone(addressBookVO.getPhone());
        orders.setAddressBookId(addressBookVO.getId());
        orders.setAddress(addressBookVO.getDetail());
        orders.setConsignee(addressBookVO.getConsignee());
        this.orders = orders;

        orderMapper.save(orders);

        List<OrderDetail> list = new ArrayList<>();
        // 构造订单明细数据
        for (ShoppingCart shoppingCart : shoppingCartList) {
            OrderDetail orderDetail = new OrderDetail();
            BeanUtils.copyProperties(shoppingCart, orderDetail);
            orderDetail.setOrderId(orders.getId());
            list.add(orderDetail);
        }

        orderDetailMapper.insertBatch(list);

        //清理购物车中的数据
        shoppingCartMapper.deleteByUserId(userId);

        //封装返回结果
        OrderSubmitVO orderSubmitVO = OrderSubmitVO.builder()
                .id(orders.getId())
                .orderAmount(orders.getAmount())
                .orderNumber(orders.getNumber())
                .orderTime(orders.getOrderTime())
                .build();
        return orderSubmitVO;
    }

    /**
     * 订单支付
     * @param ordersPaymentDTO
     * @return
     */
    public OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        // 当前登录用户id
        Long userId = BaseContext.getCurrentId();
        User user = userMapper.getById(userId);

//        //调用微信支付接口，生成预支付交易单
//        JSONObject jsonObject = weChatPayUtil.pay(
//                ordersPaymentDTO.getOrderNumber(), //商户订单号
//                new BigDecimal(0.01), //支付金额，单位 元
//                "苍穹外卖订单", //商品描述
//                user.getOpenid() //微信用户的openid
//        );


//        if (jsonObject.getString("code") != null && jsonObject.getString("code").equals("ORDERPAID")) {
//            throw new OrderBusinessException("该订单已支付");
//        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code","ORDERPAID");
        OrderPaymentVO vo = jsonObject.toJavaObject(OrderPaymentVO.class);
        vo.setPackageStr(jsonObject.getString("package"));
        Integer OrderPaidStatus = Orders.PAID;//支付状态，已支付
        Integer OrderStatus = Orders.TO_BE_CONFIRMED;  //订单状态，待接单
        LocalDateTime check_out_time = LocalDateTime.now();//更新支付时间
        orderMapper.updateStatus(OrderStatus, OrderPaidStatus, check_out_time, this.orders.getId());

        return vo;
    }

    /**
     * 支付成功，修改订单状态
     * @param outTradeNo
     */
    public void paySuccess(String outTradeNo) {
        // 当前登录用户id
        Long userId = BaseContext.getCurrentId();

        // 根据订单号查询当前用户的订单
        Orders ordersDB = orderMapper.getByNumberAndUserId(outTradeNo, userId);

        // 根据订单id更新订单的状态、支付方式、支付状态、结账时间
        Orders orders = Orders.builder()
                .id(ordersDB.getId())
                .status(Orders.TO_BE_CONFIRMED)
                .payStatus(Orders.PAID)
                .checkoutTime(LocalDateTime.now())
                .build();

        orderMapper.update(orders);
    }
}
