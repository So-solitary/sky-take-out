package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.SetmealEnableFailedException;
import com.sky.exception.SetmealNeedUniqueException;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.CategoryVO;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;
    @Autowired
    private DishMapper dishMapper;

    /**
     * 新增套餐
     * @param setmealDTO
     */
    @Override
    public void saveWithDish(@RequestBody SetmealDTO setmealDTO) {
        // 套餐名称唯一
        Setmeal setmeal = setmealMapper.getById(setmealDTO.getId());
        if (setmeal != null && setmeal.getName().equals(setmealDTO.getName())) {
            throw new SetmealNeedUniqueException("套餐名称需要唯一");
        }
        // 套餐必须属于某个分类
        Long categoryId = setmealDTO.getCategoryId();
        Category category = categoryMapper.getById(categoryId);
        if (category == null) {
            throw new SetmealEnableFailedException("套餐必须属于某个分类");
        }
        // 套餐必须包含菜品
        if (setmealDTO.getSetmealDishes().isEmpty()) {
            throw new SetmealEnableFailedException("套餐必须包含菜品");
        }
        // 名称、分类、价格、图片为必填项
        if (setmealDTO.getName() == null ||
                setmealDTO.getCategoryId() == null ||
                setmealDTO.getPrice() == null ||
                setmealDTO.getImage() == null) {
            throw new SetmealEnableFailedException("名称、分类、价格、图片为必填项");
        }
        // 添加菜品窗口需要根据分类类型来展示菜品
        // 新增的套餐默认为停售状态
        Setmeal sm = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, sm);
        sm.setStatus(StatusConstant.DISABLE);
        setmealMapper.save(sm);
        Long id = sm.getId();
        setmealDTO.getSetmealDishes().forEach(dish -> dish.setSetmealId(id));
        setmealDishMapper.insertBatch(setmealDTO.getSetmealDishes());

    }

    @Override
    public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {

        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
        Page<SetmealVO> page = setmealMapper.pageQuery(setmealPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void deleteBatch(List<Long> ids) {
        ids.forEach(id -> {
            Setmeal setmeal = setmealMapper.getById(id);
            if (setmeal.getStatus() == StatusConstant.ENABLE) {
                throw new SetmealEnableFailedException("起售中的套餐不能删除");
            }
        });
        ids.forEach(id -> {
            setmealMapper.deleteById(id);
            Setmeal setmeal = setmealMapper.getById(id);
            if (setmeal != null) {
                setmealDishMapper.deleteBySetmealId(id);
            }
        });

    }

    /**
     * 根据id查询套餐和菜品信息
     * @param id
     * @return
     */
    @Override
    public SetmealVO getWithDishesByid(Long id) {
        Setmeal setmeal = setmealMapper.getById(id);
        List<SetmealDish> list = setmealDishMapper.getBySetmealId(id);
        SetmealVO setmealVO = new SetmealVO();
        BeanUtils.copyProperties(setmeal, setmealVO);
        setmealVO.setSetmealDishes(list);
        return setmealVO;
    }

    @Override
    public void startOrStop(Integer status, Long id) {
        Setmeal setmeal = Setmeal.builder()
                .status(status)
                .id(id)
                .build();
        setmealMapper.update(setmeal);
    }

    /**
     * 根据分类id查询套餐
     * @param categoryId
     * @return
     */
    @Override
    public List<SetmealVO> getByCategoryId(Long categoryId) {
        Setmeal setmeal = Setmeal.builder()
                .categoryId(categoryId)
                .status(StatusConstant.ENABLE)
                .build();
        List<SetmealVO> list = setmealMapper.select(setmeal);
        return list;
    }

    /**
     * 根据套餐id查询包含的菜品
     * @param id
     * @return
     */
    @Override
    public List<DishItemVO> getBySetmealId(Long id) {
        List<SetmealDish> setmealDishes = setmealDishMapper.getBySetmealId(id);
        List<DishItemVO> list = new ArrayList<>();
        for (SetmealDish setmealDish : setmealDishes) {
            Dish dish = dishMapper.getById(setmealDish.getId().intValue());
            DishItemVO dishItemVO = new DishItemVO();
            BeanUtils.copyProperties(setmealDish, dishItemVO);
            dishItemVO.setImage(dish.getImage());
        }
        return list;
    }


}
