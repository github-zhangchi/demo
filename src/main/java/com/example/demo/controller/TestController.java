package com.example.demo.controller;


import com.example.demo.service.QrCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private QrCodeService qrCodeService;



    @RequestMapping(name = "/hello",method = RequestMethod.GET)
    public String hello(@RequestParam String name){
        return  name + "是傻逼!!!";
    }

    @RequestMapping("/tomakeMode")
    public String tomakeMode(@RequestParam String strJson){
        return qrCodeService.tomakeMode(strJson);

}
}
