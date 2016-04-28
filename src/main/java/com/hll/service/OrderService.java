package com.hll.service;

import com.hll.model.Order;
import org.springframework.stereotype.Service;

/**
 * Created by hll on 2016/4/28.
 */
@Service
public class OrderService {

  public void printOrder(Order order) {
    System.out.println(order);
  }
}
