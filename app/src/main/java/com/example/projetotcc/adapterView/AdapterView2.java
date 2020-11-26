package com.example.projetotcc.adapterView;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.projetotcc.R;
import com.squareup.picasso.Picasso;

import java.util.Timer;
import java.util.TimerTask;

public class AdapterView2 extends PagerAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private int[] mResources;
    private String[] url;
    final Handler handler = new Handler();
    public Timer swipeTimer ;

    public AdapterView2(Context context, int[] u) {
        mContext = context;
        mResources = u;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mResources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.item_pager, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        try {
            imageView.setImageResource(mResources[position]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
    public void setTimer(final ViewPager myPager, int time, final int numPages, final int curPage){

        final Runnable Update = new Runnable() {
            int NUM_PAGES =numPages;
            int currentPage = curPage ;
            public void run() {
                if (currentPage == NUM_PAGES ) {
                    currentPage = 0;
                }
                myPager.setCurrentItem(currentPage++, true);
            }
        };

        swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(Update);
            }
        }, 1000, time*1000);

    }
}