package com.random.ui;

import com.random.db.BaseDao;
import com.random.vo.AccountVo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class UpdateMyinfoUI extends JFrame implements ActionListener {
    private JLabel lblQQcode,lblnickName,lblage;
    private JLabel lblSex,lblNation,lblStar,lblBlood;
    private JLabel /*lblstatus,*/lblRemark,lblHobit,lblHeadImage;
    private JTextField txtQQcode,txtnickName,txtage,txtHobit;

    private  JRadioButton rbMale,rbRemale;
    private JComboBox cbNation,cbStar,cbBlood,cdHeadImg;
    private JTextArea toRemark;
    private  ButtonGroup bg;
    //保存
    private JButton btnSave,btnClose;

    private AccountVo myinfo;
    private MainUI mainUI;

    //背景
    private JLabel lblbg;

    String sNation[]={"汉族","壮族","回族","满族","维吾尔族","苗族","彝族","其他"};
    String sStar[]={"白羊座","金牛座","双子座","巨蟹座","狮子座","处女座","天秤座","天蝎座","射手座","摩羯座","水瓶座","双鱼座"};
    String sBlood[]={"A","B","AB","S"};
    String sImg[]={"head/0.png","head/1.png","head/2.png","head/3.png","head/4.png","head/5.png","head/6.png","head/7.png","head/8.png",
            "head/9.png","head/10.png"
    };
    private  ImageIcon[] headicon={new ImageIcon(sImg[0]),
            new ImageIcon(sImg[1]),
            new ImageIcon(sImg[2]),
            new ImageIcon(sImg[3]),
            new ImageIcon(sImg[4]),
            new ImageIcon(sImg[5]),
            new ImageIcon(sImg[6]),
            new ImageIcon(sImg[7]),
            new ImageIcon(sImg[8]),
            new ImageIcon(sImg[9]),
            new ImageIcon(sImg[10])
    };



    public  UpdateMyinfoUI(AccountVo myinfo,MainUI mainUI)  {
        super("修改用户信息");
        this.mainUI=mainUI;
        this.myinfo=myinfo;
        //小图标设置
        Image frame_icon=Toolkit.getDefaultToolkit().createImage("images/qqicon.jpeg");
        this.setIconImage(frame_icon);

        //背景
        /* Image image=new ImageIcon("images/bg1.jpeg").getImage();*/
        lblbg=new JLabel(new ImageIcon("images/bg1.jpeg"));
        lblbg.setLayout(null);
        add(lblbg);

        //标题
        JLabel title=new JLabel("用户资料",JLabel.CENTER);
        title.setFont(new Font("黑体",Font.BOLD,36));
        title.setForeground(Color.red);
        title.setBounds(200,10,200,40);
        lblbg.add(title);

        //绝对布局
        /*this.setLayout(null);*/
        lblQQcode=new JLabel("QQ号码",JLabel.RIGHT);
        lblnickName=new JLabel("昵称",JLabel.RIGHT);
        lblHeadImage=new JLabel("头像",JLabel.RIGHT);

        lblage=new JLabel("年龄",JLabel.RIGHT);
        lblSex=new JLabel("性别",JLabel.RIGHT);
        lblNation=new JLabel("民族",JLabel.RIGHT);
        lblStar=new JLabel("星座",JLabel.RIGHT);
        lblBlood=new JLabel("血型",JLabel.RIGHT);
        lblHobit=new JLabel("爱好",JLabel.RIGHT);

        lblRemark=new JLabel("备注",JLabel.RIGHT);

        txtQQcode =new JTextField(10);
        txtQQcode.setText(myinfo.getQqCode()+"");
        txtQQcode.setEditable(false);
        txtnickName=new JTextField(myinfo.getNickName());

        //设置选中头像
        cdHeadImg =new JComboBox(headicon);
        for(int i=0;i<sImg.length;i++)
        {
            if(sImg[i].equals(myinfo.getHeadImg()))
            {
                cdHeadImg.setSelectedIndex(i);
                break;
            }
        }
        if(myinfo.getSex().equals("男"))
        {
            rbMale=new JRadioButton("男",true);
            rbRemale =new JRadioButton("女");
        }
        else {
            rbMale=new JRadioButton("男");
            rbRemale =new JRadioButton("女",true);
        }
        txtage=new JTextField(myinfo.getAge()+"");


        bg=new ButtonGroup();
        bg.add(rbMale);
        bg.add(rbRemale);
  cbNation=new JComboBox(sNation);
        cbStar=new JComboBox(sStar);
        cbBlood=new JComboBox(sBlood);
        for(int i=0;i<sNation.length;i++)
        {
            if(sNation[i].equals(myinfo.getNation()))
            {
              cbNation.setSelectedIndex(i);
                break;
            }
        }

        for(int i=0;i<sStar.length;i++)
        {
            if(sStar[i].equals(myinfo.getStar()))
            {
                cbStar.setSelectedIndex(i);
                break;
            }
        }

        for(int i=0;i<sBlood.length;i++)
        {
            if(sBlood[i].equals(myinfo.getBlood()))
            {
               cbBlood.setSelectedIndex(i);
                break;
            }
        }

        txtHobit=new JTextField(myinfo.getHobit());
        InetAddress  addr= null;

        toRemark=new JTextArea(myinfo.getRemark());

        //布局
        lblQQcode.setBounds(30,60,100,20);
        txtQQcode.setBounds(130,60,150,20);
        lblnickName.setBounds(30,90,100,20);
        txtnickName.setBounds(130,90,150,20);

        lblHeadImage.setBounds(320,50,80,70);
        cdHeadImg.setBounds(400,50,80,60);


        lblage.setBounds(30,150,100,20);
        txtage.setBounds(130,150,150,20);
        lblSex.setBounds(320,150,80,20);
        rbMale.setOpaque(false);
        rbMale.setBounds(400,150,70,20);
        rbRemale.setOpaque(false);
        rbRemale.setBounds(440,150,70,20);

        lblNation.setBounds(30,180,100,20);
        cbNation.setBounds(130,180,150,20);
        lblStar.setBounds(320,180,80,20);
        cbStar.setBounds(400,180,150,20);

        lblBlood.setBounds(30,210,100,20);
        cbBlood.setBounds(130,210,150,20);
        lblHobit.setBounds(30,240,100,20);
        txtHobit.setBounds(130,240,400,20);


        lblRemark.setBounds(30,300,100,20);
        toRemark.setBounds(130,300,420,60);


        lblbg.add(lblQQcode);
        lblbg.add(txtQQcode);
        lblbg.add(lblnickName);
        lblbg.add(txtnickName);
        lblbg.add(lblHeadImage);
        lblbg.add(cdHeadImg);


        lblbg.add(lblage);
        lblbg.add(txtage);
        lblbg.add(lblSex);
        lblbg.add(rbMale);
        lblbg.add(rbRemale);

        lblbg.add(lblNation);
        lblbg.add(cbNation);
        lblbg.add(lblStar);
        lblbg.add(cbStar);
        lblbg.add(lblBlood);
        lblbg.add(cbBlood);
        lblbg.add(lblHobit);
        lblbg.add(txtHobit);

        lblbg.add(  lblRemark);
        lblbg.add(  toRemark);
        btnSave=new JButton("保存(S)");
        btnSave.setMnemonic('S');
        btnClose=new JButton("关闭(X)");
        btnClose.setMnemonic('X');
        btnSave.setBounds(180,400,100,40);
        btnSave.addActionListener(this);
        btnClose.addActionListener(this);
        btnClose.setBounds(350,400,100,40);
        lblbg.add(  btnSave);
        lblbg.add(  btnClose);

        setSize(600,500);
        setVisible(true);
        setLocationRelativeTo(null);//置于中央
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    };
    public  UpdateMyinfoUI(){};
    /*@Override
    public void paint(Graphics g) {
        Image image=new ImageIcon("images/bg1.jpeg").getImage();
        g.drawImage(image,0,0,this);

    }*/

    public static void main(String[] args) {
        new UpdateMyinfoUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==btnSave)
        {
            String nickname=txtnickName.getText().trim();
            if(nickname.equals(""))
            {
                JOptionPane.showMessageDialog(this,"请输入昵称");
                return;
            }

            int age=0;
            String sage=txtage.getText().trim();
            if(sage.equals(""))
            {
                JOptionPane.showMessageDialog(this,"请输入年龄");
                txtage.setText("0");
                return;
            }
            try{
                age=Integer.parseInt(sage);
            }catch (Exception ex){
                JOptionPane.showMessageDialog(this,"请输入0-150的年龄");
                return;
            }
            if(age<0||age>150)
            {
                JOptionPane.showMessageDialog(this,"请输入0-150的年龄");
                return;
            }


           myinfo.setNickName(txtnickName.getText().trim());
           myinfo.setHeadImg(sImg[cdHeadImg.getSelectedIndex()]);

            myinfo.setAge(age);
            if(rbMale.isSelected())
            {
              myinfo.setSex("男");
            }
            else{
               myinfo.setSex("女");
            }
          myinfo.setNation(sNation[cbNation.getSelectedIndex()]);
          myinfo.setStar(sStar[cbStar.getSelectedIndex()]);
          myinfo.setBlood(sBlood[cbBlood.getSelectedIndex()]);
          myinfo.setHobit(txtHobit.getText());

          myinfo.setRemark(toRemark.getText().trim());

            /* System.out.println(account.toString());*/



            BaseDao basedao=new BaseDao();
            System.out.println(1234123);
            System.out.println(myinfo);
            //保存修改后的信息到数据表
            myinfo=basedao.updaAccount(myinfo);

            JOptionPane.showMessageDialog(this,"修改成功");
            ImageIcon icon=new ImageIcon(myinfo.getHeadImg());
            String str=myinfo.getNickName()+"("+myinfo.getQqCode()+")"+"["+myinfo.getRemark()+"]";
            mainUI.lblmyinfo.setIcon(icon);
            mainUI.lblmyinfo.setText(str);
            dispose();


        }

        else if (e.getSource()==btnClose)
        {
            System.exit(0);
        }
    }
}
