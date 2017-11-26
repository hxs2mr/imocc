package microtech.hxswork.com.latte.ec.login;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import microtech.hxswork.com.latte.ec.database.DataBaseManager;
import microtech.hxswork.com.latte.ec.database.UserProfile;
import microtech.hxswork.com.latte.init.AccountManager;

/**
 * Created by microtech on 2017/11/15.
 */

public class LoginHandler {
    public static void onSignIn(String response,ILoginListener loginListener){
        final JSONObject JSON = com.alibaba.fastjson.JSON.parseObject(response).getJSONObject("data");//解析返回的json数据  xxx：表示是json中相应的字段
        final long userId = JSON.getLong("userId");//获取json数据中的userId字段
        final String name = JSON.getString("name");
        final String avatar = JSON.getString("avatar");
        final String gender = JSON.getString("sex");
        final String address = JSON.getString("address");
        final String birthday = JSON.getString("birthday");
        final String phone = JSON.getString("phone");

        final UserProfile profile = new UserProfile(userId,name,avatar,gender,address,birthday,phone);
        DataBaseManager.getInstance().getDao().insert(profile);//插入数据

        //保存用户的状态 已经注册并登录成功
        AccountManager.setLoginState(true);
        loginListener.onLoginSuccess();
    }

    public static void onSignUp(String response,ILoginListener loginListener){
        final JSONObject JSON = com.alibaba.fastjson.JSON.parseObject(response).getJSONObject("data");//解析返回的json数据  xxx：表示是json中相应的字段
        final long userId = JSON.getLong("userId");//获取json数据中的userId字段
        final String name = JSON.getString("name");
        final String avatar = JSON.getString("avatar");
        final String gender = JSON.getString("sex");
        final String address = JSON.getString("address");
        final String birthday = JSON.getString("birthday");
        final String phone = JSON.getString("phone");

        final UserProfile profile = new UserProfile(userId,name,avatar,gender,address,birthday,phone);
        DataBaseManager.getInstance().getDao().insert(profile);//插入数据

        //保存用户的状态 已经注册并登录成功
        AccountManager.setLoginState(true);
        loginListener.OnResgisterSuuecc();
    }
}
