package com.sean.controller;

import com.sean.common.Const;
import com.sean.common.ServerResponse;
import com.sean.entity.User;
import com.sean.entity.Vacation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by sean on 2019/8/30.
 */
@Controller
@Slf4j
public class ViewController {


    @RequestMapping("/index")
    public String index()
    {
        return"index";
    }

    @RequestMapping("/login")
    public String login()
    {
        return "login";
    }

    @RequestMapping("welcome")
    public  String welcome()
    {
        return "welcome";
    }

    @RequestMapping("vacation")
    public String vacation()
    {
        return "vacation";
    }




}
