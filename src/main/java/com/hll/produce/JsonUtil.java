package com.hll.produce;


import com.alibaba.fastjson.JSON;
import com.hll.model.Order;

/**
 * Created by hll on 2016/4/28.
 */
public class JsonUtil {

  public static String toJson(Object obj){
   return JSON.toJSONString(obj);
  }

  public static void main(String[] args) {
    String s = toJson(new Order("ii", 9, "idj"));
    System.out.println(s);
  }
}
