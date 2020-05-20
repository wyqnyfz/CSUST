package com.coolweather.csust;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyData {
    public static String TAG = "MyData :;";

    //个人信息
    public static Map<String, String> myMassage = new HashMap<>();
    //课表
    public static ArrayList<String> myClass = new ArrayList<>();


    public static String userAccount = "201823060312";
    public static String userPassword = "(miMA)123";
    public static String userPassword_jiami = "";

    public static String URL_1 = "http://xk.csust.edu.cn/Logon.do?method=logon&flag=sess";
    public static String Cookie_1 = "";

    public static String URL_2 = "http://xk.csust.edu.cn/Logon.do?method=logon";

    public static String URL_3 = "";
    public static String Cookie_3 = "";

    //个人信息
    public static String URL_4 = "http://xk.csust.edu.cn/jsxsd/framework/xsMain_new.jsp";

    //课程安排
    public static String URL_5 = "http://xk.csust.edu.cn/jsxsd/framework/main_index_loadkb.jsp";

    public static String zhou = "";

    /**
     * f4
     */
    //是否已经登录
    public static boolean login = false;
    //照片
    public static int f4_sex = 0;
    //姓名
    public static String f4_name = "";
    //年级
    public static String f4_class = "";

    /**
     * login
     */
    //name
    public static String login_name = "";
    //密码
    public static String login_password = "";
    //记住密码
    public static boolean cb1 = false;
    //自动登录
    public static boolean cb2 = false;
}
