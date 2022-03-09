package com.example.noorankahvikauppa;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NooranKahvikauppaApplicationController {
  
    @GetMapping("*")
    public String home() {
      return "index";
  }
  
  @GetMapping("/tuotteet")
  public String tuotteet() {
    return "tuotteet";
  }
  
}
