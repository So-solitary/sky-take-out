package com.sky.service.impl;

import com.sky.entity.Setmeal;
import com.sky.mapper.SetmealMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Mapper
public class SetmealMapperImpl implements SetmealMapper {

    @Autowired
    private SetmealMapper setmealMapper;

    @Override
    public Setmeal getByDishId(Integer dishId) {
        return setmealMapper.getByDishId(dishId);
    }
}
