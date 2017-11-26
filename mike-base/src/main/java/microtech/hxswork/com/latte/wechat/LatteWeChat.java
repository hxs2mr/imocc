package microtech.hxswork.com.latte.wechat;

import android.app.Activity;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import microtech.hxswork.com.latte.init.ConfigKeys;
import microtech.hxswork.com.latte.init.Latte;
import microtech.hxswork.com.latte.wechat.callbacks.IWeChatLoginCallBack;

/**
 * Created by microtech on 2017/11/17.
 */

public class LatteWeChat {
   public static final String APP_ID= Latte.getConfigureation(ConfigKeys.WX_CHAT_APP_ID);
    public static final String APP_SECRET = Latte.getConfigureation(ConfigKeys.WX_CHAT_APP_SECRET);
    private final IWXAPI WXAPI ;
    private IWeChatLoginCallBack mLoginCallback=null;
    private static final class Holder
    {
        private static final LatteWeChat INSTANCE = new LatteWeChat();
    }

    public  static LatteWeChat getInstance(){
        return Holder.INSTANCE;
    }
    private LatteWeChat()
    {
        final Activity activity = Latte.getConfigureation(ConfigKeys.ACTIVITY);
        WXAPI = WXAPIFactory.createWXAPI(activity,APP_ID,true);
        WXAPI.registerApp(APP_ID);
    }
    public final IWXAPI getWXAPI(){
        return WXAPI;
    }
    public LatteWeChat onLoginSuccess(IWeChatLoginCallBack callBack){
        this.mLoginCallback = callBack;
        return this;
    }
    public IWeChatLoginCallBack getLoginCallback(){
        return mLoginCallback;
    }
    public final void LogIn(){
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state ="random_state";
        WXAPI.sendReq(req);
    }
}
