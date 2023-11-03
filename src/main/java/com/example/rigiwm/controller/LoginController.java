package com.example.rigiwm.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.rigiwm.comment.R;
import com.example.rigiwm.entity.Employee;
import com.example.rigiwm.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/employee")
public class LoginController {

    @Autowired
    private IEmployeeService employeeService;

    /**
     * 登录
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public R<Employee> handleLogin(HttpServletRequest request, @RequestBody Employee employee){
        // 用户登录密码加密
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        // 校验用户名
        QueryWrapper<Employee> wrapper = new QueryWrapper<>();
        wrapper.eq("username", employee.getUsername());
        Employee entity = employeeService.getOne(wrapper);
        if(entity == null){
            return R.error("用户名不存在");
        }
        // 校验密码
        if(!entity.getPassword().equals(password)){
            return R.error("密码错误");
        }
        // 校验用户状态 1 启用 0 禁用
        if(entity.getStatus() == 0){
            return R.error("该用户已禁用");
        }
        // 返回成功信息
        request.getSession().setAttribute("employee", entity.getId());
        return R.success(entity);

    }

    /**
     * 退出
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        // 删除回话
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

}
