package com.random.base;

import com.random.vo.AccountVo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.file.StandardCopyOption;
import java.util.Vector;

public class SendCmd {
    public static void send(SendMsg msg) throws IOException {
        //定义Socket
        DatagramSocket socket=new DatagramSocket();
        //创建字符数组输出流
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        //把对象输出到字符数组中
        ObjectOutputStream oos=new ObjectOutputStream(bos);
        oos.writeObject(msg);
        //发送数据转换为字符数组
        byte b[]=bos.toByteArray();
        //获取加好友ip
        InetAddress addr=InetAddress.getByName(msg.friendinfo.getIpAddr());
        //获取好友端口
        int port=msg.friendinfo.getPort();

        //生成发送报文
        DatagramPacket pack=new DatagramPacket(b,0,b.length,addr,port);
        socket.send(pack);
        System.out.println(msg.cmd+"==发送消息");
        socket.close();
        oos.close();

    }
    //广播发送功能
    public  static void sendAll(Vector<AccountVo> allInfo,AccountVo myinfo,int cmd) throws IOException {
        for(AccountVo acc:allInfo)
        {
            if(!acc.getOnlinestatus().equals(Cmd.STATUS[1])&&acc.getQqCode()!=myinfo.getQqCode())
            {
                SendMsg msg=new SendMsg();
                msg.cmd=cmd;
                msg.friendinfo=acc;
                msg.myinfo=myinfo;
                send(msg);
            }
        }
    }
}
