package microtech.hxswork.com.latte.ec.pay;

import android.app.Activity;
import android.nfc.NdefRecord;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;

import com.alipay.sdk.app.PayTask;

import microtech.hxswork.com.latte.ui.LatteLoader;
import retrofit2.Call;

/**
 * Created by microtech on 2017/11/22.支付宝支付必须异步处理
 */

public class PayAsyncTask extends AsyncTask<String,Void,String> {

    private final Activity ACTIVITY;
    private PayResultListener LISTENER;

    public PayAsyncTask(Activity activity, PayResultListener listener) {
        this.ACTIVITY = activity;
        this.LISTENER = listener;
    }

    //订单支付成功
    private static final String Al_PAY_STATUS_SUCCESS="9000";
    //订单处理中
    private static final String Al_PAY_STATUS_PAYING="8000";

    //订单支付失败
    private static final String Al_PAY_STATUS_FAIL="4000";
    //订单支付取消
    private static final String Al_PAY_STATUS_CANCEL="6001";

    //订单支付连接网络错误
    private static final String Al_PAY_STATUS_CONNECT_ERROR="6002";

    @Override
    protected String doInBackground(String... strings) {
        final String alPaySign = strings[0];
        final PayTask payTask = new PayTask(ACTIVITY);
        return payTask.pay(alPaySign,true);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        LatteLoader.stopLoading();
        final  PayResult payResult =new PayResult(s);
        //支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签

        final String resultInfo = payResult.getResult();
        final String resultStatus = payResult.getResultStatus();
        Log.d("al_Pay_result",resultInfo);
        Log.d("al_Pay_resultStatus",resultStatus);

        switch (resultStatus){
            case Al_PAY_STATUS_SUCCESS:
                    if(LISTENER != null){
                        LISTENER.onPaySuccess();
                    }
                break;
            case  Al_PAY_STATUS_PAYING:
                if(LISTENER != null){
                    LISTENER.onPaying();
                }
                break;
            case  Al_PAY_STATUS_FAIL:
                if(LISTENER != null){
                    LISTENER.onPayFail();
                }
                break;
            case  Al_PAY_STATUS_CANCEL:
                if(LISTENER != null){
                    LISTENER.onPayCancel();
                }
                break;
            case Al_PAY_STATUS_CONNECT_ERROR:
                if(LISTENER != null){
                    LISTENER.onPayConnectError();
                }
                break;
            default:
                break;
        }
    }
}