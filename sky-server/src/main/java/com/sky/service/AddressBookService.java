package com.sky.service;

import com.sky.dto.AddressBookDTO;
import com.sky.vo.AddressBookVO;

import java.util.List;

public interface AddressBookService {

    /**
     * 新增地址
     * @param addressBooKDTO
     */
    void save(AddressBookDTO addressBooKDTO);

    /**
     * 查询登录用户所有地址
     * @param userId
     * @return
     */
    List<AddressBookVO> list(Long userId);

    /**
     * 查询默认地址
     * @param userId
     * @return
     */
    AddressBookVO listDefaultAddr(Long userId);

    /**
     * 修改地址
     * @param addressBookDTO
     */
    void update(AddressBookDTO addressBookDTO);

    /**
     * 根据id删除地址
     * @param id
     */
    void deleteById(Long id);

    /**
     * 设置默认地址
     * @param id
     */
    void setDefaultAddr(Integer id);

    /**
     * 根据id查询地址
     * @param id
     * @return
     */
    AddressBookVO getById(Long id);
}
