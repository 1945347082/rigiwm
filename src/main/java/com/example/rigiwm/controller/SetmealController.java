package com.example.rigiwm.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.rigiwm.comment.R;
import com.example.rigiwm.dto.SetmealDto;
import com.example.rigiwm.entity.Setmeal;
import com.example.rigiwm.service.ISetmealDishService;
import com.example.rigiwm.service.ISetmealService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 套餐 前端控制器
 * </p>
 *
 * @author liang gx
 * @since 2023-11-01
 */

@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private ISetmealService setmealService;

    @GetMapping("/page")
    public R<Page<SetmealDto>> getSetmealList(Integer page, Integer pageSize, String name){
        log.info("套餐查询：page: {}, pageSize: {}, name: {}", page, pageSize, name);
        Page<SetmealDto> dtoPage = setmealService.getSetmealList(page, pageSize, name);
        return R.success(dtoPage);
    }

    @DeleteMapping
    public R<String> deleteSetmealByIds(String ids){
        log.info("删除套餐：{}", ids);
        if (StringUtils.isEmpty(ids)) return R.error("当前套餐id不可以为空");
        List<String> list = Arrays.asList(ids.split(","));
        boolean b = setmealService.removeByIds(list);
        if (b) return R.success("删除成功");
        return R.error("删除失败");
    }

    @PostMapping
    public R<String> addSetmeal(HttpServletRequest request, @RequestBody SetmealDto setmealDto){
        log.info("新增套餐：{}", setmealDto);
//        Long id = (Long)request.getSession().getAttribute("employee");
        Long  id = 1L;
        Boolean save = setmealService.addSetmeal(id, setmealDto);
        if (save) return R.success("新增成功");
        return R.error("套餐新增失败");
    }

    @GetMapping("/{id}")
    public R<SetmealDto> querySetmealById(@PathVariable("id") String id){
        log.info("查询套餐详情：{}", id);
        if (StringUtils.isEmpty(id)) return R.error("套餐id不可为空");
        SetmealDto dto = setmealService.getSetmealById(id);
        if (dto == null) return R.error("套餐不存在");
        return R.success(dto);
    }

    @PostMapping("/status/{status}")
    public R<String> setmealStatusByStatus(HttpServletRequest request, @PathVariable("status") Integer status, String ids){
        log.info("禁用套餐： status: {}, ids: {}", status, ids);
        List<String> list = Arrays.asList(ids.split(","));
        Long id = (Long)request.getSession().getAttribute("employee");
        Boolean aBoolean = setmealService.updateByIds(list, status, id);
        if (aBoolean) return R.success("修改成功");
        return R.error("修改失败");
    }

    @PutMapping
    public R<String> editSetmeal(HttpServletRequest request, @RequestBody SetmealDto setmealDto){
        log.info("修改套餐：{}", setmealDto);
//        Long id = (Long)request.getSession().getAttribute("employee");
        Long  id = 1L;
        return setmealService.updateSetmeal(id, setmealDto);
    }




}
