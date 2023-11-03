package com.example.rigiwm.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.rigiwm.comment.R;
import com.example.rigiwm.entity.Dish;
import com.example.rigiwm.service.IDishService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 菜品管理 前端控制器
 * </p>
 *
 * @author liang gx
 * @since 2023-11-01
 */
@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private IDishService dishService;

    @GetMapping("/page")
    public R<Page<Dish>> getDishList(Integer page, Integer pageSize, String name) {
        log.info("查询菜品列表: page:{} ,pageSize:{} ,name:{}", page, pageSize, name);
        Page<Dish> dishPage = new Page<>(page, pageSize);
        QueryWrapper<Dish> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(name)) wrapper.like("name", name);
       dishService.page(dishPage, wrapper);
        return R.success(dishPage);
    }

    @PostMapping
    public R<String> addDish(HttpServletRequest request, @RequestBody Dish dish){
        log.info("新增菜单：request: {}, dist:{}", request, dish);
        Long id = (Long)request.getSession().getAttribute("employee");
        dish.setCode("222222");
        dish.setSort(1);
        dish.setIsDeleted(1);
        dish.setCreateTime(LocalDateTime.now());
        dish.setUpdateTime(LocalDateTime.now());
        dish.setCreateUser(id);
        dish.setUpdateUser(id);
        boolean b = dishService.save(dish);
        if (b) return R.success("新增菜品成功");
        return R.error("新增菜品失败");
    }

    @PutMapping
    public R<String> updateDish(HttpServletRequest request, @RequestBody Dish dish){
        log.info("修改菜品：{}", dish);
        Long id = (Long)request.getSession().getAttribute("employee");
        dish.setUpdateUser(id);
        dish.setUpdateTime(LocalDateTime.now());
        boolean b = dishService.updateById(dish);
        if (b) return R.success("修改成功");
        return R.error("修改失败");
    }

    @DeleteMapping
    public R<String> deleteDish(String ids){
        log.info("删除菜品：{}", ids);
        if (StringUtils.isEmpty(ids)) return R.error("菜品id不可为空");
        String[] split = ids.split(",");
        List<String> list = Arrays.asList(split);
        boolean b = dishService.removeByIds(list);
        if (b) return R.success("删除菜品成功");
        return R.error("删除菜品失败");
    }

    @PostMapping("/status/{status}")
    public R<String> dishStartOrStop(HttpServletRequest request, @PathVariable("status") Integer status, String ids){
        log.info("起售或停手菜品：{}", request);
        // 获取起售或停手状态 菜品id
//        String sta = request.getParameter("status");
//        if (StringUtils.isEmpty(sta)) return R.error("状态不可以为空");
//        Integer status = Integer.valueOf(sta);
//        String ids = request.getParameter("ids");
        if (StringUtils.isEmpty(ids)) return R.error("菜品id不可为空");
        List<String> idList = Arrays.asList(ids.split(","));
        // 获取对应数据
        List<Dish> dishes = dishService.listByIds(idList);
        Long id = (Long)request.getSession().getAttribute("employee");
        for (Dish dish : dishes) {
            dish.setUpdateTime(LocalDateTime.now());
            dish.setUpdateUser(id);
            dish.setStatus(status);
            dishService.updateById(dish);
        }
        return R.success("修改成功");
    }

    @GetMapping("/{id}")
    public R<Dish> getDishDetail(@PathVariable("id") Long id){
        log.info("菜品id: {}", id);
        Dish dish = dishService.getById(id);
        return R.success(dish);
    }

    @GetMapping("/list")
    public R<List<Dish>> getDishDetailByCategoryId(Long categoryId){
        log.info("菜品分类id: {}", categoryId);
        QueryWrapper<Dish> wrapper = new QueryWrapper<>();
        if (categoryId == null) return R.error("categoryId不可为空");
        wrapper.eq("category_id", categoryId);
        List<Dish> dishList = dishService.list(wrapper);
        return R.success(dishList);
    }


}
