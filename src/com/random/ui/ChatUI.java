package com.random.ui;

import com.random.base.Cmd;
import com.random.base.SendCmd;
import com.random.base.SendMsg;
import com.random.vo.AccountVo;

import javax.print.DocFlavor;
import javax.swing.*;
import javax.swing.text.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatUI extends JFrame implements ActionListener, ItemListener {
    JLabel title;
    JTextPane txtReceive,txtSend;
    JButton btnSend,btnClose;
    JButton btnShake,btnFile,btnColor,btnFace;
    JComboBox cbFont,cbSize;
    AccountVo myInfo,friendInfo;
    String sFont[]={"宋体","黑体","隶书","楷体"};
   String sSize[]={"8","10","12","16","18","24","26","28"};

    JLabel bg;
    Font font;
    public ChatUI(AccountVo myInfo,AccountVo friendInfo)
    {
        String str=myInfo.getNickName()+"和"+friendInfo.getNickName()+"的聊天";
        setTitle(str);
        this.myInfo=myInfo;
        this.friendInfo=friendInfo;
        setIconImage(new ImageIcon(friendInfo.getHeadImg()).getImage());
        title=new JLabel(str,new ImageIcon(friendInfo.getHeadImg()),JLabel.LEFT);
        add(title, BorderLayout.NORTH);
     /*   setTitle("ceshi");
        title=new JLabel("hh");
        add(title,BorderLayout.NORTH);*/

        JPanel centerPanel=new JPanel(new GridLayout(2,1,5,5));
        txtReceive=new JTextPane();
        centerPanel.add(new JScrollPane(txtReceive));
        //下面1,抖动，颜色，表情等控件，2,发送，3,关闭
        JPanel sendPanel=new JPanel(new BorderLayout());//包括三部分
        //第一部分
        JPanel btnPanel=new JPanel(new FlowLayout(FlowLayout.LEFT));
        cbFont=new JComboBox(sFont);
        cbSize=new JComboBox(sSize);
        cbFont.addItemListener(this);
        cbSize.addItemListener(this);

        btnShake=new JButton(new ImageIcon("images/zd.png"));
        btnShake.setBounds(new Rectangle(0,0,0,0));
        btnFile=new JButton(new ImageIcon("images/file.jpg"));
        btnColor=new JButton(new ImageIcon("images/color.jpg"));
        btnFace=new JButton(new ImageIcon("images/emoji.jpg"));
        btnShake.setMargin(new Insets(0,0,0,0));
        btnFile.setMargin(new Insets(0,0,0,0));
        btnFace.setMargin(new Insets(0,0,0,0));
        btnColor.setMargin(new Insets(0,0,0,0));

        btnShake.addActionListener(this);
        btnFace.addActionListener(this);
        btnFile.addActionListener(this);
        btnColor.addActionListener(this);




        btnPanel.add(cbFont);
        btnPanel.add(cbSize);
        btnPanel.add(btnFace);
        btnPanel.add(btnShake);
        //*btnPanel.add(btnSend);*//*
        btnPanel.add(btnFile);
        btnPanel.add(btnColor);
        sendPanel.add(btnPanel,BorderLayout.NORTH);
        txtSend=new JTextPane();
        sendPanel.add(txtSend,BorderLayout.CENTER);

        //底部
    JPanel bottonPanel=new JPanel(new FlowLayout(FlowLayout.RIGHT));
       btnSend=new JButton("发送（S）");
       btnSend.setMnemonic('S');
       btnClose=new JButton("关闭（X）");
       btnClose.setMnemonic('X');

        btnSend.addActionListener(this);
        btnClose.addActionListener(this);


        bottonPanel.add(btnSend);
       bottonPanel.add(btnClose);
       sendPanel.add(bottonPanel,BorderLayout.SOUTH);
       centerPanel.add(new JScrollPane(sendPanel));
       add(centerPanel);
       JPanel rightPanel=new JPanel(new GridLayout(2,1,5,5));
       add(rightPanel,BorderLayout.EAST);
        JLabel lblboy=new JLabel(new ImageIcon("images/qqshowboy .gif"));
        JLabel lblgirl=new JLabel(new ImageIcon("images/qqshowgirl.gif"));
      rightPanel.add(lblboy);
      rightPanel.add(lblgirl);

      setSize(800,500);
      setVisible(true);
      setLocationRelativeTo(null);
      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

//把发送框内容调教到接受框，同时清空发送框内容
    public  void appendView(String name, StyledDocument xx) throws BadLocationException {
        //获取内容
        StyledDocument vdoc=txtReceive.getStyledDocument();
        //格式化时间
        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time=sdf.format(date);
        //创建属性集合
        SimpleAttributeSet as=new SimpleAttributeSet();
        //显示说话者
        String s=name+"   "+time+"\n";

        //保存聊天记录
        vdoc.insertString(vdoc.getLength(),s,as);
        int end=0;
        //处理显示内容
        while (end<xx.getLength()) {
            //获取一个元素
            Element e0=xx.getCharacterElement(end);
            //获取对应风格
            SimpleAttributeSet as1=new SimpleAttributeSet();
            StyleConstants.setForeground(as1,StyleConstants.getForeground(e0.getAttributes()));
            StyleConstants.setFontSize(as1,StyleConstants.getFontSize(e0.getAttributes()));
            StyleConstants.setFontSize(as1,StyleConstants.getFontSize(e0.getAttributes()));
            //获取该元素的内容
            s=e0.getDocument().getText(end,e0.getEndOffset()-end);
            //将元素添加到浏览器中
            if("icon".equals(e0.getName()))
            {
                vdoc.insertString(vdoc.getLength(),s, e0.getAttributes());
            }
            else {
                vdoc.insertString(vdoc.getLength(),s,as1);
            }
            end=e0.getEndOffset();
        }
        //输入一个换行符
        vdoc.insertString(vdoc.getLength(),"\n",as);
        //设置显示视图加字符的位置与文档结尾，一遍视图滚动
        txtReceive.setCaretPosition(vdoc.getLength());
    }


    //监听事件
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==btnShake)
        {

        }
        else if(e.getSource()==btnColor)
        {
            JColorChooser colordlg=new JColorChooser();
            //打开颜色对话框
            Color color= colordlg.showDialog(this,"请选择字体颜色",Color.BLACK);
            //设置发哦送给您文本框
            txtSend.setForeground(color);


        }
        else if(e.getSource()==btnFile)
        {

        }
        else if(e.getSource()==btnFace)
        {
            new BqUI(this);
        }
        else if(e.getSource()==btnSend)
        {
            if(txtSend.getText().equals(""))
            {
                JOptionPane.showMessageDialog(this,"请输入聊天内容");
                return;
            }
            else {
                try {
                    appendView(myInfo.getNickName(), txtSend.getStyledDocument());
                    SendMsg msg=new SendMsg();
                    msg.cmd= Cmd.CMD_SEND;
                    msg.myinfo=myInfo;
                    msg.friendinfo=friendInfo;
                    msg.doc=txtSend.getStyledDocument();
                    SendCmd.send(msg);
                    txtSend.setText("");
                } catch (BadLocationException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        else if(e.getSource()==btnClose)
        {
            dispose();
        }

    }

    public void setfont()
    {
        String sf=sFont[cbFont.getSelectedIndex()];
        String size=sSize[cbSize.getSelectedIndex()];
        font =new Font(sf,Font.PLAIN,Integer.parseInt(size));
        //设置发送文本框的代码
        txtSend.setFont(font);
    }
    @Override
    public void itemStateChanged(ItemEvent e) {

        if(e.getSource()==cbFont)
        {
           setfont();

        } else if (e.getSource()==cbSize) {
            setfont();
        }
    }

 /*   public static void main(String[] args) {
        new ChatUI();
    }*/
}
