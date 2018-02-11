package microtech.hxswork.com.latte.middle.bottom;

import android.provider.Settings;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import microtech.hxswork.com.latte.middle.MiddleFragment;

/**
 * Created by microtech on 2017/11/17.
 */

public abstract class BottomItemFragment extends MiddleFragment{
    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;

    @Override
    public boolean onBackPressedSupport() {
        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
            _mActivity.finish();
        } else {
            TOUCH_TIME = System.currentTimeMillis();
            Toast.makeText(_mActivity, "再按一次退出", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
