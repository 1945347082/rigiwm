package com.example.rigiwm.dto;

import com.example.rigiwm.entity.Setmeal;
import com.example.rigiwm.entity.SetmealDish;
import lombok.Data;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    // 套餐分类id
    private Long categoryId;

    // 套餐分类名称
    private String categoryName;

    // 套餐菜品
    private List<SetmealDish> setmealDishes;

}
