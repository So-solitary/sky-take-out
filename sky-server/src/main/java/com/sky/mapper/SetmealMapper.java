package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SetmealMapper {

    /**
     * 根据菜品ID查询套餐
     * @param dishId
     * @return
     */
    @Select("select * from setmeal_dish where dish_id = #{dishId}")
    Setmeal getByDishId(Integer dishId);

    /**
     * 新增套餐
     * @param setmeal
     */
    @Insert("insert into setmeal(name, image, price, category_id, description, status, create_time, create_user, update_time, update_user) values " +
            "(#{name}, #{image}, #{price}, #{categoryId}, #{description}, #{status}, #{createTime}, #{createUser}, #{updateTime}, #{updateUser})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @AutoFill(value = OperationType.INSERT)
    void save(Setmeal setmeal);

    /**
     * 根据ID查询套餐
     * @param id
     * @return
     */
    @Select("select * from setmeal where id = #{id}")
    Setmeal getById(Long id);

    /**
     * 套餐分类分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    Page<SetmealVO> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);


    /**
     * 删除套餐
     * @param id
     */
    @Delete("delete from setmeal where id = #{id}")
    void deleteById(Long id);

    /**
     * 变更套餐
      * @param setmeal
     */
    @AutoFill(OperationType.UPDATE)
    void update(Setmeal setmeal);

    /**
     * 批量删除套餐
     * @param setmeal
     */
    List<SetmealVO> select(Setmeal setmeal);
}
