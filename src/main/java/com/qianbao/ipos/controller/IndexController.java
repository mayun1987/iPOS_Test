package com.qianbao.ipos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//import java.util.logging.Logger;
//import com.qianbao.ipos.log.AppLogger;


@Controller
public class IndexController {

    @GetMapping("/refund")
    public String refund() {
        return "refund";
    }

    @GetMapping("/push")
    public String push() {
        return "push";
    }

    @GetMapping("/auth")
    public String auth() {
        return "auth";
    }

    @GetMapping("/interfaceTool")
    public String interfaceTool() {
        return "interfaceTool";
    }

    @GetMapping("/encryptOrDecrypt.html")
    public String encryptOrDecrypt() {
        return "encryptOrDecrypt";
    }


}