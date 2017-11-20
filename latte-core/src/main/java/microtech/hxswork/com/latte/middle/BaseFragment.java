package microtech.hxswork.com.latte.middle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * Created by microtech on 2017/11/10.
 */

public abstract class BaseFragment extends SwipeBackFragment{
    public abstract Object setLayout();
    @SuppressWarnings("SpellCheckingInspection")
    //private Unbinder mUnbinder =null;
   private Unbinder mUnbinder =null;
    public abstract  void onBindView( @Nullable Bundle savedInstanceState,View rootView);
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = null;
        if (setLayout() instanceof Integer) {
            rootView = inflater.inflate((Integer) setLayout(), container, false);//绑定布局
        } else {
            rootView = (View) setLayout();
        }
        if (rootView != null)
        {
            mUnbinder=ButterKnife.bind(this,rootView);
            onBindView(savedInstanceState,rootView);
        }
        return rootView;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mUnbinder != null)//接触绑定
        {
            mUnbinder.unbind();
        }
    }
}
