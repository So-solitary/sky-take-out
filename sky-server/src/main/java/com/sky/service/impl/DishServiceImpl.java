package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.entity.Setmeal;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Slf4j
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    /**
     * 新增菜品
     * @param dishDTO
     */
    @Override
    public void save(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.save(dish);

        Long id = dish.getId();

        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && !flavors.isEmpty()) {
            flavors.forEach(flavor -> {
                flavor.setDishId(id);
            });
            dishFlavorMapper.insertBatch(flavors);
        }
    }

    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void deleteBatch(List<Integer> ids) {
        List<Dish> dishes = dishMapper.getByIds(ids);
        log.info("dishes: {}", dishes);
        for (Dish dish : dishes) {
            if (dish.getStatus() == StatusConstant.ENABLE) {
                throw new DeletionNotAllowedException(dish.getName() + "起售中，不能删除");
            }
        }

        for (Integer id : ids) {
            Setmeal setmeal = setmealMapper.getByDishId(id);
            Dish dish = dishMapper.getById(id);
            if (setmeal != null) {
                throw new DeletionNotAllowedException(dish.getName() + "存在于" + setmeal.getName() + "套餐中，不能删除");
            }
        }

        dishMapper.deleteByIds(ids);
        ArrayList<Long> dishIds = new ArrayList<>();
        for (Dish dish : dishes) {
            dishIds.add(dish.getId());
        }
        dishFlavorMapper.deleteByDishIds(dishIds);
    }

    /**
     * 根据ID查询菜品
     * @param id
     * @return
     */
    @Override
    public DishVO getByIdWithDishFlavors(Long id) {
        Dish dish = dishMapper.getById(id.intValue());
        List<DishFlavor> dishFlavor = dishFlavorMapper.getByDishId(id);
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
        dishVO.setFlavors(dishFlavor);
        return dishVO;
    }

    @Override
    public void update(DishDTO dishDTO) {

        // 变更口味表
        // 先将原本的菜品口味给删除
        List<DishFlavor> flavors = dishDTO.getFlavors();
        log.info("flavor: {}", flavors);
        if (flavors != null && !flavors.isEmpty()) {
            List<Long> list = new ArrayList<>();
            list.add(dishDTO.getId());
            dishFlavorMapper.deleteByDishIds(list);
        }
        flavors = dishDTO.getFlavors();
        flavors.forEach(dishFlavor -> dishFlavor.setDishId(dishDTO.getId()));
        dishFlavorMapper.insertBatch(flavors);

        // 变更菜品表
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.update(dish);
    }

    @Override
    public List<DishVO> getByCategoryId(Long categoryId) {
        List<DishVO> list = dishMapper.getByCategoryId(categoryId);
        return list;
    }

}
