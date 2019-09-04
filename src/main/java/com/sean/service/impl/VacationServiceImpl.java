package com.sean.service.impl;

import com.sean.entity.Vacation;
import com.sean.mapper.VacationMapper;
import com.sean.service.IOaVacationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sean
 * @since 2019-08-30
 */
@Service
public class VacationServiceImpl extends ServiceImpl<VacationMapper, Vacation> implements IOaVacationService {


}
