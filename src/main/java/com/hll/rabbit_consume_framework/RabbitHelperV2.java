package com.hll.rabbit_consume_framework;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by hll on 2016/5/2.
 * RabbitMQ帮助类，读取配置文件，建立和rabbitmq的连接
 * channel是非线程安全的，对于发送相同类型的消息（比如发送订单消息）,可以使用ThreadLocal复用channel
 */
@SuppressWarnings("Duplicates")
public class RabbitHelperV2 {

  private static Logger logger = LoggerFactory.getLogger(RabbitHelperV2.class);
  private static Connection connection;

  static {
    String host = "localhost";
    ConnectionFactory connectionFactory = new ConnectionFactory();
    connectionFactory.setUsername("guest");
    connectionFactory.setPassword("guest");
    connectionFactory.setHost(host);
    try {
      connection = connectionFactory.newConnection();
    } catch (IOException | TimeoutException e) {
      logger.error("连接rabbitmq失败", e);
    }
  }

  static ThreadLocal<Channel> THREADLOCAL_ORDER_CHANNEL = new ThreadLocal<Channel>() {
    @Override
    protected Channel initialValue() {
      try {
        Channel channel = connection.createChannel();
        channel.queueDeclare();
        //......设置channel参数
        return channel;
      } catch (IOException e) {
        throw new RuntimeException("创建rabbitmq信道失败");
      }
    }
  };

  public static Channel getChannelForOrder() throws IOException {
    return THREADLOCAL_ORDER_CHANNEL.get();
  }
}
