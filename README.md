# consumer
rabbitmq实例应用

封装了消费端结合spring的使用框架

使用方式：
  实现RabbitConsumer接口，加上@Consumer注解
  在consume方法实现中订阅rabbitmq消息，注册消费回调函数

添加发送确认的demo
