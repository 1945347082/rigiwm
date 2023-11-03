package com.example.rigiwm.service.impl;

import com.example.rigiwm.entity.Orders;
import com.example.rigiwm.mapper.OrdersMapper;
import com.example.rigiwm.service.IOrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author liang gx
 * @since 2023-11-01
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements IOrdersService {

}
