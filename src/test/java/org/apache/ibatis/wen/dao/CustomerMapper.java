package org.apache.ibatis.wen.dao;

import org.apache.ibatis.wen.domain.Customer;

public interface CustomerMapper {

  // 根据用户Id查询Customer(不查询Address)

  Customer find(long id);

  // 根据用户Id查询Customer(同时查询Address)

  Customer findWithAddress(long id);

  // 根据orderId查询Customer

  Customer findByOrderId(long orderId);

  // 持久化Customer对象

  int save(Customer customer);

}
