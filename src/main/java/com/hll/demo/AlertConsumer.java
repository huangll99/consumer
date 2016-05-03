package com.hll.demo;

import com.hll.rabbit_consume_framework.RabbitHelper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

/**
 * Created by hll on 2016/5/3.
 * 告警消费者
 */
public class AlertConsumer {

  private static final String AMQP_EXCHANGE = "alerts";

  public static void main(String[] args) throws IOException {
    Channel channel = RabbitHelper.getChannel();
    channel.exchangeDeclare(AMQP_EXCHANGE, "topic", false, false, null);

    channel.queueDeclare("critical", false, false, false, null);
    channel.queueBind("critical", AMQP_EXCHANGE, "critical.*");

    channel.queueDeclare("rate_limit", false, false, false, null);
    channel.queueBind("rate_limit", AMQP_EXCHANGE, "*.rate_limit");

    channel.basicConsume("critical", false, new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        System.out.println("critical:" + new String(body));
      }
    });

    channel.basicConsume("rate_limit", false, new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        System.out.println("rate_limit:" + new String(body));
      }
    });
  }

}
