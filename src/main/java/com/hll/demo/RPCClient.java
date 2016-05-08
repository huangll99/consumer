package com.hll.demo;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 * Created by hll on 2016/5/8.
 */
public class RPCClient {

  private Channel channel;
  private String replyQueueName;
  private QueueingConsumer consumer;
  private final Connection connection;
  private String requestQueueName = "rpc_queue";

  public RPCClient() throws IOException, TimeoutException {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    connection = factory.newConnection();
    channel = connection.createChannel();

    replyQueueName = channel.queueDeclare().getQueue();
    consumer = new QueueingConsumer(channel);
    channel.basicConsume(replyQueueName, true, consumer);
  }

  public String call(String message) throws IOException, InterruptedException {
    String response;
    String corrId = UUID.randomUUID().toString();
    AMQP.BasicProperties props = new AMQP.BasicProperties
        .Builder()
        .correlationId(corrId)
        .replyTo(replyQueueName)
        .build();
    channel.basicPublish("", requestQueueName, props, message.getBytes());
    while (true) {
      QueueingConsumer.Delivery delivery = consumer.nextDelivery();
      if (delivery.getProperties().getCorrelationId().equals(corrId)) {
        response = new String(delivery.getBody());
        break;
      }
    }
    return response;
  }

  public void close() throws IOException {
    connection.close();
  }

  public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
    RPCClient rpcClient = new RPCClient();
    String response = rpcClient.call("53");
    System.out.println(response);
    response = rpcClient.call("30");
    System.out.println(response);
    rpcClient.close();
  }
}
