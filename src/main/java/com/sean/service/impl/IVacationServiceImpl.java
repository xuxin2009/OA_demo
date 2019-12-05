package com.sean.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.sean.common.Const;
import com.sean.common.ServerResponse;
import com.sean.entity.Vacation;
import com.sean.mapper.VacationMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sean.service.IVacationService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sean
 * @since 2019-08-30
 */
@Service
public class IVacationServiceImpl extends ServiceImpl<VacationMapper, Vacation> implements IVacationService {

    @Autowired
    private IdentityService identityService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;


    /**
     * 申请假期开始流程
     * @param vacation
     * @param userName
     * @return
     */
    @Override
    public ServerResponse startVactionActiviti(Vacation vacation, String userName) {

        identityService.setAuthenticatedUserId(userName);
        //开始流程
        ProcessInstance vacationInstance = runtimeService.startProcessInstanceByKey(Const.VACATION_PROCESS_DEFINE_KEY);
        //查询当前任务
        Task currentTask = taskService.createTaskQuery().processInstanceId(vacationInstance.getId()).singleResult();
        //声明任务
        taskService.claim(currentTask.getId(),userName);

        Map<String,Object> vars = new HashMap<>();
        vars.put("applyUser",userName);
        vars.put("reason",vacation.getReason());
        vars.put("vacationType",vacation.getVacationType());
        vars.put("startTime",vacation.getBeginDate());
        vars.put("endTime",vacation.getEndDate());
        taskService.complete(currentTask.getId(),vars);

        //获取申请的假期，返回前端展示
        List<ProcessInstance> instanceList = runtimeService.createProcessInstanceQuery()
                .startedBy(userName)
                .list();
        List<Vacation> vacationList = new ArrayList<>();
        for(ProcessInstance processInstance : instanceList)
        {
            Vacation vaca = getVacation(processInstance);
            vacationList.add(vaca);
        }
        return ServerResponse.createBySuccess("申请成功!",vacationList);
    }


    private Vacation getVacation(ProcessInstance processInstance)
    {
        Date startDate =DateUtil.parse( runtimeService.getVariable(processInstance.getId(),"startTime",String.class));
        Date endDate =DateUtil.parse( runtimeService.getVariable(processInstance.getId(),"endTime",String.class));

        //请假天数
        int days =  (int) DateUtil.between(startDate,endDate, DateUnit.DAY);
        //请假原因
        String reason = runtimeService.getVariable(processInstance.getId(),"reason",String.class);
        Vacation vacation = new Vacation();
        vacation.setReason(reason);
        vacation.setDays(days);

        Date applyTime = processInstance.getStartTime();
        vacation.setApplyTime(DateUtil.formatDate(applyTime));

        vacation.setApplyStatus(processInstance.isEnded() ? "申请结束" : "等待审批");

        return vacation;
    }
}
