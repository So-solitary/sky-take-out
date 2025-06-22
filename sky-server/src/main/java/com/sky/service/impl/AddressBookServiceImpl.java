package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.AddressBookDTO;
import com.sky.entity.AddressBook;
import com.sky.mapper.AddressBookMapper;
import com.sky.service.AddressBookService;
import com.sky.vo.AddressBookVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressBookServiceImpl implements AddressBookService {

    @Autowired
    private AddressBookMapper addressBookMapper;

    /**
     * 新增地址
     * @param addressBooKDTO
     */
    @Override
    public void save(AddressBookDTO addressBooKDTO) {
        if (addressBooKDTO.getIsDefault() == null) {
            addressBooKDTO.setIsDefault(0);
        }
        addressBooKDTO.setUserId(BaseContext.getCurrentId());
        addressBookMapper.save(addressBooKDTO);
    }

    /**
     * 查询登录用户所有地址
     * @param userId
     * @return
     */
    @Override
    public List<AddressBookVO> list(Long userId) {
        AddressBook addressBook = AddressBook.builder()
                .userId(userId)
                .build();

        List<AddressBookVO> list = addressBookMapper.list(addressBook);
        return list;
    }

    /**
     * 查询默认地址
     * @param userId
     * @return
     */
    @Override
    public AddressBookVO listDefaultAddr(Long userId) {
        AddressBook addressBook = AddressBook.builder()
                .userId(userId)
                .isDefault(1)
                .build();
        List<AddressBookVO> list = addressBookMapper.list(addressBook);
        return list.get(0);
    }

    /**
     * 修改地址
     * @param addressBookDTO
     */
    @Override
    public void update(AddressBookDTO addressBookDTO) {
        AddressBook addressBook = new AddressBook();
        BeanUtils.copyProperties(addressBookDTO, addressBook);
        addressBookMapper.update(addressBook);
    }

    /**
     * 根据id删除地址
     * @param id
     */
    @Override
    public void deleteById(Long id) {
        addressBookMapper.deleteById(id);
    }

    /**
     * 设置默认地址
     * @param id
     */
    @Override
    @Transactional
    public void setDefaultAddr(Integer id) {
        // 先将用户之前的地址全部修改为非默认地址
        AddressBook addressBook = AddressBook.builder()
                .isDefault(0)
                .userId(BaseContext.getCurrentId())
                .build();
        addressBookMapper.update(addressBook);

        addressBook.setId(id.longValue());
        addressBook.setIsDefault(1);
        addressBookMapper.update(addressBook);
    }

    /**
     * 根据id查询地址
     * @param id
     * @return
     */
    @Override
    public AddressBookVO getById(Long id) {
        return addressBookMapper.getById(id);
    }


}
