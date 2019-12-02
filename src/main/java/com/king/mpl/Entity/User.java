package com.king.mpl.Entity;

public class User {
    private int id;
    private String openid;
    private String avagar;
    private String nickname;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getAvagar() {
        return avagar;
    }

    public void setAvagar(String avagar) {
        this.avagar = avagar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", openid='" + openid + '\'' +
                ", avagar='" + avagar + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
