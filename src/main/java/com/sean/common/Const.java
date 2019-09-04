package com.sean.common;



/**
 * Created by Administrator on 2018/9/12.
 */
public class Const {
    public static final String CURRENT_USER = "current_user";
    public static  final String EMAIL = "email";
    public static final  String USERNAME = "userName";


    public interface Role {
        int ROLE_CUSTOMER = 0;//普通用户
        int ROLE_ADMIN = 1;//admin
    }

    public interface Vacation{
        int  TYPE_PAID = 0;//带薪假
        int  TYPE_SICK = 1;//病假
        int  TYPE_MATTER = 2;//事假
    }



    public enum OrderStatusEnum {
        CANCEL(0, "已取消"),
        NO_PAY(10, "未支付"),
        PAID(20, "已支付"),
        SHIPPED(40, "已发货"),
        ORDER_SUCCESS(50, "订单完成"),
        ORDER_CLOSE(60, "订单关闭");

        private String value;
        private int code;

        OrderStatusEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }

        public static OrderStatusEnum codeOf(int code) {
            for (OrderStatusEnum orderStatusEnum : values()) {
                if (orderStatusEnum.getCode() == code) {
                    return orderStatusEnum;
                }
            }
            throw new RuntimeException("没有找到对应的枚举");
        }
    }

    public interface AlipayCallback {
        String TRADE_STATUS_WAIT_BUYER_PAY = "WAIT_BUYER_PAY";
        String TRADE_STATUS_TRADE_SUCCESS = "TRADE_SUCCESS";

        String RESPONSE_SUCCESS = "success";
        String RESPONSE_FAILED = "failed";
    }




}
