package com.sean.controller;


import com.sean.common.Const;
import com.sean.common.ServerResponse;
import com.sean.entity.User;
import com.sean.entity.Vacation;
import com.sean.service.IVacationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sean
 * @since 2019-08-30
 */
@Slf4j
@RestController
public class VacationController {

    @Autowired
    private IVacationService vacationService;


    /**
     * 请假申请
     * @param vacation
     * @param session
     * @return
     */
    @RequestMapping(value = "/vacationApply",method = RequestMethod.POST)
    public String vacationApply(@RequestBody Vacation vacation, HttpSession session,ModelMap  model)
    {
        User user= (User)session.getAttribute(Const.CURRENT_USER);

        ServerResponse serverResponse = vacationService.startVactionActiviti(vacation,user.getUsername());
        model.addAttribute("vacationData",serverResponse.getData());

        return "vacation";
    }
}
