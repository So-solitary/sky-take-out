package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.enumeration.OperationType;
import com.sky.vo.CategoryVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {

    /**
     * 新增用户
     * @param category
     */
    @Insert("insert into category(id, name, sort, type, status, create_time, update_time, create_user, update_user)" +
            "values (#{id}, #{name},#{sort}, #{type},#{status}, #{createTime},#{updateTime}, #{createUser},#{updateUser})")
    @AutoFill(value = OperationType.INSERT)
    void save(Category category);

    Page<CategoryVO> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    @Delete("delete from category where id = #{id}")
    void removeById(Long id);

    /**
     * 修改分类
     * @param category
     */
    @AutoFill(OperationType.UPDATE)
    void update(Category category);

    @Select("select * from category where type = #{type}")
    List<CategoryVO> list(Integer type);
}
