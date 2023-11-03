package com.example.rigiwm.service.impl;

import com.example.rigiwm.entity.User;
import com.example.rigiwm.mapper.UserMapper;
import com.example.rigiwm.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息 服务实现类
 * </p>
 *
 * @author liang gx
 * @since 2023-11-01
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
