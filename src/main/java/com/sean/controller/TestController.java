package com.sean.controller;

import com.sean.common.Const;
import com.sean.entity.Vacation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by sean on 2019/8/30.
 */
@Controller
@Slf4j
public class TestController {

    /**
     *
     */
    @RequestMapping("/index")
    public String index()
    {
        log.info("i am come in");
        return"index";
    }

    @RequestMapping(value = "/tree")
    public String tree()
    {
        log.info("i am come in");
        return"tree";
    }
    @RequestMapping(value = "/welcome")
    public String welcome()
    {
        log.info("i am come in");
        return"welcome";
    }

    @RequestMapping(value = "/vacation")
    public String vacation()
    {
        log.info("vacation");
        return"vacation";
    }

    @RequestMapping(value = "/vacationApply",method = RequestMethod.POST)
    public String vacationApply(@RequestBody Vacation vacation)
    {
        log.info("vacationApply:"+vacation.toString());
        return"fdsfsd";
    }
}
