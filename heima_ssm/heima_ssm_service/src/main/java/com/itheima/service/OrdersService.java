package com.itheima.service;

import com.itheima.domain.Orders;

import java.util.List;

public interface OrdersService {
    List<Orders> findAll(int page, int size)throws Exception;

    Orders findById(String ordersId) throws  Exception;
}
