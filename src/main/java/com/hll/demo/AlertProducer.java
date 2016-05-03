package com.hll.demo;

import com.hll.rabbit_consume_framework.RabbitHelper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by hll on 2016/5/3.
 * 告警生产者
 */
public class AlertProducer {
  private static final String AMQP_EXCHANGE = "alerts";

  public static void main(String[] args) throws IOException, TimeoutException {
    Channel channel = RabbitHelper.getChannel();
    channel.basicPublish(AMQP_EXCHANGE,"vv.rate_limit",new AMQP.BasicProperties().builder().contentType("apllication/json").build(),"alert...".getBytes());
    RabbitHelper.close();
  }
}
