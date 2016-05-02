package com.hll.demo;

import com.hll.rabbit_consume_framework.RabbitHelper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Created by hll on 2016/5/1.
 */
public class HelloProducerWithConfirm {
  public static void main(String[] args) throws IOException, InterruptedException {
    Channel channel = RabbitHelper.getChannel();
    channel.exchangeDeclare("hello-exchange", "direct", true, false, null);
    // final SortedSet<Long> unconfirmedSet = Collections.synchronizedSortedSet(new TreeSet<Long>());
    final SortedSet<Long> unconfirmedSet = new ConcurrentSkipListSet<>();
    channel.addConfirmListener(new ConfirmListener() {
      @Override
      public void handleAck(long deliveryTag, boolean multiple) throws IOException {
        if (!unconfirmedSet.contains(deliveryTag)) {
          System.out.println("got duplicate ack:" + deliveryTag);
        }
        if (multiple) {
          unconfirmedSet.headSet(deliveryTag + 1).clear();
        } else {
          System.out.println("msg - " + deliveryTag + " received");
          unconfirmedSet.remove(deliveryTag);
        }
      }

      @Override
      public void handleNack(long deliveryTag, boolean multiple) throws IOException {
        if (unconfirmedSet.contains(deliveryTag)) {
          System.out.println("msg - " + deliveryTag + " lost");
        }
      }
    });

    channel.confirmSelect();//Enables publisher acknowledgements on this channel.

    for (int i = 0; i < 100000; i++) {
      unconfirmedSet.add(channel.getNextPublishSeqNo());//要在publish之前，否则还没放入set,就已经确认,会认为duplicate
      channel.basicPublish("hello-exchange", "hola", new AMQP.BasicProperties().builder().contentType("text/plain").build(), "hello".getBytes());
      Thread.sleep(1000);
    }
    channel.waitForConfirmsOrDie();
    if (!unconfirmedSet.isEmpty()) {
      System.out.println("waitForConfirms returned with unconfirmed messages");
    }
  }
}
