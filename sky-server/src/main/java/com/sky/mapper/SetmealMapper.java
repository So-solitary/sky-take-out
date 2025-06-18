package com.sky.mapper;

import com.sky.entity.Setmeal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SetmealMapper {

    /**
     * 根据菜品ID查询套餐
     * @param dishId
     * @return
     */
    @Select("select * from setmeal_dish where dish_id = #{dishId}")
    Setmeal getByDishId(Integer dishId);
}
