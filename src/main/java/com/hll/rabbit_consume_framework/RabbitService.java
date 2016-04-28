package com.hll.rabbit_consume_framework;

import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * Created by hll on 2016/4/28.
 * spring 容器启动后执行，获取应用上下文，找到所有有Consumer注解的Bean,调用其consume方法，开始消费rabbitmq消息
 */
@Component
public class RabbitService implements ApplicationContextAware {

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    //System.out.println("启动......");

    Map<String, Object> consumerMap = applicationContext.getBeansWithAnnotation(Consumer.class);
    if (MapUtils.isNotEmpty(consumerMap)) {
      for (Map.Entry<String, Object> entry : consumerMap.entrySet()) {
        RabbitConsumer consumer = (RabbitConsumer) entry.getValue();
        try {
          consumer.consume(RabbitHelper.getChannel());
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
    }
  }
}
