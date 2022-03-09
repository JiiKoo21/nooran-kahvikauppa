package com.example.noorankahvikauppa;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class NooranKahvikauppaApplicationController {
  
    @GetMapping("*")
    @ResponseBody
    public String home() {
      return "Morjensta!";
  }  
}
