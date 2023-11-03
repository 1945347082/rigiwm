package com.example.rigiwm.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.rigiwm.comment.R;
import com.example.rigiwm.entity.Category;
import com.example.rigiwm.service.ICategoryService;
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
 * 菜品及套餐分类 前端控制器
 * </p>
 *
 * @author liang gx
 * @since 2023-11-01
 */
@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Category category){
        log.info("新增菜品分类：{}", category);
        Long id = (Long)request.getSession().getAttribute("employee");
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        category.setCreateUser(id);
        category.setUpdateUser(id);
        boolean save = categoryService.save(category);
        if (save) return R.success("新增菜品分类成功");
        return R.error("新增菜品分类失败");
    }

    @GetMapping("/page")
    public R<Page<Category>> getCategoryList(Integer page, Integer pageSize){
        log.info("=======查询菜品分类========");
        Page<Category> categoryPage = new Page<>(page, pageSize);
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        return R.success(categoryService.page(categoryPage, wrapper));
    }

    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Category category){
        log.info("修改菜品分类：{}", category);
        Long id = (Long)request.getSession().getAttribute("employee");
        category.setUpdateTime(LocalDateTime.now());
        category.setUpdateUser(id);
        boolean b = categoryService.updateById(category);
        if (b) return R.success("修改成功");
        return R.error("修改失败");
    }

    @DeleteMapping
    public R<String> deleteCategory(String ids){
        if (StringUtils.isEmpty(ids)) return R.error("删除id不存在");
        String[] split = ids.split(",");
        List<String> list = Arrays.asList(split);
        boolean b = categoryService.removeByIds(list);
        if (b) return R.success("删除成功");
        return R.error("删除失败");
    }

    @GetMapping("/list")
    public R<List<Category>> getCatetoryListByType(HttpServletRequest request){
        String type = request.getParameter("type");
        log.info("菜品类型：{}", type);
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.eq("type", type);
        List<Category> list = categoryService.list(wrapper);
        if (list.isEmpty()) return R.error("当前没有对应菜单分类");
        return R.success(list);
    }


}
