package com.example.rigiwm.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.rigiwm.dto.SetmealDto;
import com.example.rigiwm.entity.Setmeal;
import com.example.rigiwm.entity.SetmealDish;
import com.example.rigiwm.mapper.CategoryMapper;
import com.example.rigiwm.mapper.SetmealDishMapper;
import com.example.rigiwm.mapper.SetmealMapper;
import com.example.rigiwm.service.ISetmealDishService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 套餐菜品关系 服务实现类
 * </p>
 *
 * @author liang gx
 * @since 2023-11-01
 */
@Service
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper, SetmealDish> implements ISetmealDishService {


}
