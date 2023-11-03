package com.example.rigiwm.service.impl;

import com.example.rigiwm.entity.Category;
import com.example.rigiwm.mapper.CategoryMapper;
import com.example.rigiwm.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜品及套餐分类 服务实现类
 * </p>
 *
 * @author liang gx
 * @since 2023-11-01
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

}
