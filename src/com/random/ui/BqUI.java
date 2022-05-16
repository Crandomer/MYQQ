package com.random.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

public class BqUI extends JFrame implements MouseListener {
    JLabel []bqicon;
    String iconlist[];
    ChatUI chat;
    public BqUI(ChatUI chat)
    {
        this.chat=chat;
        setUndecorated(true);
        setResizable(true);
        //设置窗口在最前面
        setAlwaysOnTop(true);
        Container con=getContentPane();
        con.setLayout(new FlowLayout(FlowLayout.LEFT));
        con.setBackground(Color.white);
        File file=new File("bq");
        //获取bq文件夹的所以
        iconlist=file.list();
        bqicon=new JLabel[iconlist.length];
        for(int i=0;i<iconlist.length;i++)
        {
            //每个Jlable控件显示一个图标
            bqicon[i]=new JLabel(new ImageIcon("bq/"+iconlist[i]));
            //设置边框白色，边框2px
            bqicon[i].setBorder(BorderFactory.createLineBorder(Color.WHITE,2));
            //为每一个Jlbale控件添加鼠标事件
            bqicon[i].addMouseListener(this);
            add(bqicon[i]);
        }

        setSize(210,210);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public static void main(String[] args) {
        new BqUI(null);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    for(int i=0;i<iconlist.length;i++)
    {
    if(e.getSource()== bqicon[i])
    {
        bqicon[i].setBorder((BorderFactory.createLineBorder(Color.BLUE,2)));
        break;
    }
    }
    }

    @Override
    public void mouseExited(MouseEvent e) {

        for(int i=0;i<iconlist.length;i++)
        {
            if(e.getSource()== bqicon[i])
            {
                bqicon[i].setBorder((BorderFactory.createLineBorder(Color.WHITE,2)));
                break;
            }
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {

        if(e.getClickCount()==2)
        {
            for(int i=0;i<iconlist.length;i++)
            {
                if(e.getSource()==bqicon[i])
                {

                    chat.txtSend.insertIcon(bqicon[i].getIcon());
                    dispose();

                    break;
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }
}
