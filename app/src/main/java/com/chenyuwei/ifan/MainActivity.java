package com.chenyuwei.ifan;

import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chenyuwei.ifan.util.TabAdapter;
import com.chenyuwei.ifan.util.Tool;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private RelativeLayout loContainer;
    private LinearLayout loList;
    private View rootView;
    private RelativeLayout.LayoutParams pmList;
    private RelativeLayout.LayoutParams pmContainerS;
    private RelativeLayout.LayoutParams pmContainerL;

    private RelativeLayout.LayoutParams pmViewPagerS;
    private RelativeLayout.LayoutParams pmViewPagerL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        loContainer = (RelativeLayout) findViewById(R.id.loContainer);
        loList = (LinearLayout) findViewById(R.id.loList);
        rootView = findViewById(R.id.rootView);

        pmContainerL = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        pmViewPagerS = new RelativeLayout.LayoutParams(Tool.getScreenWidth(this)*5/10, Tool.getScreenHeight(this)*6/10);
        pmViewPagerL = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        rootView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        int height = rootView.getHeight();
                        if (height != 0){
                            //设置初始状态为缩小状态
                            pmList=new RelativeLayout.LayoutParams(Tool.getScreenWidth(MainActivity.this), height/2);
                            pmList.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                            pmContainerS=new RelativeLayout.LayoutParams(Tool.getScreenWidth(MainActivity.this), height/2);
                            pmContainerS.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                            loList.setLayoutParams(pmList);
                            loContainer.setLayoutParams(pmContainerS);
                            viewPager.setLayoutParams(pmViewPagerS);
                            if (Build.VERSION.SDK_INT < 16) {
                                rootView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                            } else {
                                rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            }
                        }
                    }
                });
        TabAdapter adapterTab = new TabAdapter(getSupportFragmentManager());
        adapterTab.addFragment(new EmptyFragment().setBg(R.drawable.bg1));
        adapterTab.addFragment(new EmptyFragment().setBg(R.drawable.bg2));
        adapterTab.addFragment(new EmptyFragment().setBg(R.drawable.bg3));
        adapterTab.addFragment(new EmptyFragment().setBg(R.drawable.bg4));
        viewPager.setAdapter(adapterTab);

        viewPager.setOffscreenPageLimit(4);
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            int flag = 0 ;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        flag = 0 ;
                        break ;
                    case MotionEvent.ACTION_MOVE:
                        flag = 1 ;
                        break ;
                    case  MotionEvent.ACTION_UP :
                        if (flag == 0) {
                            Animation animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.anim_to_big);
                            animation.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    //变大
                                    loContainer.setLayoutParams(pmContainerL);
                                    viewPager.setLayoutParams(pmViewPagerL);
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                            viewPager.clearAnimation();
                            viewPager.startAnimation(animation);
                        }
                        break ;
                }
                return false;
            }
        });
    }

    public void toSmall(){
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.anim_to_small);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //变小
                loContainer.setLayoutParams(pmContainerS);
                viewPager.setLayoutParams(pmViewPagerS);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        viewPager.clearAnimation();
        viewPager.startAnimation(animation);
    }
}
