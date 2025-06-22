package com.sky.controller.user;

import com.sky.context.BaseContext;
import com.sky.dto.AddressBookDTO;
import com.sky.result.Result;
import com.sky.service.AddressBookService;
import com.sky.vo.AddressBookVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/addressBook")
@Api(tags = "C端-地址管理")
@Slf4j
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    @PostMapping
    @ApiOperation("新增地址")
    public Result save(@RequestBody AddressBookDTO addressBooKDTO) {
        log.info("新增地址: {}", addressBooKDTO);
        addressBookService.save(addressBooKDTO);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("查询登录用户所有地址")
    public Result<List<AddressBookVO>> list() {
        log.info("查询登录用户所有地址");
        List<AddressBookVO> list = addressBookService.list(BaseContext.getCurrentId());
        return Result.success(list);
    }

    @GetMapping("/default")
    @ApiOperation("查询默认地址")
    public Result<AddressBookVO> listDefaultAddr() {
        log.info("查询默认地址");
        AddressBookVO addressBookVO = addressBookService.listDefaultAddr(BaseContext.getCurrentId());
        return Result.success(addressBookVO);
    }

    @PutMapping
    @ApiOperation("修改地址")
    public Result update(@RequestBody AddressBookDTO addressBookDTO) {
        log.info("修改地址: {}", addressBookDTO);
        addressBookService.update(addressBookDTO);
        return Result.success();
    }

    @DeleteMapping
    @ApiOperation("根据id删除地址")
    public Result deleteById(Long id) {
        log.info("根据id删除地址: {}", id);
        addressBookService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<AddressBookVO> getById(@PathVariable Long id) {
        log.info("根据id查询地址: {}", id);
        AddressBookVO addressBookVO = addressBookService.getById(id);
        return Result.success(addressBookVO);
    }

    @PutMapping("/default")
    @ApiOperation("设置默认地址")
    public Result setDefaultAddr(@RequestBody AddressBookDTO addressBookDTO) {
        log.info("设置默认地址: {}", addressBookDTO.getId());
        addressBookService.setDefaultAddr(addressBookDTO.getId().intValue());
        return Result.success();
    }

}
