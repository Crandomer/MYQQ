package com.random.base;

public class Cmd {
    //上线通知
    public static final int CMD_ONLINE=1000;
    //离线
    public static final int CMD_OFFLINE=1001;

    /*离开*/
    public static final int CMD_LEAVE=1002;
    /*忙碌*/
    public static final int CMD_BUYS=1003;
    /*发送消息*/
    public static final int CMD_SEND=1004;
    /*发送文件*/
    public static final int CMD_FILE=1005;
    /*发送抖动*/
    public static final int CMD_SHAKE=1006;
    /*添加好友*/
    public static final int CMD_ADDFRIEND=1007;
    /*同意添加*/
    public static final int CMD_AGREEN=1008;
    /*拒绝*/
    public static final int CMD_REFUSE=1009;
    //好友状态
    public static final String[] STATUS={"在线","离线","忙碌","隐身"};
    //分组名称
    public static final String[]GroupNAME={"同学","好友","家人","黑名单"};





}
