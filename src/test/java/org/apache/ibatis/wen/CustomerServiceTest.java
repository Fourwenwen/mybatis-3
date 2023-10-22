package org.apache.ibatis.wen;

import com.google.common.collect.Lists;
import org.apache.ibatis.wen.domain.*;
import org.apache.ibatis.wen.service.CustomerService;
import org.apache.ibatis.wen.service.OrderService;
import org.apache.ibatis.wen.service.ProductService;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

public class CustomerServiceTest {

    private static CustomerService customerService;

    private static OrderService orderService;

    private static ProductService productService;

    @Before
    public void init() {
        customerService = new CustomerService();
        orderService = new OrderService();
        productService = new ProductService();
    }

    @Test
    public void test01() {
        // 创建一个用户
        long customerId = customerService.register("杨四正", "12345654321");
        // 为用户添加一个配送地址
        long addressId = customerService.addAddress(customerId,
            "牛栏村", "牛栏市", "矮人国");
        System.out.println(addressId);
        // 查询用户信息以及地址信息
        Customer customer = customerService.find(customerId);
        System.out.println(customer);
        Customer customer2 = customerService.findWithAddress(customerId);
        System.out.println(customer2);
        List<Address> addressList = customer2.getAddresses();
        addressList.stream().forEach(System.out::println);

        // 入库一些商品
        Product product = new Product();
        product.setName("MyBatis课程");
        product.setDescription("深入MyBatis源码的视频教程");
        product.setPrice(new BigDecimal(99));
        long productId = productService.createProduct(product);
        System.out.println("create productId:" + productId);

        // 创建一个订单
        Order order = new Order();
        order.setCustomer(customer); // 买家
        order.setDeliveryAddress(addressList.get(0)); // 配送地址
        order.setCreateTime(System.currentTimeMillis());
        // 生成购买条目
        OrderItem orderItem = new OrderItem();
        orderItem.setAmount(20);
        orderItem.setProduct(product);
        order.setOrderItems(Lists.newArrayList(orderItem));
        long orderId = orderService.createOrder(order);
        System.out.println("create orderId:" + orderId);

        Order order2 = orderService.find(orderId);
        System.out.println(order2);
    }

    @Test
    public void query(){
        int customerId = 2;
        Customer customer2 = customerService.findWithAddress(customerId);
        System.out.println(customer2);
        List<Address> addressList = customer2.getAddresses();
        addressList.stream().forEach(System.out::println);
    }

}
