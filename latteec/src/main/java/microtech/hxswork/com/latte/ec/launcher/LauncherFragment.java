package microtech.hxswork.com.latte.ec.launcher;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.BindView;
import microtech.hxswork.com.latte.ec.login.LoginFragment;
import microtech.hxswork.com.latte.init.AccountManager;
import microtech.hxswork.com.latte.init.IUserChecker;
import microtech.hxswork.com.latte.middle.MiddleFragment;
import microtech.hxswork.com.latte.ec.R;
import microtech.hxswork.com.latte.ec.R2;
import microtech.hxswork.com.latte.ui.luncher.ILuncherListener;
import microtech.hxswork.com.latte.ui.luncher.OnluncherFinishTag;
import microtech.hxswork.com.latte.util.storage.LattePreference;

import com.nineoldandroids.view.ViewHelper;
/**
 * Created by microtech on 2017/11/14.
 */

public class LauncherFragment extends MiddleFragment {//app启动界面 左右轮播图
    @BindView(R2.id.luncher_timer)//圆形计时器
    CircleProgressbar circleProgressbar=null;
    @BindView(R2.id.luncher_startbutton)//立即跳转按钮
    Button luncher_startbutton;
    @BindView(R2.id.pager)
    ViewPager viewPager;
    @BindView(R2.id.luncher_linear)
    LinearLayout linearLayout;

    @BindView(R2.id.luncher_backimage)
    ImageView luncher_backimage;
    static final int NUM_PAGES = 5;
    PagerAdapter pagerAdapter;
    boolean isOpaque = true;
    boolean isClick = false;

    private ILuncherListener iLuncherListener = null;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof ILuncherListener)
        {
            iLuncherListener = (ILuncherListener) activity;
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_fragment;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        if(LattePreference.getAppFlag(ScrollLauncherTag.HAS_FIRST_LUNCHER_TAG.name()))
        {
            luncher_backimage.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.GONE);
            viewPager.setVisibility(View.GONE);
            initview_circleProgressbar();//设置时间按钮
        }else {
            luncher_backimage.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
            viewPager.setVisibility(View.VISIBLE);
            initview_circleProgressbar();//设置时间按钮
            initview_vipager();//设置滚动界面
        }

    }

    private void initview_circleProgressbar() {
        circleProgressbar.setOutLineColor(Color.parseColor("#d1dde6"));
        circleProgressbar.setInCircleColor(Color.parseColor("#ffffff"));
        circleProgressbar.setProgressColor(Color.parseColor("#1bb079"));
        circleProgressbar.setProgressLineWidth(5);
        circleProgressbar.setProgressType(CircleProgressbar.ProgressType.COUNT);
        circleProgressbar.setTimeMillis(10000);
        circleProgressbar.reStart();
        circleProgressbar.setCountdownProgressListener(1,progressListener);
        circleProgressbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isClick = true;
                LattePreference.setAppFlag(ScrollLauncherTag.HAS_FIRST_LUNCHER_TAG.name(),true);

                checkuser();//检查用户是否登录
               // start(new LoginFragment());
                //还需要检查用户是否登录APP
                //startActivity(new Intent(ZeroActivity.this,LoginActivity.class));
            }
        });
    }

    private void initview_vipager() {

        pagerAdapter = new ScreenSlidePagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setPageTransformer(true, new CrossfadePageTransformer());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == NUM_PAGES - 2 && positionOffset > 0) {
                    if (isOpaque) {
                        viewPager.setBackgroundColor(Color.TRANSPARENT);
                        isOpaque = false;
                    }
                } else {
                    if (!isOpaque) {
                        viewPager.setBackgroundColor(getResources().getColor(R.color.primary_material_light));
                        isOpaque = true;
                    }
                }
            }
            @Override
            public void onPageSelected(int position) {
                setIndicator(position);
                if (position == NUM_PAGES - 2) {
                    luncher_startbutton.setVisibility(View.VISIBLE);
                } else if (position < NUM_PAGES - 2) {
                    luncher_startbutton.setVisibility(View.GONE);
                } else if (position == NUM_PAGES - 1) {
                    //endTutorial();
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        buildCircles();

        luncher_startbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LattePreference.setAppFlag(ScrollLauncherTag.HAS_FIRST_LUNCHER_TAG.name(),true);
                checkuser();
               // start(new LoginFragment());
                //检查用户是否登录
                /*startActivity(new Intent(ZeroActivity.this,LoginActivity.class));
                SaveZero();
                finish();*/
            }
        });
    }

    private void setIndicator(int index){
        if(index < NUM_PAGES){
            for(int i = 0 ; i < NUM_PAGES - 1 ; i++){
                ImageView circle = (ImageView) linearLayout.getChildAt(i);
                if(i == index){
                    circle.setColorFilter(getResources().getColor(R.color.material_deep_teal_500));
                }else {
                    circle.setColorFilter(getResources().getColor(R.color.material_grey_900));
                }
            }
        }
    }

    private void buildCircles(){
        float scale = getResources().getDisplayMetrics().density;
        int padding = (int) (5 * scale + 0.5f);
        for(int i = 0 ; i < NUM_PAGES - 1 ; i++){
            ImageView circle = new ImageView(getContext());
            circle.setImageResource(R.drawable.shadow_right);
            circle.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            circle.setAdjustViewBounds(true);
            circle.setPadding(padding, 0, padding, 0);
            linearLayout.addView(circle);
        }
        setIndicator(0);
    }

    public class CrossfadePageTransformer implements ViewPager.PageTransformer {

        @Override
        public void transformPage(View page, float position) {
            int pageWidth = page.getWidth();

            View backgroundView = page.findViewById(R.id.welcome_fragment);
            View text_head= page.findViewById(R.id.heading);
            View text_content = page.findViewById(R.id.content);
            View welcomeImage01 = page.findViewById(R.id.welcome_01);
            View welcomeImage02 = page.findViewById(R.id.welcome_02);
            View welcomeImage03 = page.findViewById(R.id.welcome_03);
            View welcomeImage04 = page.findViewById(R.id.welcome_04);

            if(0 <= position && position < 1){
                ViewHelper.setTranslationX(page,pageWidth * -position);
            }
            if(-1 < position && position < 0){
                ViewHelper.setTranslationX(page,pageWidth * -position);
            }

            if(position <= -1.0f || position >= 1.0f) {
            } else if( position == 0.0f ) {
            } else {
                if(backgroundView != null) {
                    ViewHelper.setAlpha(backgroundView,1.0f - Math.abs(position));
                }

                if (text_head != null) {
                    ViewHelper.setTranslationX(text_head,pageWidth * position);
                    ViewHelper.setAlpha(text_head,1.0f - Math.abs(position));
                }

                if (text_content != null) {
                    ViewHelper.setTranslationX(text_content,pageWidth * position);
                    ViewHelper.setAlpha(text_content,1.0f - Math.abs(position));
                }

                if (welcomeImage01 != null) {
                    ViewHelper.setTranslationX(welcomeImage01,(float)(pageWidth/2 * position));
                    ViewHelper.setAlpha(welcomeImage01,1.0f - Math.abs(position));
                }

                if (welcomeImage02 != null) {
                    ViewHelper.setTranslationX(welcomeImage02,(float)(pageWidth/2 * position));
                    ViewHelper.setAlpha(welcomeImage02,1.0f - Math.abs(position));
                }

                if (welcomeImage03 != null) {
                    ViewHelper.setTranslationX(welcomeImage03,(float)(pageWidth/2 * position));
                    ViewHelper.setAlpha(welcomeImage03,1.0f - Math.abs(position));
                }
                if (welcomeImage04 != null) {
                    ViewHelper.setTranslationX(welcomeImage04,(float)(pageWidth/2 * position));
                    ViewHelper.setAlpha(welcomeImage04,1.0f - Math.abs(position));
                }
            }
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter
    {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            ProductTourFragment tp = null;
            switch(position){
                case 0:
                    tp = ProductTourFragment.newInstance(R.layout.luncher_image1);
                    break;
                case 1:
                    tp = ProductTourFragment.newInstance(R.layout.luncher_image2);
                    break;
                case 2:
                    tp = ProductTourFragment.newInstance(R.layout.luncher_image3);
                    break;
                case 3:
                    tp = ProductTourFragment.newInstance(R.layout.luncher_image4);
                    break;
                case 4:
                    tp = ProductTourFragment.newInstance(R.layout.luncher_image5);
                    break;
            }

            return tp;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    CircleProgressbar.OnCountdownProgressListener progressListener = new CircleProgressbar.OnCountdownProgressListener() {
        @Override
        public void onProgress(int what, int progress) {

            if (what == 1 && progress == 100 && !isClick)
            {
                LattePreference.setAppFlag(ScrollLauncherTag.HAS_FIRST_LUNCHER_TAG.name(),true);
                //还需要检查用户是否登录APP
                //startActivity(new Intent(ZeroActivity.this,LoginActivity.class));
                checkuser();
              //  start(new LoginFragment());
            }
        }
    };

    private void checkuser(){
            //检查用户是否登录
            if(LattePreference.getAppFlag(ScrollLauncherTag.HAS_FIRST_LUNCHER_TAG.name())){
                AccountManager.checkAccount(new IUserChecker() {
                    @Override
                    public void onLogin() {//以登录
                        if(iLuncherListener !=null)
                        {
                            iLuncherListener.onLuncherFinish(OnluncherFinishTag.SINGED);
                        }
                    }

                    @Override
                    public void onNotLogion() {//没有登录
                         if(iLuncherListener !=null){
                            iLuncherListener.onLuncherFinish(OnluncherFinishTag.NOT_SINGED);
                        }
                    }
                });
        }
    }

}
