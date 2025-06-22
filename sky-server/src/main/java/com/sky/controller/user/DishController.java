package com.sky.controller.user;

import com.sky.constant.StatusConstant;
import com.sky.entity.Dish;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController("userDishController")
@RequestMapping("/user/dish")
@Api(tags = "C端-菜品接口")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    public Result list(Long categoryId) {
        log.info("根据分类id查询菜品: {}", categoryId);

        String dishKey = "dish_" + categoryId;

        List<DishVO> redisList = (List<DishVO>)redisTemplate.opsForValue().get(dishKey);
        if(redisList != null && !redisList.isEmpty()) {
            return Result.success(redisList);
        }
        Dish dish = Dish.builder()
                .categoryId(categoryId)
                .status(StatusConstant.ENABLE)
                .build();

        List<DishVO> list = dishService.listWithFlavors(dish);
        redisTemplate.opsForValue().set(dishKey, list);

        return Result.success(list);
    }

}
