package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DishMapper {


    /**
     * 新增菜品
     * @param dish
     */
    @Insert("insert into dish(id , image, name, price, category_id, description, status, create_time, create_user, update_time, update_user) " +
            "values (#{id}, #{image}, #{name}, #{price}, #{categoryId}, #{description}, #{status}, #{createTime},#{createUser}, #{updateTime}, #{updateUser})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @AutoFill(value = OperationType.INSERT)
    void save(Dish dish);

    /**
     * 菜品分类分页查询
     * @param dishPageQueryDTO
     * @return
     */
    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 根据id列表查找
     * @param ids
     * @return
     */
    List<Dish> getByIds(List<Integer> ids);

    @Select("select * from dish where id = #{id}")
    Dish getById(Integer id);

    void deleteByIds(List<Integer> ids);

    /**
     * 修改菜品
     * @param dish
     */
    void update(Dish dish);

    @Select("select * from dish where category_id = #{categoryId}")
    List<DishVO> getByCategoryId(Long categoryId);
}
