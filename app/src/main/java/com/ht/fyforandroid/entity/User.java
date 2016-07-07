package com.ht.fyforandroid.entity;

import com.google.gson.Gson;
import com.ht.fyforandroid.util.PreferenceHelper;

/**
 * Created by niehongtao on 16/5/21.
 * 用户实体类
 */
public class User {
    private long userId; // 用户id
    private long userName; // 用户姓名
    private static User user;


    public long getUserId() {
        return userId;
    }

    public long getUserName() {
        return userName;
    }

    public void setUser(User user) {
        User.user = user;
    }

    private User() {
    }


    public static User getInstance() {
        if (user == null) {
            user = new User();
        }
        return user;
    }


    /**
     * 保存用户的登陆信息到本地
     *
     * @param user
     */
    public void saveUserInfo(User user) {
        this.setUser(user);
        String userString = new Gson().toJson(user);
        PreferenceHelper.getInstance().putStringValue("user", userString);
    }


    /**
     * 用户注销
     */
    public void logOut() {
        this.setUser(new User());
        clearnLoginInfo();
    }

    private void clearnLoginInfo() {
        PreferenceHelper.getInstance().removeValue("user");
    }

    /**
     * 更新用户的信息
     * @param user
     */
    public void updateUserInfo(User user) {
        this.setUser(user);
        String userString = new Gson().toJson(user);
        PreferenceHelper.getInstance().putStringValue("user", userString);
    }


    /**
     * 判断用户是否登陆了
     * @return
     */
    public boolean isLogined() {
        return userId > 0;
    }

}
