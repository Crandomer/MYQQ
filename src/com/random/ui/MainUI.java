package com.random.ui;

import com.random.base.Cmd;
import com.random.base.SendCmd;
import com.random.base.SendMsg;
import com.random.db.BaseDao;
import com.random.vo.AccountVo;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Vector;

public class MainUI  extends JFrame implements ActionListener, MouseListener {

    JLabel bgImg, lblmyinfo;
    JTabbedPane tabPanel;
    JList lstFriend, lstmate, lstFamily, lstHmd;
    JButton btnfind, btnChangebj;
    JComboBox cbState;

    //弹出菜单
    JPopupMenu popMenu;
    JMenuItem miChat,miFamilily,miFriend,miMate,miHmd,milookinfo,miDel;

    ChatUI chat;
    HashMap<Integer,ChatUI>chatWin=new HashMap<Integer,ChatUI>();
    //保存登录成功后的信息
    AccountVo myinfo,friendinfo;
    //保存所有好友信息（）
Vector<AccountVo> vAllInfo,vFriend,vFamiliy,vMate,vHmd;
BaseDao baseDao=new BaseDao();

    public MainUI(AccountVo myinfo) {
        this.myinfo = myinfo;
        /* setResizable(false);*/
        //菜单图标
       /* Toolkit toolkit =Toolkit.getDefaultToolkit();
        Image img=toolkit.getImage("images/qqicon.jpeg");*/
        setIconImage(new ImageIcon(myinfo.getHeadImg()).getImage());

        bgImg = new JLabel(new ImageIcon("images/m1.jpg"));
        bgImg.setLayout(new BorderLayout());
        //设置背景透明
        bgImg.setOpaque(false);
        add(bgImg);


        //获取好友信息
        vAllInfo=new Vector<AccountVo>();
        vFriend=new Vector<AccountVo>();
        vFamiliy=new Vector<AccountVo>();
        vMate=new Vector<AccountVo>();
        vHmd=new Vector<AccountVo>();

        lstFriend=new JList();
        lstFamily=new JList();
        lstHmd=new JList();
        lstmate=new JList();



        refresh();


        //设置透明,并添加事件
        lstHmd.setOpaque(false);
        lstHmd.setBackground(new Color(0, 0, 0, 0));
        lstFriend.setOpaque(false);
        lstFriend.setBackground(new Color(0, 0, 0, 0));
        lstFamily.setOpaque(false);
        lstFamily.setBackground(new Color(0, 0, 0, 0));
        lstmate.setOpaque(false);
        lstmate.setBackground(new Color(0, 0, 0, 0));
        lstFriend.addMouseListener(this);
        lstFamily.addMouseListener(this);
        lstmate.addMouseListener(this);
        lstHmd.addMouseListener(this);


        //摄制tabpanel为透明
        UIManager.put("TabbedPane.contentOpaque", false);
        tabPanel = new JTabbedPane();
        tabPanel.setOpaque(false);

        //添加标签
        tabPanel.add("好友", lstFriend);
        tabPanel.add("同事", lstmate);
        tabPanel.add("家人", lstFamily);
        tabPanel.add("黑名单", lstHmd);

        bgImg.add(tabPanel);

        //设置个人头像
        lblmyinfo = new JLabel(myinfo.getNickName() + "(" + myinfo.getQqCode() + ")" + myinfo.getRemark(), new ImageIcon(myinfo.getHeadImg()), JLabel.LEFT);
        lblmyinfo.addMouseListener(this);
        bgImg.add(lblmyinfo, BorderLayout.NORTH);

        setVisible(true);
        setSize(300, 700);
        setLocation(1050, 50);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        CreateMenu();
        //启动线程
        new ReceiveThread().start();
        //发送登录广播
        try {
            SendCmd.sendAll(vAllInfo,myinfo,Cmd.CMD_ONLINE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        //下方功能按钮
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setOpaque(false);
        btnfind = new JButton("查找");
        btnChangebj = new JButton("换肤");
        bottomPanel.add(btnfind);
        bottomPanel.add(btnChangebj);
        bgImg.add(bottomPanel, BorderLayout.SOUTH);

        //监听事件
        btnfind.addActionListener(this);
        btnChangebj.addActionListener(this);

    }

    //把好友信息分组放入对应Vector类
    public void refresh()
    {
        //获取好友信息
        vAllInfo=baseDao.getMyFriend(myinfo.getQqCode());
      //清空Vector的值
        vMate.clear();
        vFamiliy.clear();
        vHmd.clear();
        vFriend.clear();
        for(AccountVo acc:vAllInfo)
        {
            String group=acc.getGroupName();
            if(group.equals(Cmd.GroupNAME[0]))
            {
                vMate.add(acc);
            }
            else if(group.equals(Cmd.GroupNAME[1]))
            {
                vFriend.add(acc);
            }
            else if(group.equals(Cmd.GroupNAME[2]))
            {
                vFamiliy.add(acc);
            }
            else if(group.equals(Cmd.GroupNAME[3]))
            {
                vHmd.add(acc);
            }
        }
        //初始化界面显示
        lstFriend .setModel(new DataModel(vFriend));
        lstmate .setModel(new DataModel(vMate));
        lstFamily.setModel(new DataModel(vFriend));
        lstHmd .setModel(new DataModel(vHmd));
        lstFamily.setCellRenderer(new myHeadImg(vFamiliy));
        lstFriend.setCellRenderer(new myHeadImg(vFriend));
        lstmate.setCellRenderer(new myHeadImg(vMate));
        lstHmd.setCellRenderer(new myHeadImg(vHmd));



    }


    @Override
    public void actionPerformed(ActionEvent e) {
        //更换背景图片
        if (e.getSource() == btnChangebj) {
            JFileChooser dlg = new JFileChooser();
            //打开文件对话框
            dlg.setDialogType(JFileChooser.OPEN_DIALOG);
            dlg.setFileSelectionMode(JFileChooser.FILES_ONLY);
            //设置对话框标题
            dlg.setDialogTitle("更换皮肤");
            if (dlg.showOpenDialog(this) == dlg.APPROVE_OPTION) {
                File file = dlg.getSelectedFile();
                String path = file.getPath();
                bgImg.setIcon(new ImageIcon(path));
            }
        } else if (e.getSource()==milookinfo) {
            System.out.println(12312);
                friendinfo=vFriend.get(lstFriend.getSelectedIndex());
                System.out.println(friendinfo);
                new LookInfoUI(friendinfo);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == lblmyinfo && e.getClickCount() == 2) {

            new UpdateMyinfoUI(myinfo, this);

        } else if (e.getSource() == lstFriend) {
            //点击右键
            if (e.getButton() == 3) {
                System.out.println("fr");
                if(lstFriend.getSelectedIndex()>=0)
                {
                    popMenu.show(lstFriend, e.getX(), e.getY());}
            } else if (e.getClickCount()==2) {
                if(lstFriend.getSelectedIndex()>=0)
                {
                    System.out.println(12313);
                    System.out.println(lstFriend.getSelectedIndex());
                  friendinfo=vFriend.get(lstFriend.getSelectedIndex());

                  chat=new ChatUI(myinfo,friendinfo);

            }
            }
        } else if (e.getSource() == lstFamily) {

            if (e.getButton() == 3) {
                if(lstFamily.getSelectedIndex()>=0)
                {
                    System.out.println("fa");
                   popMenu.show(lstFamily, e.getX(), e.getY());}

            }
        } else if (e.getSource() == lstmate) {

            if (e.getButton() == 3) {
                System.out.println("ma");
                if(lstmate.getSelectedIndex()>=0)
                {  popMenu.show(lstmate, e.getX(), e.getY());}


            }
        } else if (e.getSource() == lstHmd) {

            if (e.getButton() == 3) {
                if(lstHmd.getSelectedIndex()>=0)
                {
                    System.out.println("hm");popMenu.show(lstHmd, e.getX(), e.getY());}

            }
        }

    }



    //显示文本信息
    class DataModel extends AbstractListModel
    {
        Vector<AccountVo>data;
        public DataModel(){

        }
        public DataModel(Vector<AccountVo> data)
        {
            this.data=data;
        }

        @Override
        public int getSize() {

            return data.size();
        }
    //系统自动运行，下标从零开始
        @Override
        public Object getElementAt(int index) {
          AccountVo info=data.get(index);
          return null/*info.getNickName()+"("+info.getQqCode()+")"+"["+info.getRemark()+"]"*/;
        }
    }

    //创建弹出次菜单
    public void CreateMenu()
    {
        miChat=new JMenuItem("聊天");
        miFamilily=new JMenuItem("移动到家庭");
        miFriend=new JMenuItem("移动到朋友");
        miMate=new JMenuItem("移动到好友");
        miHmd=new JMenuItem("移动到黑名单");
        milookinfo=new JMenuItem("查看好友信息");

        miDel=new JMenuItem("删除好友");
        popMenu=new JPopupMenu();
        popMenu.add(miChat);
        popMenu.add(miFamilily);
        popMenu.add(miFriend);
        popMenu.add(miHmd);
        popMenu.add(miMate);
        popMenu.add(milookinfo);
        popMenu.add(miDel);
        //给Michat添加事件
        miChat.addActionListener(this);
        miFamilily.addActionListener(this);
        miFriend.addActionListener(this);
        miMate.addActionListener(this);
        miHmd.addActionListener(this);
        milookinfo.addActionListener(this);
        miDel.addActionListener(this);






    }
    //获取好友头像
    class myHeadImg extends DefaultListCellRenderer{
        Vector<AccountVo>datas;
        public myHeadImg(Vector<AccountVo> datas)
        {
            this.datas=datas;
        }
        public Component getListCellRendererComponent(JList list,Object value,int index,boolean isSelected,boolean cellHasFocus)
        {
            Component c=super.getListCellRendererComponent(list,value,index,isSelected,cellHasFocus);
            if(index>=0&&index<datas.size())
            {
                AccountVo user=datas.get(index);
                String status=user.getOnlinestatus();

                String headImg= user.getHeadImg();
                String filename="";
                int pos=headImg.indexOf('.');
                String pre=headImg.substring(0,pos);
                String fix=headImg.substring(pos,headImg.length());

                if(status.equals((Cmd.STATUS[0])))
                {
                   filename=headImg;

                }
                else if(status.equals((Cmd.STATUS[1])))
                {

                filename=pre+"_h"+fix;


                }
                else if(status.equals((Cmd.STATUS[2])))
                {

                    filename=pre+"_l"+fix;
                }
                else if(status.equals((Cmd.STATUS[3])))
                {

                    filename=pre+"_w"+fix;
                }
                //给列表中好友设置头像、

                setIcon(new ImageIcon(filename));
                //设置wenbenneir
                setText(user.getNickName()+"("+user.getQqCode()+")"+"["+user.getRemark()+"]");
               /* System.out.println(user.getHeadImg());*/
            }
            if(isSelected)
            {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            }
            else
            {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            //设置有效
            setEnabled(list.isEnabled());
            //默认字体
            setFont(list.getFont());
            setFont(new Font("宋体",Font.BOLD,14));
            //设置透蜜背景
            setOpaque(true);
            return this;
        }

    }

    //定义内部线程类
    class ReceiveThread extends Thread {
        public ReceiveThread() {

        }

        @Override
        public void run() {

            try {
                DatagramSocket socket=new DatagramSocket(myinfo.getPort());
               while (true)
               {
                   byte[] b=new byte[1024*512];

                   DatagramPacket pack=new DatagramPacket(b,0,b.length);
                   socket.receive(pack);
                   ByteArrayInputStream bais=new ByteArrayInputStream(b);
                   ObjectInputStream ois=new ObjectInputStream(bais);
                   SendMsg msg=(SendMsg) ois.readObject();
                   switch(msg.cmd)
                   {
                       case Cmd.CMD_ONLINE://登录上线
                           System.out.println("shangxian");
                           refresh();
                           break;
                       case Cmd.CMD_SEND://接受聊天消息
                           if(chat==null)
                           {
                               chat=new ChatUI(msg.friendinfo,msg.myinfo);
                           }
                            chat.appendView(msg.myinfo.getNickName(),msg.doc);
                           break;
                   }

               }


            } catch (SocketException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (BadLocationException e) {
                throw new RuntimeException(e);
            }
        }


    }
    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

    /*public static void main(String[] args) {
        new MainUI(acc);
    }*/

