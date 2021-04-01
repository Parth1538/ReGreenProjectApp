package com.kf.regreen.regreenproject.Activities;

import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kf.regreen.regreenproject.Fragments.FirstFragment;
import com.kf.regreen.regreenproject.Fragments.SecondFragment;
import com.kf.regreen.regreenproject.Fragments.ThirdFragment;
import com.kf.regreen.regreenproject.R;
import com.viewpagerindicator.CirclePageIndicator;


public class MainActivity extends FragmentActivity {

    ViewPager pager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    LinearLayout circlePageIndicatorContainerC;
    CirclePageIndicator circlePageIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pager = (ViewPager) findViewById(R.id.viewPager);

        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        pager.addOnPageChangeListener(new DetailOnPageChangeListener());

        circlePageIndicatorContainerC = (LinearLayout)findViewById(R.id.circlePageIndicatorContainer);

        circlePageIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        circlePageIndicator.setViewPager(pager);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.fragment_1_color));
        }

    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            Log.d("Pos:", "" + pos);
            switch (pos) {

                case 0:
                    return FirstFragment.newInstance("FirstFragment, Instance 1");
                case 1:
                    return SecondFragment.newInstance("SecondFragment, Instance 1");
                case 2:
                    return ThirdFragment.newInstance("ThirdFragment, Instance 1");
                default:
                    return ThirdFragment.newInstance("ThirdFragment, Default");
            }
        }


        @Override
        public int getCount() {
            return 3;
        }
    }

    public class DetailOnPageChangeListener extends ViewPager.SimpleOnPageChangeListener {

        private int currentPage;

        @Override
        public void onPageSelected(int position) {
            currentPage = position;

            if(currentPage==0)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Window window = getWindow();
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(getResources().getColor(R.color.fragment_1_color));

                    circlePageIndicatorContainerC.setBackgroundColor(getResources().getColor(R.color.fragment_1_color));
                }
            if(currentPage==1)
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Window window = getWindow();
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(getResources().getColor(R.color.fragment_2_color));

                    circlePageIndicatorContainerC.setBackgroundColor(getResources().getColor(R.color.fragment_2_color));

                }
            }

            if(currentPage==2)
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Window window = getWindow();
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(getResources().getColor(R.color.fragment_3_color));

                    circlePageIndicatorContainerC.setBackgroundColor(getResources().getColor(R.color.fragment_3_color));

                }
            }
            Log.e("CurrentPage",""+currentPage);

        }

        public final int getCurrentPage() {
            return currentPage;
        }
    }
}


