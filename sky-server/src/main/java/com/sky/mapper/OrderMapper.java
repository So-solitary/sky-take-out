package com.sky.mapper;

import com.sky.entity.Orders;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;

@Mapper
public interface OrderMapper {

    /**
     * 新增订单
     * @param orders
     */
    @Insert("insert into orders(number, status, user_id, address_book_id, order_time, checkout_time, pay_method, pay_status, amount, remark, phone, address, user_name, consignee, cancel_reason, rejection_reason, cancel_time, estimated_delivery_time, delivery_status, delivery_time, pack_amount, tableware_number, tableware_status) " +
            "VALUES (#{number}, #{status}, #{userId}, #{addressBookId}, #{orderTime}, #{checkoutTime}, #{payMethod}, #{payStatus}, #{amount}, #{remark}, #{phone}, #{address}, #{userName}, #{consignee}, #{cancelReason}, #{rejectionReason}, #{cancelTime}, #{estimatedDeliveryTime}, #{deliveryStatus}, #{deliveryTime}, #{packAmount}, #{tablewareNumber}, #{tablewareStatus})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(Orders orders);

    /**
     * 根据订单号和用户id查询订单
     * @param orderNumber
     * @param userId
     */
    @Select("select * from orders where number = #{orderNumber} and user_id= #{userId}")
    Orders getByNumberAndUserId(String orderNumber, Long userId);

    /**
     * 修改订单信息
     * @param orders
     */
    void update(Orders orders);

    @Update("update orders set status = #{orderStatus},pay_status = #{orderPaidStatus} ,checkout_time = #{check_out_time} where id = #{id}")
    void updateStatus(Integer orderStatus, Integer orderPaidStatus, LocalDateTime check_out_time, Long id);
}
