package microtech.hxswork.com.latte.ec.pay;

/**
 * Created by microtech on 2017/11/22.
 */

public interface PayResultListener {
    void  onPaySuccess();//付款成功
    void onPaying();//正在支付
    void onPayFail();//付款失败
    void onPayCancel();//取消付款
    void onPayConnectError();//付款连接错误
}
