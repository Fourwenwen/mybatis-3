package org.apache.ibatis.wen.service;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.apache.ibatis.wen.DaoUtils;
import org.apache.ibatis.wen.dao.AddressMapper;
import org.apache.ibatis.wen.dao.CustomerMapper;
import org.apache.ibatis.wen.dao.OrderItemMapper;
import org.apache.ibatis.wen.dao.OrderMapper;
import org.apache.ibatis.wen.domain.Address;
import org.apache.ibatis.wen.domain.Customer;
import org.apache.ibatis.wen.domain.Order;
import org.apache.ibatis.wen.domain.OrderItem;

import java.util.List;

public class CustomerService {

    public long register(String name, String phone){
        Preconditions.checkArgument(!Strings.isNullOrEmpty(name),"name is empty");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(phone),"phone is empty");
        return DaoUtils.execute(sqlSession -> {
            CustomerMapper mapper = sqlSession.getMapper(CustomerMapper.class);
            Customer customer = new Customer();
            customer.setName(name);
            customer.setPhone(phone);
            int affected = mapper.save(customer);
            if (affected <= 0) {
                throw new RuntimeException("Save Customer fail...");
            }
            return customer.getId();
        });
    }

    // 用户添加一个新的送货地址
    public long addAddress(long customerId, String street, String city, String country) {
        // 检查传入的name参数以及phone参数是否合法
        Preconditions.checkArgument(customerId > 0, "customerId is empty");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(street), "street is empty");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(city), "city is empty");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(country), "country is empty");
        // 我们还可以完成其他业务逻辑，例如检查该地址是否超出了送货范围等，这里不再展示
        return DaoUtils.execute(sqlSession -> {
            // 创建Address对象并调用AddressMapper.save()方法完成持久化
            AddressMapper mapper = sqlSession.getMapper(AddressMapper.class);
            Address address = new Address();
            address.setStreet(street);
            address.setCity(city);
            address.setCountry(country);
            int affected = mapper.save(address, customerId);
            if (affected <= 0) {
                throw new RuntimeException("Save Customer fail...");
            }
            return address.getId();
        });
    }

    public Customer find(long customerId) {
        // 检查orderId参数是否合法
        Preconditions.checkArgument(customerId > 0, "customId error");
        return DaoUtils.execute(sqlSession -> {
            // 查询用户
            CustomerMapper customerMapper = sqlSession.getMapper(CustomerMapper.class);
            return customerMapper.find(customerId);
        });
    }

    public Customer findWithAddress(long customerId) {
        // 检查orderId参数是否合法
        Preconditions.checkArgument(customerId > 0, "customId error");
        return DaoUtils.execute(sqlSession -> {
            // 查询用户
            CustomerMapper customerMapper = sqlSession.getMapper(CustomerMapper.class);
            Customer customer = customerMapper.find(customerId);
            // 查询订单本身的信息
            AddressMapper addressMapper = sqlSession.getMapper(AddressMapper.class);
            List<Address> addressList = addressMapper.findAll(customerId);
            customer.setAddresses(addressList);
            return customer;
        });
    }

}
