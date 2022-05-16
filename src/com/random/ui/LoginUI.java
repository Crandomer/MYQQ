package com.random.ui;

import com.random.db.BaseDao;
import com.random.vo.AccountVo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class LoginUI  extends JFrame implements MouseListener, ActionListener,ItemListener {
    int x,y;
    JLabel lblMin, lblClose, lblHead, lblReg, lblForegetPwd;
    JButton btnLogin;
    JTextField txtQQcode;
    JPasswordField txtPassword;
    JComboBox cbQQcode;

    JCheckBox cbpwd, cbauto;

    HashMap<Integer, AccountVo> user = null;




    public LoginUI() {


        //隐藏标题栏
        setUndecorated(true);
        //隐藏图标
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image img = toolkit.getImage("images/qqicon.jpeg");
        setIconImage(img);
        JLabel bg = new JLabel(new ImageIcon("images/login(1).jpg"));
        add(bg);
        bg.setLayout(null);

        lblMin = new JLabel("_");
        lblMin.setForeground(Color.white);
        lblMin.setFont(new Font("黑体", Font.BOLD, 20));
        lblClose = new JLabel("x");
        lblClose.setForeground(Color.white);
        lblClose.setFont(new Font("黑体", Font.BOLD, 20));
        lblMin.setBounds(370, 0, 20, 20);
        lblClose.setBounds(400, 0, 20, 25);
        lblMin.addMouseListener(this);
        lblClose.addMouseListener(this);
        bg.add(lblMin);
        bg.add(lblClose);
        lblHead = new JLabel(new ImageIcon("images/bg1.jpeg"));
        lblReg = new JLabel("     ");
        txtQQcode = new JTextField("账号");
        lblForegetPwd = new JLabel("     ");
        cbQQcode = new JComboBox();
        txtPassword = new JPasswordField();
        btnLogin = new JButton("登 录");
        btnLogin.setFont(new Font("黑体", Font.BOLD, 16));
        btnLogin.setOpaque(false);
        cbQQcode.setBounds(130, 175, 195, 30);
        cbQQcode.setEditable(true);
        cbQQcode.setOpaque(true);
        txtPassword.setBounds(130, 205, 195, 30);
        lblReg.setBounds(325, 175, 80, 30);
     /*   lblReg.setFont(new Font("楷体",Font.BOLD,18));
        lblReg.setForeground(Color.BLUE);*/

        lblForegetPwd.setBounds(325, 205, 80, 30);
   /*    lblForegetPwd.setFont(new Font("楷体",Font.BOLD,18));
       lblForegetPwd.setForeground(Color.BLUE);*/

        bg.add(cbQQcode);
        bg.add(txtPassword);
        bg.add(lblReg);
        bg.add(lblForegetPwd);
        bg.add(btnLogin);

        lblReg.addMouseListener(this);
        lblForegetPwd.addMouseListener(this);
        btnLogin.setBounds(130, 265, 200, 32);
        btnLogin.setBackground(Color.getHSBColor(225, 224, 516));

        cbpwd = new JCheckBox("记住密码");
        cbauto = new JCheckBox("自动登录");

        cbpwd.setBounds(130, 235, 100, 20);
        cbpwd.setFont(new Font("黑体", Font.BOLD, 12));
        cbauto.setBounds(220, 235, 100, 20);
        cbpwd.setOpaque(false);
        cbauto.setOpaque(false);
        bg.add(cbpwd);
        bg.add(cbauto);
        //头像
        lblHead.setBounds(45, 175, 80, 80);
        bg.add(lblHead);

        //action事件
        lblReg.addMouseListener(this);
        lblForegetPwd.addMouseListener(this);
        cbpwd.addActionListener(this);
        cbauto.addActionListener(this);
        btnLogin.addActionListener(this);

        cbQQcode.addItemListener(this);
        //读取文件内容
        readFile();
        btnLogin.setBorderPainted(false);
        setSize(427, 308);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

    }

    public static void main(String[] args) {
        new LoginUI();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == lblMin) {
            this.setState(JFrame.HIDE_ON_CLOSE);
        } else if (e.getSource() == lblClose) {
            System.exit(0);
        } else if (e.getSource() == lblReg) {
            JOptionPane.showMessageDialog(this, "lblReg");
        } else if (e.getSource() == lblForegetPwd) {
            JOptionPane.showMessageDialog(this, "lblForegetPwd");
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        //登录功能
        if (e.getSource() == btnLogin) {
            String qqcode = "";
            String pwd = txtPassword.getText().trim();
            if (cbQQcode.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "请输入QQ号");
                return;
            }
            if (pwd.equals("")) {
                JOptionPane.showMessageDialog(this, "请输入密码");
                return;
            }
            qqcode = cbQQcode.getSelectedItem().toString();
            AccountVo account = new AccountVo();
            account.setQqCode(Integer.parseInt(qqcode));
            account.setPassword(pwd);
            account = new BaseDao().login(account);
            if (account == null) {
                JOptionPane.showMessageDialog(this, "登录失败，用户名或密码错误");
                return;
            } else {
                System.out.println(1);
                /*  JOptionPane.showMessageDialog(this,"登录成功");*/
                //保存文件
                save(account);
                //登录成功后，关闭当前窗口，显示主窗口
                dispose();
                new MainUI(account);
             /*   try
                {
                   *//* user=new HashMap<Integer,AccountVo>();
                    user.put(account.getQqCode(),account);
                    ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream("account.dat"));
                    oos.writeObject(user);
                    oos.flush();
                    oos.close();*//*
                 *//*  File file=new File("account.dat");
                    FileInputStream fis=new FileInputStream(file);
                    ObjectInputStream ois=new ObjectInputStream(fis);
                    user=(HashMap<Integer,AccountVo>)ois.readObject();
                    System.out.println(user);
*//*

                } catch(Exception ex){
                    ex.printStackTrace();
                }*/
            }

            /* JOptionPane.showMessageDialog(this,"登陆成功");*/
        } else if (e.getSource() == cbpwd) {

        }
    }

    //保存qq登陆成功的到文件
    public void save(AccountVo account) {
        HashMap<Integer, AccountVo> user = null;
        File file = new File("account.dat");

        try {
            if (!file.exists()) {

                file.createNewFile();
                user = new HashMap<Integer, AccountVo>();
            } else {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                user = (HashMap<Integer, AccountVo>) ois.readObject();
                fis.close();
                ois.close();
            }
            //新的登录的用户保存到hashMa中
            user.put(account.getQqCode(), account);
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("account.dat"));
            oos.writeObject(user);
            oos.flush();
            oos.close();
            System.out.println("wenjian");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //读取文件内容
    public void readFile() {

        try {
            File file = new File("account.dat");
            if (!file.exists()) {
                return;
            } else {

                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                //成员变量，不是局部变量
                user = (HashMap<Integer, AccountVo>) ois.readObject();
                Set<Integer> set = user.keySet();
                Iterator<Integer> it = set.iterator();
                while (it.hasNext()) {
                    cbQQcode.addItem(it.next());
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //下拉
    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == cbQQcode) {
            if (cbQQcode.getSelectedItem() != null) {
                int qqcode = Integer.parseInt(cbQQcode.getSelectedItem().toString());
                AccountVo account = user.get(qqcode);
                txtPassword.setText(account.getPassword());
                lblHead.setIcon(new ImageIcon(account.getHeadImg()));
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