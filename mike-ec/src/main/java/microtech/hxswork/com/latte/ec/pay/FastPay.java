package microtech.hxswork.com.latte.ec.pay;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;

import microtech.hxswork.com.latte.ec.R;
import microtech.hxswork.com.latte.init.ConfigKeys;
import microtech.hxswork.com.latte.init.Latte;
import microtech.hxswork.com.latte.middle.MiddleFragment;
import microtech.hxswork.com.latte.net.RestClent;
import microtech.hxswork.com.latte.net.callback.ISuccess;
import microtech.hxswork.com.latte.ui.LatteLoader;
import microtech.hxswork.com.latte.wechat.LatteWeChat;

/**
 * Created by microtech on 2017/11/22.支付
 */

public class FastPay implements View.OnClickListener {
    //设置支付回调监听
    private   PayResultListener mParListener=null;

    private  Activity mActivity=null;

    private AlertDialog mDialog = null;
    private int mOrderID = -1;

    private  FastPay(MiddleFragment fragment)
    {
        this.mActivity = fragment.getProxyActivity();
        this.mDialog = new AlertDialog.Builder(fragment.getContext()).create();
    }
    public static FastPay create(MiddleFragment fragment){
        return new FastPay(fragment);
    }

    public void beginPayDialog(){
        mDialog.show();
        final Window window = mDialog.getWindow();
        if(window !=null)
        {
            window.setContentView(R.layout.pay_dialog);
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.anim_bottom_to_top);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //设置属性
            final WindowManager.LayoutParams params =window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(params);
            window.findViewById(R.id.pay_zhifubao).setOnClickListener(this);
            window.findViewById(R.id.pay_weichat).setOnClickListener(this);
            window.findViewById(R.id.pay_cancel).setOnClickListener(this);
        }
    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.pay_zhifubao) {//支付宝支付
            alPay(mOrderID);
            mDialog.cancel();
        } else if (i == R.id.pay_weichat) {//微信支付
            weChatPay(mOrderID);
            mDialog.cancel();
        } else if (i == R.id.pay_cancel) {//取消支付

            mDialog.cancel();
        }
    }


    public FastPay setPayResultListener(PayResultListener listener){
        this.mParListener = listener;
        return this;
    }
    public FastPay setPayOrderId(int  OrderID){
        this.mOrderID = OrderID;
        return this;
    }
    public final void alPay(int orderId)//支付宝
    {
        final  String singurl ="url"+orderId;
        //获取签名字符串
        RestClent.builder()
                .url(singurl)
                .success(new ISuccess() {//和自己的服务器交互
                    @Override
                    public void onSuccess(String response) {
                        final String paySign = JSON.parseObject(response).getString("obj");//服务端返回的数据信息
                        //必须是异步请求
                        final  PayAsyncTask payAsyncTask = new PayAsyncTask(mActivity,mParListener);
                        payAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,paySign);
                    }
                })
                .build()
                .post();
    }
    private  final  void weChatPay(int orderId){
        LatteLoader.stopLoading();
        final String weChatUrl = ""+orderId;
        final IWXAPI iwxapi = LatteWeChat.getInstance().getWXAPI();
        final String app_id =  Latte.getConfigureation(ConfigKeys.WX_CHAT_APP_ID);
        iwxapi.registerApp(app_id);
        RestClent.builder()
                .url(weChatUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final JSONObject result = JSON.parseObject(response).getJSONObject("result");//与服务器交互返回的信息

                        final String prepayId = result.getString("prepayid");//微信规定必须返回
                        final String partnerId = result.getString("partnerid");
                        final String packageValue = result.getString("package");
                        final String timestamp = result.getString("timestamp");
                        final String nonceStr = result.getString("noncestr");
                        final String paySign = result.getString("sign");

                    final PayReq payReq = new PayReq();
                        payReq.appId = app_id;
                        payReq.prepayId = prepayId;
                        payReq.partnerId = partnerId;
                        payReq.packageValue = packageValue;
                        payReq.timeStamp = timestamp;
                        payReq.nonceStr = nonceStr;
                        payReq.sign = paySign;

                        iwxapi.sendReq(payReq);
                    }
                })
                .build()
                .post();
    }
}
