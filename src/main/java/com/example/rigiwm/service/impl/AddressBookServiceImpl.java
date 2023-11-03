package com.example.rigiwm.service.impl;

import com.example.rigiwm.entity.AddressBook;
import com.example.rigiwm.mapper.AddressBookMapper;
import com.example.rigiwm.service.IAddressBookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 地址管理 服务实现类
 * </p>
 *
 * @author liang gx
 * @since 2023-11-01
 */
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements IAddressBookService {

}
