package com.itheima.service.impl;

import com.github.pagehelper.PageHelper;
import com.itheima.dao.OrderDao;
import com.itheima.domain.Orders;
import com.itheima.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    OrderDao orderDao;
    public List<Orders> findAll(int page,int size) throws Exception {
        PageHelper.startPage(page,size);
        return orderDao.findAll();
    }

    public Orders findById(String ordersId) throws Exception {
        return orderDao.findById(ordersId);
    }
}
