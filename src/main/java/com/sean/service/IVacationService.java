package com.sean.service;

import com.sean.common.ServerResponse;
import com.sean.entity.Vacation;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sean
 * @since 2019-08-30
 */
public interface IVacationService extends IService<Vacation> {



    ServerResponse startVactionActiviti(Vacation vacation , String userName);
}
