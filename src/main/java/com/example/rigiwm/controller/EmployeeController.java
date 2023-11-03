package com.example.rigiwm.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.rigiwm.comment.R;
import com.example.rigiwm.entity.Employee;
import com.example.rigiwm.service.IEmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * <p>
 * 员工信息 前端控制器
 * </p>
 *
 * @author liang gx
 * @since 2023-11-01
 */
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee){
        log.info("新增员工信息：{}", employee);
        if (employee == null){
            return R.error("新增员工失败");
        }
        try{
            employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
            employee.setCreateTime(LocalDateTime.now());
            employee.setUpdateTime(LocalDateTime.now());
            employee.setCreateUser((Long) request.getSession().getAttribute("employee"));
            employee.setUpdateUser((Long) request.getSession().getAttribute("employee"));
            employeeService.save(employee);
        } catch (Exception e){
            e.printStackTrace();
        }
        return R.success("新增成功");
    }

    @GetMapping("/page")
    public R<Page<Employee>> getEmployeeList(Integer page, Integer pageSize, String name){
        log.info("查询员工列表: page = {},pageSize = {},name = {}", page, pageSize, name);
        Page<Employee> EmpPage = new Page<>(page, pageSize);
        QueryWrapper<Employee> wrapper = new QueryWrapper<>();
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(name)) {
            queryWrapper = wrapper.like("name", name).orderByDesc("update_time");
        }
        Page<Employee> employeePage = employeeService.page(EmpPage, queryWrapper);
        return R.success(employeePage);
    }

    @GetMapping("/{id}")
    public R<Employee> queryEmployeeById(@PathVariable Long id){
        log.info("当前员工id：{}", id);
        Employee employee = employeeService.getById(id);
        log.info("员工详情: {}", employee);
        return R.success(employee);
    }

    @PutMapping
    public R<String> editEmployee(@RequestBody Employee employee){
        log.info("员工详情：{}", employee);
        if (employee != null){
          employeeService.updateById(employee);
          return R.success("修改成功");
        }
        return R.error("修改失败");
    }





}
