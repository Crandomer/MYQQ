package com.random.db;

import com.random.base.Cmd;
import com.random.vo.AccountVo;

import java.io.DataOutput;
import java.sql.*;
import java.util.Random;
import java.util.Vector;

public class BaseDao {
    boolean bExist = false;

    //随机生成qq号码
    public int getQQcode() throws SQLException {

        Connection conn = DBConn.openDB();
        String sql = "select qqcode from account where qqcode=?";
        int qqcode = 0;
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            while (!bExist) {
                Random rand = new Random();
                //控制产生四位
                qqcode = rand.nextInt(8889) + 1000;
                pstm.setInt(1, qqcode);
                ResultSet rs = pstm.executeQuery();
                if (rs.next()) {
                    continue;
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return qqcode;
    }

    //随机生成端口号码
    public int getPort() throws SQLException {

        Connection conn = DBConn.openDB();
        String sql = "select port from account where  onlinestatus<>? and qqcode=?";
        PreparedStatement pstm = conn.prepareStatement(sql);
        int port = 0;
        while (!bExist) {
            Random rand = new Random();
            //控制产生四位
            port = rand.nextInt(64000) + 1024;
            pstm.setString(1, Cmd.STATUS[1]);
            pstm.setInt(2, port);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                continue;
            } else {
                break;
            }
        }


        return port;
    }

    //保存注册用户信息
    public AccountVo saveAccount(AccountVo acc) {
        //获取qq号码
        int qqcode;
        try {
            qqcode = getQQcode();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        int port;
        try {
            port = getPort();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        acc.setQqCode(qqcode);
        acc.setPort(port);
        acc.setOnlinestatus(Cmd.STATUS[1]);//离线
        System.out.println(acc.toString());
        String sql = "insert into account values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Connection conn = DBConn.openDB();
        try {
            int i = 1;
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(i++, acc.getQqCode());
            pstm.setString(i++, acc.getNickName());
            pstm.setString(i++, acc.getHeadImg());
            pstm.setString(i++, acc.getPassword());
            pstm.setInt(i++, acc.getAge());
            pstm.setString(i++, acc.getSex());
            pstm.setString(i++, acc.getStar());
            pstm.setString(i++, acc.getBlood());
            pstm.setString(i++, acc.getNation());
            pstm.setString(i++, acc.getHobit());

            pstm.setString(i++, acc.getIpAddr());
            pstm.setInt(i++, acc.getPort());
            pstm.setString(i++, acc.getOnlinestatus());
            pstm.setString(i++, acc.getRemark());
            System.out.println(pstm);
            i = pstm.executeUpdate();
            System.out.println(i);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return acc;
    }

    /*登录*/
    public AccountVo login(AccountVo account) {
        Connection conn = DBConn.openDB();
        String sql = "select * from account where qqCode=? and password =?";

        System.out.println(sql);
        int qqcode = 0;
        try {
            int port = getPort();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, account.getQqCode());
            pstmt.setString(2, account.getPassword());

           /* System.out.println(pstmt);
            System.out.println(1);*/
            ResultSet rs = pstmt.executeQuery();
            System.out.println(rs);
            if (rs.next()) {
                account.setNickName(rs.getString("nickName").trim());
                account.setHeadImg(rs.getString("headImg"));
                account.setAge(rs.getInt("age"));
                account.setSex(rs.getString("sex").trim());
                account.setStar(rs.getString("star").trim());
                account.setBlood(rs.getString("blood").trim());
                account.setNation(rs.getString("nation").trim());
                account.setHobit(rs.getString("hobit").trim());
                account.setIpAddr(rs.getString("ipAddr"));

                account.setPort(port);
                account.setOnlinestatus(Cmd.STATUS[0]);
                account.setRemark(rs.getString("remark").trim());
                //更改状态和端口
                sql = "update account set port=" + port + ",onlinestatus='" + Cmd.STATUS[0] + "'where qqCode=" + account.getQqCode();
                System.out.println(sql);
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(sql);
                pstmt.close();
                stmt.close();

            } else {
                account = null;
            }
        } catch (SQLException e) {
            //登录失败

            throw new RuntimeException(e);
        }
        return account;

    }

    //修改用户信息
    public AccountVo updaAccount(AccountVo acc) {

        String sql = "update account set nickName=?,headImg=?,age=?,sex=?,star=?,blood=?,nation=?,hobit=?,remark=? where qqCode=?";
        Connection conn = DBConn.openDB();
        try {
            int i = 1;
            PreparedStatement pstm = conn.prepareStatement(sql);

            pstm.setString(i++, acc.getNickName());
            pstm.setString(i++, acc.getHeadImg());
            pstm.setInt(i++, acc.getAge());
            pstm.setString(i++, acc.getSex());
            pstm.setString(i++, acc.getStar());
            pstm.setString(i++, acc.getBlood());
            pstm.setString(i++, acc.getNation());
            pstm.setString(i++, acc.getHobit());
            pstm.setString(i++, acc.getRemark());
            pstm.setInt(i++, acc.getQqCode());
            System.out.println(pstm);
            i = pstm.executeUpdate();
            System.out.println(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return acc;
    }

    //查找好友信息
  public Vector<AccountVo> getMyFriend(int myQqcode)
  {
      Connection conn = DBConn.openDB();
      String sql = "select a.*,f.groupName from account a left join friends  f  on a.qqCode=f.friendQQcode WHERE myQQcode=?";
      int qqcode = 0;
      Vector<AccountVo> vmyFreiend=new Vector<AccountVo>();
      try {
          int port = getPort();
          PreparedStatement pstmt = conn.prepareStatement(sql);
          pstmt.setInt(1, myQqcode);
          ResultSet rs = pstmt.executeQuery();
          System.out.println(rs);
         while (rs.next()) {
             AccountVo account = new AccountVo();
             account.setNickName(rs.getString("nickName").trim());
             account.setHeadImg(rs.getString("headImg"));
             account.setQqCode(rs.getInt("qqCode"));
             account.setAge(rs.getInt("age"));
             account.setSex(rs.getString("sex").trim());
             account.setStar(rs.getString("star").trim());
             account.setBlood(rs.getString("blood").trim());
             account.setNation(rs.getString("nation").trim());
             account.setHobit(rs.getString("hobit").trim());
             account.setIpAddr(rs.getString("ipAddr"));
             account.setPort(rs.getInt("port"));
             account.setOnlinestatus(rs.getString("onlinestatus"));
             account.setRemark(rs.getString("remark").trim());
             account.setGroupName(rs.getString("groupName").trim());
             vmyFreiend.add(account);
         }
          pstmt.close();
      } catch (SQLException e) {
         e.printStackTrace();
      }

      return vmyFreiend;
  }

}
