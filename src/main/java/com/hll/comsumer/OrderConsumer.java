package com.hll.comsumer;

import com.alibaba.fastjson.JSON;
import com.hll.model.Order;
import com.hll.rabbit_consume_framework.Consumer;
import com.hll.rabbit_consume_framework.RabbitConsumer;
import com.hll.service.OrderService;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * Created by hll on 2016/4/28.
 */
@Consumer
public class OrderConsumer implements RabbitConsumer {

  @Autowired
  OrderService orderService;

  @Override
  public void consume(Channel channel) {
    try {
      channel.queueDeclare("order", false, false, false, null);
      com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel) {
        @Override
        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
          System.out.println("consume11111111111............");
          Order order = JSON.parseObject(body, Order.class);
          orderService.printOrder(order);
        }
      };
      channel.basicConsume("order", true, consumer);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
