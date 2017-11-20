package microtech.hxswork.com.latte.ec.main.Index;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import microtech.hxswork.com.latte.ec.R;
import microtech.hxswork.com.latte.ui.recyclew.Rgb;

/**
 * Created by microtech on 2017/11/20.状态栏颜色的变化和逻辑判断
 */

@SuppressWarnings("unused")
public class TransluncentBehavior extends CoordinatorLayout.Behavior<Toolbar> {

    //顶部距离
    private int mDistanceY=20;
    //颜色变化速度
    private static final  int SHOW_SPEED=1;
    //定义颜色
    private final Rgb rgb_value= Rgb.create(255,124,2);

    public TransluncentBehavior(Context context, AttributeSet attrs) {
    super(context,attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, Toolbar child, View dependency) {
        return dependency.getId() == R.id.home_rv;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, Toolbar child, View directTargetChild, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, Toolbar child, View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        //增加滑动距离
        mDistanceY +=dy;
        //toolbar的高度
        final  int targetHeight = child.getBottom();
        //滑动时，并且距离小于toolbar高度的时候，跳转渐变色
        if(mDistanceY >0&&mDistanceY<=targetHeight){
            final  float scale = (float) mDistanceY/targetHeight;
            final float alpha = scale*255;
            child.setBackgroundColor(Color.argb((int) alpha, rgb_value.red(), rgb_value.green(), rgb_value.blue()));
        }else if(mDistanceY>targetHeight){

            child.setBackgroundColor(Color.rgb(rgb_value.red(),rgb_value.green(),rgb_value.blue()));
        }
    }

}
