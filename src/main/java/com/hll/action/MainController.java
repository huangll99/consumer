package com.hll.action;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by hll on 2016/2/25.
 */
@Controller
@RequestMapping("/")
public class MainController {

  @RequestMapping("index")
  public String index(Model model){
	  model.addAttribute("user", "wwwddd");
	  System.out.println("nani");
    return "index";
  }
}
