package com.mm.mmcalendar.bmobmodel;

/**
 * Created by Administrator on 2016/11/3.
 */
public class UserBean extends BaseBmob{

    public static final String restApiUrl="https://api.bmob.cn/1/classes/_User";


    private String username;
    private String password;


    public UserBean(String username, String password) {
        this.username = username;
        this.password = password;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
