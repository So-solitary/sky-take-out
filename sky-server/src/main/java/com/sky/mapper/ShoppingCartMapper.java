package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {


    /**
     * 查看购物车
     * @param shoppingCart
     * @return
     */
    public List<ShoppingCart> list(ShoppingCart shoppingCart);

    /**
     * 变更购物车
     * @param shoppingCart
     */
    void update(ShoppingCart shoppingCart);

    /**
     * 新增购物车
     * @param shoppingCart
     */
    @Insert("insert into  shopping_cart(name, image, user_id, dish_id, setmeal_id, dish_flavor, amount, create_time) VALUES " +
            "(#{name}, #{image}, #{userId}, #{dishId}, #{setmealId}, #{dishFlavor}, #{amount}, #{createTime})")
    void save(ShoppingCart shoppingCart);

    /**
     * 清空购物车
     * @param userId
     */
    @Delete("delete from shopping_cart where user_id = #{userId}")
    void deleteByUserId(Long userId);

    /**
     * 根据userId查询购物车
     * @param userId
     * @return
     */
    @Select("select * from shopping_cart where user_id = #{userId}")
    List<ShoppingCart> getByUserId(Long userId);

    /**
     * 从购物车中减少商品
     * @param shoppingCart
     */
    void delete(ShoppingCart shoppingCart);
}
