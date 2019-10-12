package com.itheima.dao;

import com.itheima.domain.Member;
import com.itheima.domain.Orders;
import com.itheima.domain.Product;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDao {

    //查询所有
    @Select("select * from orders")
    @Results({@Result(column = "orderNum",property = "orderNum"),
            @Result(column = "orderTime",property = "orderTime"),
            @Result(column = "orderStatus",property = "orderStatus"),
            @Result(column = "peopleCount",property = "peopleCount"),
            @Result(column = "payType",property = "payType"),
            @Result(column = "orderDesc",property = "orderDesc"),
            @Result(column = "productId",property = "product",javaType = Product.class,one = @One(select =
                    "com.itheima.dao.ProductDao.findById"))
    })
    List<Orders> findAll();





    //根据Id查询
    @Select("select * from orders where id =#{ordersId}")
    @Results({@Result(column = "orderNum",property = "orderNum"),
            @Result(column = "orderTime",property = "orderTime"),
            @Result(column = "orderStatus",property = "orderStatus"),
            @Result(column = "peopleCount",property = "peopleCount"),
            @Result(column = "payType",property = "payType"),
            @Result(column = "orderDesc",property = "orderDesc"),
            @Result(column = "productId",property = "product",one = @One(select =
                    "com.itheima.dao.ProductDao.findById")),
            @Result(column = "memberId",property = "member",javaType = Member.class,one = @One(select =
                    "com.itheima.dao.MemberDao.findById")),
            @Result(column = "id",property = "travellers",javaType =java.util.List.class,many = @Many(select =
                    "com.itheima.dao.TravellerDao.findByOrdersId"))
    })
    Orders findById(String ordersId);
}
