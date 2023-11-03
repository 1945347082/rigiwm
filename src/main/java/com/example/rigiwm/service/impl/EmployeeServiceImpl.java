package com.example.rigiwm.service.impl;

import com.example.rigiwm.entity.Employee;
import com.example.rigiwm.mapper.EmployeeMapper;
import com.example.rigiwm.service.IEmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 员工信息 服务实现类
 * </p>
 *
 * @author liang gx
 * @since 2023-11-01
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

}
