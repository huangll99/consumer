package com.hll.produce;

import com.hll.model.Order;
import com.hll.rabbit_consume_framework.RabbitHelper;
import com.rabbitmq.client.Channel;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by hll on 2016/4/28.
 */
@Component
public class ProducerService {

  public void sendOrderMsg(Order order) {
    try {
      Channel channel = RabbitHelper.getChannel();
      channel.queueDeclare("order", false, false, false, null);
      channel.basicPublish("", "order", null, JsonUtil.toJson(order).getBytes());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
