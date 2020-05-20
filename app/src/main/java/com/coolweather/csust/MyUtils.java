package com.coolweather.csust;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MyUtils {

    private static String TAG = "MyUtils:";

    public static String name;
    public static String password;
    public static Handler mHandler;




    /**
     * 1,获取密钥
     */
    public static void get_1(Handler handler) {
        mHandler = handler;
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(MyData.URL_1)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "False.........");
            }
            public void onResponse(Call call, Response response) throws IOException {
                if(response.code()== 200){
                    Log.d(TAG, "获取密钥成功");
                    Headers headers = response.headers();
                    //获取Cookie_1
                    MyData.Cookie_1 = headers.values("Set-Cookie").get(0);
                    MyData.Cookie_1 = MyData.Cookie_1.substring(0,MyData.Cookie_1.indexOf(";"));
                    Log.d(TAG, MyData.Cookie_1);
                    //获取加密后密码
                    MyData.userPassword_jiami = getPassword(MyData.userAccount,MyData.userPassword,response.body().string());

                    post_2();
                }
            }
        });


    }

    /**
     * 计算加密密码
     */
    public static String getPassword(String userAccount, String userPassword, String dataStr) {
        String sCode = dataStr.split("#")[0];
        String sxh = dataStr.split("#")[1];
        String code = userAccount+"%%%"+userPassword;
        String encoded = "";
        for(int i=0;i<code.length();i++){
            if(i<20){
                encoded = encoded + code.substring(i,i+1)+sCode.substring(0,Integer.valueOf(sxh.substring(i,i+1)));
                sCode = sCode.substring(Integer.valueOf(sxh.substring(i,i+1)),sCode.length());
            }else{
                encoded = encoded + code.substring(i,code.length());
                i = code.length();
            }
        }
        return encoded;
    }

    /**
     * 2，带加密密码请求
     */
    private static void post_2() {
        final OkHttpClient okHttpClient = new OkHttpClient().newBuilder().followRedirects(false).build();
        RequestBody requestBody = new FormBody.Builder()
                .add("userAccount",MyData.userAccount)
                .add("userPassword", "")
                .add("encoded",MyData.userPassword_jiami)
                .build();
        final Request request = new Request.Builder()
                .url(MyData.URL_2)
                .post(requestBody)
                .addHeader("Cookie",MyData.Cookie_1)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "False.........");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.code()== 302){
                    Log.d(TAG, "获取新密码成功");
                    //获取URL_3
                    Headers headers = response.headers();
                    MyData.URL_3 = headers.values("Location").get(0);
                    get_3(okHttpClient);
                }else {
                    sentHanderMassage(3, 1);
                }
            }
        });
    }

    /**
     * 3，获得Cookie3
     */
    private static void get_3(final OkHttpClient okHttpClient) {
        final Request request = new Request.Builder()
                .url(MyData.URL_3)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "False.........");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "code_3........."+response.code());
                if(response.code()== 302){
                    Headers headers = response.headers();
                    Log.d(TAG, "第3次获取的body："+headers.toString());
                    //获取MyData.Cookie_3
                    MyData.Cookie_3 = headers.values("Set-Cookie").get(0);
                    MyData.Cookie_3 = MyData.Cookie_3.substring(0,MyData.Cookie_3.indexOf(";"));
                    Log.d(TAG,"MyData.Cookie_3: "+ MyData.Cookie_3);
                    get_4(okHttpClient);
                    post_5(okHttpClient);
                }

            }
        });
    }

    /**
     * 获取个人信息
     */
    private static void get_4(OkHttpClient okHttpClient){
        final Request request = new Request.Builder()
                .url(MyData.URL_4)
                .addHeader("Cookie", MyData.Cookie_3)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "False.........");
                Message m = new Message();
              sentHanderMassage(1, 0);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "code_4........."+response.code());
                if(response.code()== 200){
                    String html = response.body().string();
                    Log.d(TAG, html);
                    Document document = Jsoup.parse(html);
                    Elements ele = document.getElementsByClass("main_text main_color");
                    for(Element e : ele){
                        MyData.zhou = e.text();
                    }
                    Elements elements1 = document.getElementsByAttributeValue("class", "f14 blue middletopdwxxtit");
                    Elements elements2 = document.getElementsByAttributeValue("class", "middletopdwxxcont");
                    MyData.myMassage.clear();
                    for (int i = 1; i < 6; i++) {
                        String key = elements1.get(i).text().replace("：", "");
                        String val = elements2.get(i).text();
                        MyData.myMassage.put(key,val );
                        if(i == 1){
                            MyData.f4_name = val;
                        }
                        if(i == 5){
                            MyData.f4_class = val;
                        }
                    }
                   sentHanderMassage(1, 1);
                }
            }
        });
    }

    /**
     * 获取课程
     */
    private static void post_5(OkHttpClient okHttpClient){

        //获取时间
        String time = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        RequestBody requestBody = new FormBody.Builder()
                .add("rq", time)
                .add("sjmsValue", "94673FF3230E4769E0533C41FF0A2703")
                .build();
        final Request request = new Request.Builder()
                .url(MyData.URL_5)
                .addHeader("Cookie", MyData.Cookie_3)
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "False.........");

                sentHanderMassage(2, 0);
            }
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "code_5........."+response.code());
                if(response.code()== 200){
                    String html = response.body().string();
//                    Log.d(TAG, html);
                    Document document = Jsoup.parse(html);
                    Elements elements = document.getElementsByTag("td");
                    Log.d(TAG, "=================="+elements.size());
                    MyData.myClass.clear();
                    for (int i = 0; i < elements.size(); i++) {
                        if(elements.get(i).text().equals("")){
                            MyData.myClass.add("0");
                        }else{
                            Element e = elements.get(i);
                            String[] titles = e.toString().split("\"");
                            String ss = "";
                            if(titles.length>1){
                                ss = e.text()+"<br/>"+titles[1];
                            }else{
                                ss = e.text();
                            }
                            ss = ss.replace("<br/>", ",");
                            String[] strings = ss.split(",");
                            String s1 = strings[0].substring(0, strings[0].length()-2);
                            String[] s2 = strings[strings.length-1].split("：");
                            if(s2.length == 2){
                                s2[0] = s2[1];
                            }
                            ss = s1+","+s2[0];
//                            Log.d(TAG, ss);
                            MyData.myClass.add(ss);
                        }
                    }
                    //登录状态
                    MyData.login = true;
                    sentHanderMassage(2, 1);
                }
            }
        });

    }

    /**
     * 发送massag
     * @param what
     * @param obj
     */
    public static void sentHanderMassage(int what, int obj){
        Message m = new Message();
        m.what = what;
        m.obj = obj;
        mHandler.sendMessage(m);
    }

}
