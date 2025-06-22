package com.sky.controller.user;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;
import com.sky.vo.ShoppingCartVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/shoppingCart")
@Api(tags = "C端-购物车")
@Slf4j
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    @ApiOperation("添加购物车")
    public Result addShoppingCart(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("添加购物车: {}", shoppingCartDTO);
        shoppingCartService.addShoppingCart(shoppingCartDTO);
        return Result.success();
    }

    @PostMapping("/sub")
    @ApiOperation("从购物车删除商品")
    public Result subShoppingCart(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("从购物车删除商品: {}", shoppingCartDTO);
        shoppingCartService.subShoppingCart(shoppingCartDTO);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("查看购物车")
    public Result<List<ShoppingCartVO>> list() {
        log.info("查看购物车");
        List<ShoppingCartVO> list = shoppingCartService.list();
        return Result.success(list);
    }

    @DeleteMapping("/clean")
    @ApiOperation("清空购物车")
    public Result clean() {
        log.info("清空购物车");
        shoppingCartService.deleteByUserId(BaseContext.getCurrentId());
        return Result.success();
    }


}
