package com.sky.controller.user;

import com.sky.entity.Category;
import com.sky.entity.Setmeal;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.CategoryVO;
import com.sky.vo.DishItemVO;
import com.sky.vo.DishVO;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController("userSet,ealController")
@RequestMapping("/user/setmeal")
@Api(tags = "C端-套餐接口")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @GetMapping("/list")
    @ApiOperation("根据分类id查询套餐")
    @Cacheable(value = "setmealCache", key = "#categoryId") // key: setmealCache::100
    public Result<List<SetmealVO>> listByCategory(Long categoryId) {
        log.info("根据分类id查询套餐: {}", categoryId);
        List<SetmealVO> list = setmealService.getByCategoryId(categoryId);
        return Result.success(list);
    }

    @GetMapping("/dish/{id}")
    @ApiOperation("根据套餐id查询包含的菜品")
    public Result<List<DishItemVO>> getBySetmealId(@PathVariable Long id) {
        log.info("根据套餐id查询包含的菜品: {}", id);
        List<DishItemVO> list = setmealService.getBySetmealId(id);
        return Result.success(list);
    }
}
