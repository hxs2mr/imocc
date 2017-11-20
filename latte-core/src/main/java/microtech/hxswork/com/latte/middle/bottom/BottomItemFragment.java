package microtech.hxswork.com.latte.middle.bottom;

import android.provider.Settings;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import microtech.hxswork.com.latte.middle.MiddleFragment;

/**
 * Created by microtech on 2017/11/17.
 */

public abstract class BottomItemFragment extends MiddleFragment implements View.OnKeyListener{
    private long mExitTime =0 ;
    private static final int EXIT_TIME=2000;

    @Override
    public void onResume() {
        super.onResume();
       final View rootView = getView();
        if(rootView!=null){
            rootView.setFocusableInTouchMode(true);
            rootView.requestFocus();
            rootView.setOnKeyListener(this);
        }
    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis() - mExitTime) > mExitTime){
                Toast.makeText(getContext(),"在按一次退出",Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            }else {
                _mActivity.finish();
                if(mExitTime != 0){
                    mExitTime =0;
                }
            }
            return true;
        }
        return false;
    }
}
