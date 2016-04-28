package com.hll.rabbit_consume_framework;

import com.rabbitmq.client.Channel;

/**
 * Created by hll on 2016/4/28.
 * 消费接口，实现这个接口，消费RabbitMQ消息
 */
public interface RabbitConsumer {
  /**
   * rabbitmq 信道
   * @param channel
   */
  void consume(Channel channel);
}
