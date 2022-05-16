package com.random.vo;

import java.io.Serializable;

public class FriendVo implements Serializable {
    private int friendid;
    private int myQQcode;
    private int friendQQcode;
    private String groupName;

    public int getFriendid() {
        return friendid;
    }

    public void setFriendid(int friendid) {
        this.friendid = friendid;
    }

    public int getMyQQcode() {
        return myQQcode;
    }

    public void setMyQQcode(int myQQcode) {
        this.myQQcode = myQQcode;
    }

    public int getFriendQQcode() {
        return friendQQcode;
    }

    public void setFriendQQcode(int friendQQcode) {
        this.friendQQcode = friendQQcode;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
