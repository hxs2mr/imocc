package microtech.hxswork.com.latte.wechat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.StringCodec;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelpay.PayReq;

import microtech.hxswork.com.latte.net.RestClent;
import microtech.hxswork.com.latte.net.callback.IError;
import microtech.hxswork.com.latte.net.callback.IFailure;
import microtech.hxswork.com.latte.net.callback.ISuccess;

/**
 * Created by microtech on 2017/11/17.
 */

public abstract class BaseWXEntryActivity extends BaseWXActivity {
    protected abstract void onSignInSuccess(String userInfo);//用户登录后回掉

    //微信发送亲求到第三方应用后的回掉
    @Override
    public void onReq(BaseReq baseReq) {

    }

    //第三方应用发送请求到微信的回调
    @Override
    public void onResp(BaseResp baseResp) {
        final  String code = ((SendAuth.Resp)baseResp).code;
        final  StringBuilder authUrl = new StringBuilder();
        authUrl
                .append("https://api.weixin.qq.com/sns/oauth2/access_token?appid=")
                .append(LatteWeChat.APP_ID)
                .append("&secret=")
                .append(LatteWeChat.APP_SECRET)
                .append("&code=")
                .append(code)
                .append("&grant_type=authorization_code");
        getAuth(authUrl.toString());
    }

    private  void getAuth(String url){
        RestClent.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {

                        final JSONObject authObj = JSON.parseObject(response);
                        final String accessToken = authObj.getString("access_toke");
                        final  String openId =authObj.getString("openid");

                        final StringBuilder userInfoUrl = new StringBuilder();
                        userInfoUrl
                                .append("https://api.weixin.qq.com/sns/userinfo?access_token=")
                                .append(accessToken)
                                .append("&openid=")
                                .append(openId)
                                .append("&lang=")
                                .append("zh_CN");
                        getUserInfo(userInfoUrl.toString());
                    }
                })
                .build()
                .get();
    }
    //获取用户的信息
    private  void getUserInfo(String userInfoUrl){
        RestClent
                .builder()
                .url(userInfoUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                            onSignInSuccess(response);
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onIFailure() {

                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {

                    }
                })
                .build()
                .get();
    }
}
