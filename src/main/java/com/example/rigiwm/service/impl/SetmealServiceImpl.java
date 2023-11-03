package com.example.rigiwm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.rigiwm.comment.R;
import com.example.rigiwm.dto.SetmealDto;
import com.example.rigiwm.entity.Category;
import com.example.rigiwm.entity.Setmeal;
import com.example.rigiwm.entity.SetmealDish;
import com.example.rigiwm.mapper.CategoryMapper;
import com.example.rigiwm.mapper.SetmealDishMapper;
import com.example.rigiwm.mapper.SetmealMapper;
import com.example.rigiwm.service.ISetmealService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 套餐 服务实现类
 * </p>
 *
 * @author liang gx
 * @since 2023-11-01
 */
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements ISetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;


    @Override
    public Page<SetmealDto> getSetmealList(Integer page, Integer pageSize, String name) {
        // 获取套餐列表
        QueryWrapper<Setmeal> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(name)) wrapper.like("name", name);
        List<Setmeal> setmealList = setmealMapper.selectList(wrapper);
        // 封装套餐列表数据
        if (setmealList.isEmpty()) return new Page<>();
        ArrayList<SetmealDto> dtos = new ArrayList<>();
        setmealList.forEach(setmeal -> {
            SetmealDto setmealDto = new SetmealDto();
            String categoryName = categoryMapper.selectById(setmeal.getCategoryId()).getName();
            BeanUtils.copyProperties(setmeal, setmealDto);
            // 套餐菜品
            List<SetmealDish> list = getSetmealDishBySetmealId(setmeal.getId());
            setmealDto.setSetmealDishes(list);
            setmealDto.setCategoryName(categoryName);
            dtos.add(setmealDto);
        });
        Page<SetmealDto> dtoPage = new Page<>(page, pageSize);
        dtoPage.setRecords(dtos);
        return dtoPage;
    }

    @Override
    public SetmealDto getSetmealById(String id) {
        Setmeal setmeal = setmealMapper.selectById(id);
        Category category = categoryMapper.selectById(setmeal.getCategoryId());
        // 套餐菜品
        List<SetmealDish> list = getSetmealDishBySetmealId(setmeal.getId());
        SetmealDto setmealDto = new SetmealDto();
        BeanUtils.copyProperties(setmeal, setmealDto);
        setmealDto.setSetmealDishes(list);
        setmealDto.setCategoryName(category.getName());
        return setmealDto;
    }

    @Override
    public Boolean updateByIds(List<String> list, Integer status, Long id) {
        if (list.isEmpty()) return false;
        List<Setmeal> setmealList = setmealMapper.selectBatchIds(list);
        if (setmealList.isEmpty()) return false;
        setmealList.forEach(setmeal -> {
            setmeal.setStatus(status);
            setmeal.setUpdateTime(LocalDateTime.now());
            setmeal.setUpdateUser(id);
            setmealMapper.updateById(setmeal);
        });
        return true;
    }

    @Override
    public Boolean addSetmeal(Long id, SetmealDto setmealDto) {
        // 套餐名称唯一性校验
        Boolean aBoolean = validateSetmealName(setmealDto.getName());
        if (!aBoolean) return false;
        // 套餐表新增数据
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDto, setmeal);
        setmeal.setCreateUser(id);
        setmeal.setUpdateUser(id);
        setmeal.setCreateTime(LocalDateTime.now());
        setmeal.setUpdateTime(LocalDateTime.now());
        int insert = setmealMapper.insert(setmeal);
        // 套餐菜品表新增数据
        List<SetmealDish> setmealDishList = setmealDto.getSetmealDishes();
        if (setmealDto.getSetmealDishes().isEmpty()) return false;
        setmealDishList.forEach(setmealDish -> {
            setmealDish.setSetmealId(String.valueOf(setmeal.getId()));
            setmealDish.setSort(0);
            setmealDish.setCreateTime(LocalDateTime.now());
            setmealDish.setUpdateTime(LocalDateTime.now());
            setmealDish.setCreateUser(id);
            setmealDish.setUpdateUser(id);
            setmealDish.setIsDeleted(0);
            setmealDishMapper.insert(setmealDish);
        });

        return true;
    }

    @Override
    public R<String> updateSetmeal(Long id, SetmealDto setmealDto) {
        // 套餐表更新数据
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDto, setmeal);
        setmeal.setUpdateUser(id);
        setmeal.setUpdateTime(LocalDateTime.now());
        int b = setmealMapper.updateById(setmeal);
        // 删除套餐菜品表数据，然后新增
        QueryWrapper<SetmealDish> wrapper = new QueryWrapper<>();
        wrapper.eq("setmeal_id", setmeal.getId());
        setmealDishMapper.delete(wrapper);
        setmealDto.getSetmealDishes().forEach(setmealDish -> {
            setmealDish.setSetmealId(String.valueOf(setmeal.getId()));
            setmealDish.setSort(0);
            setmealDish.setCreateTime(LocalDateTime.now());
            setmealDish.setUpdateTime(LocalDateTime.now());
            setmealDish.setCreateUser(id);
            setmealDish.setUpdateUser(id);
            setmealDish.setIsDeleted(0);
            setmealDishMapper.insert(setmealDish);
        });
        return R.success("修改成功");
    }

    public List<SetmealDish> getSetmealDishBySetmealId(Long setmealId){
        QueryWrapper<SetmealDish> wrapper = new QueryWrapper<>();
        wrapper.eq("setmeal_id", setmealId);
        List<SetmealDish> list = setmealDishMapper.selectList(wrapper);
        if (list.isEmpty()) return new ArrayList<>();
        return list;
    }

    /**
     * 校验套餐名称 唯一性
     *
     * @param name
     * @return
     */
    Boolean validateSetmealName(String name){
        if (StringUtils.isEmpty(name)) return false;
        QueryWrapper<Setmeal> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        Setmeal setmeal = setmealMapper.selectOne(wrapper);
        return setmeal == null;
    }

}
