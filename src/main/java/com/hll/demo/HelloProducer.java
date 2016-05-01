package com.hll.demo;

import com.hll.rabbit_consume_framework.RabbitHelper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

import java.io.IOException;

/**
 * Created by hll on 2016/5/1.
 */
public class HelloProducer {
  public static void main(String[] args) throws IOException, InterruptedException {
    Channel channel = RabbitHelper.getChannel();
    channel.exchangeDeclare("hello-exchange","direct",true,false,null);
    for (int i=0;i<100000;i++){
      channel.basicPublish("hello-exchange","hola",new AMQP.BasicProperties().builder().contentType("text/plain").build(),"hello".getBytes());
      Thread.sleep(1000);
    }
  }
}
