package com.hll.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by hll on 2016/2/25.
 */
@Controller
@RequestMapping("/")
public class LoginController {
  @RequestMapping("login")
  public String login(){
    return "login";
  }
}
