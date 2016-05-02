package com.hll.monitor;

import com.hll.rabbit_consume_framework.RabbitHelper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

/**
 * Created by hll on 2016/5/2.
 * 监控rabbitmq的脚本
 */
public class RabbitMonitor {
  public static void main(String[] args) throws IOException {
    Channel channel = RabbitHelper.getChannel();
    String errorQueue = channel.queueDeclare().getQueue();//声明三个队列，不指定名称，由rabbit生成随机名称
    String warningQueue = channel.queueDeclare().getQueue();
    String infoQueue = channel.queueDeclare().getQueue();

    //channel.exchangeDeclare("amq.rabbitmq.log", "topic");//rabbitmq自己创建了amq.rabbitmq.log这个exchange

    channel.queueBind(errorQueue, "amq.rabbitmq.log", "error");//把队列绑定到amq.rabbitmq.log队列，指定路由键，rabbit会把error级别的日志路由到这个队列
    channel.queueBind(warningQueue, "amq.rabbitmq.log", "warning");
    channel.queueBind(infoQueue, "amq.rabbitmq.log", "info");

    channel.basicConsume(errorQueue, true, new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        System.out.println("error: " + new String(body));
      }
    });
    channel.basicConsume(warningQueue, true, new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        System.out.println("warn : " + new String(body));
      }
    });
    channel.basicConsume(infoQueue, true, new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        System.out.println("info: " + new String(body));
      }
    });
  }
}
