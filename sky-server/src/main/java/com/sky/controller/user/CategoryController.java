package com.sky.controller.user;

import com.sky.result.Result;
import com.sky.service.CategoryService;
import com.sky.vo.CategoryVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userCategoryController")
@RequestMapping("/user/category")
@Api(tags = "C端-分类接口")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    @ApiOperation("查询分类")
    public Result<List<CategoryVO>> list(Integer type) {
        log.info("查询分类: {}", type);
        List<CategoryVO> list = categoryService.list(type);
        return Result.success(list);
    }

}
