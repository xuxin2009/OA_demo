package com.sean.entity;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author sean
 * @since 2019-08-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString
public class Vacation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 休假的工作日
     */
    private Integer days;

    /**
     * 请假开始时间
     */
    @TableField("beginDate")
    private String beginDate;

    /**
     * 请假结束时间
     */
    @TableField("endDate")
    private String endDate;

    /**
     * 请假类型
     */
    @TableField("vacationType")
    private Integer vacationType;

    /**
     * 请假原因
     */
    private String reason;

    /**
     * 对应的流程实例id
     */
    @TableField("processInstanceId")
    private String processInstanceId;

    private Integer userid;
    /**
     * 申请时间
     */
    private String applyTime;
    /**
     * 申请状态
     */
    private String applyStatus;


}
