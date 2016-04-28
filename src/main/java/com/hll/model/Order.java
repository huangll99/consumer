package com.hll.model;

/**
 * Created by hll on 2016/4/28.
 */
public class Order {
  String id;
  int num;
  String user;

  public Order() {
  }

  public Order(String id, int num, String user) {
    this.id = id;
    this.num = num;
    this.user = user;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public int getNum() {
    return num;
  }

  public void setNum(int num) {
    this.num = num;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  @Override
  public String toString() {
    return "Order{" +
        "id='" + id + '\'' +
        ", num=" + num +
        ", user='" + user + '\'' +
        '}';
  }
}
