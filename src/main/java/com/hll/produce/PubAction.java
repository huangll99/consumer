package com.hll.produce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

/**
 * Created by hll on 2016/4/28.
 */
@Controller
@RequestMapping("/pub")
public class PubAction {


  @Autowired
  ProducerService producerService;

  @RequestMapping("/toPub")
  public String toPub() throws IOException {
    return "chatbox";
  }

  @RequestMapping("/push")
  public String push(String msg) {
    producerService.sendOrderMsg(new Order("ii", 9, msg));
    System.out.println("send......");
    return "redirect:/pub/toPub";
  }
}
