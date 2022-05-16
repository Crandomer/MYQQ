package com.random.base;

import com.random.vo.AccountVo;

import javax.swing.text.StyledDocument;
import java.io.Serializable;

public class SendMsg implements Serializable {
    //命令字
    public int cmd;
    //发送人和接受人信息
    public AccountVo myinfo,friendinfo;
    //发送的文本框内容
    public StyledDocument doc;
    //发送文件内容，字节流,在64k一下
    public byte b[];



}
