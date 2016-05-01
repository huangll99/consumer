package com.hll.demo;

import com.hll.rabbit_consume_framework.RabbitHelper;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * Created by hll on 2016/5/1.
 */
public class HelloConsumer {
  public static void main(String[] args) throws IOException {
    Channel channel = RabbitHelper.getChannel();
    channel.exchangeDeclare("hello-exchange", "direct", true, false, null);
    channel.queueDeclare("hello-queue", true, false, false, null);
    channel.queueBind("hello-queue", "hello-exchange", "hola");
    channel.basicConsume("hello-queue", false, new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        getChannel().basicAck(envelope.getDeliveryTag(), false);
        System.out.println(new String(body));
      }
    });
  }
}
