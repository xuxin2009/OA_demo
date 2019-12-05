package com.sean.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sean.common.Const;
import com.sean.common.ServerResponse;
import com.sean.entity.User;
import com.sean.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * Created by sean on 2019/8/30.
 */
@Slf4j
@Controller
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IdentityService identityService;

    /**
     * 用户登录
     * @param user
     * @return
     */
    @RequestMapping("/loginView")
    @ResponseBody
    public ServerResponse loginView(@RequestBody User user, HttpSession session)
    {
        //简单的一个登录验证
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username",user.getUsername()).eq("password",user.getPassword());
        User user1 = userService.getOne(userQueryWrapper);

        if(user1 != null)
        {
            session.setAttribute(Const.CURRENT_USER,user1);

            //用户登录成功后，把user信息放到activiti的user表中

            //首先查询activiti的user表中是否存在该用户，如果不存在则添加到其中
            org.activiti.engine.identity.User userdb = identityService.createUserQuery().userId(String.valueOf(user1.getId())).singleResult();

            if(userdb == null)
            {
                org.activiti.engine.identity.User activiti_user = identityService.newUser(String.valueOf(user1.getId()));
                activiti_user.setPassword(user1.getPassword());
                activiti_user.setEmail(user1.getEmail());
                activiti_user.setFirstName(user1.getUsername());
                identityService.saveUser(activiti_user);
            }


            return ServerResponse.createBySuccess("登录成功",user1);
        }
        return  ServerResponse.createByErrorMessage("用户名或密码错误，请重新输入！");

    }

}
