package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.vo.ShoppingCartVO;
import org.apache.ibatis.annotations.Delete;

import java.util.List;

public interface ShoppingCartService {

    /**
     * 添加购物车
     * @param shoppingCartDTO
     */
    void addShoppingCart(ShoppingCartDTO shoppingCartDTO);

    /**
     * 查看购物车
     * @return
     */
    List<ShoppingCartVO> list();

    /**
     * 清空购物车
     * @param currentId
     */
    void deleteByUserId(Long currentId);

    /**
     * 根据userId查询购物车
     * @param userId
     * @return
     */
    List<ShoppingCart> getByUserId(Long userId);

    /**
     * 从购物车中删除商品
     * @param shoppingCartDTO
     */
    void subShoppingCart(ShoppingCartDTO shoppingCartDTO);
}
