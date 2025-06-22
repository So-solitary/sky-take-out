package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import com.sky.vo.ShoppingCartVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetmealMapper setmealMapper;

    /**
     * 添加购物车
     * @param shoppingCartDTO
     */
    @Override
    public void addShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        shoppingCart.setUserId(BaseContext.getCurrentId());

        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);

        if (list != null && !list.isEmpty()) {
            shoppingCart = list.get(0);
            shoppingCart.setNumber(shoppingCart.getNumber() + 1);
            shoppingCartMapper.update(shoppingCart);
        } else {

            if (shoppingCartDTO.getDishId() != null) {
                // 菜品
                Dish dish = dishMapper.getById(shoppingCartDTO.getDishId().intValue());
                shoppingCart.setName(dish.getName());
                shoppingCart.setImage(dish.getImage());
                shoppingCart.setDishFlavor(shoppingCartDTO.getDishFlavor());
                shoppingCart.setAmount(dish.getPrice());

            } else {
                // 套餐
                Setmeal setmeal = setmealMapper.getById(shoppingCartDTO.getSetmealId());
                shoppingCart.setName(setmeal.getName());
                shoppingCart.setImage(setmeal.getImage());
                shoppingCart.setAmount(setmeal.getPrice());
            }
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartMapper.save(shoppingCart);
        }
    }

    /**
     * 从购物车中删除商品
     * @param shoppingCartDTO
     */
    @Override
    @Transactional
    public void subShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        shoppingCart.setUserId(BaseContext.getCurrentId());

        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        if (list != null && !list.isEmpty()) {
            ShoppingCart cart = list.get(0);
            if (cart.getNumber() > 1) {
                cart.setNumber(cart.getNumber() - 1);
                shoppingCartMapper.update(cart);
            } else  {
                shoppingCartMapper.delete(shoppingCart);
            }
        }

    }

    /**
     * 查看购物车
     * @return
     */
    @Override
    public List<ShoppingCartVO> list() {
        ShoppingCart shoppingCart = ShoppingCart.builder()
                .userId(BaseContext.getCurrentId())
                .build();
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        List<ShoppingCartVO> resList = new ArrayList<>();
        for (ShoppingCart cart : list) {
            ShoppingCartVO shoppingCartVO = new ShoppingCartVO();
            BeanUtils.copyProperties(cart, shoppingCartVO);
            resList.add(shoppingCartVO);
        }
        return resList;
    }

    @Override
    public void deleteByUserId(Long userId) {
        shoppingCartMapper.deleteByUserId(userId);
    }

    @Override
    public List<ShoppingCart> getByUserId(Long userId) {
        return shoppingCartMapper.getByUserId(userId);
    }


}
