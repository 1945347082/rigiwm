package com.example.rigiwm.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.rigiwm.comment.R;
import com.example.rigiwm.dto.SetmealDto;
import com.example.rigiwm.entity.Setmeal;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 套餐 服务类
 * </p>
 *
 * @author liang gx
 * @since 2023-11-01
 */
public interface ISetmealService extends IService<Setmeal> {

    Page<SetmealDto> getSetmealList(Integer page, Integer pageSize, String name);

    SetmealDto getSetmealById(String id);

    Boolean updateByIds(List<String> list, Integer status, Long id);

    Boolean addSetmeal(Long id, SetmealDto setmealDto);

    R<String> updateSetmeal(Long id, SetmealDto setmealDto);
}
