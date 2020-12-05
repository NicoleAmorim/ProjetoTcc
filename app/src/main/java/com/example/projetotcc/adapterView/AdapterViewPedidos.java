package com.example.projetotcc.adapterView;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.projetotcc.PaginaUsuario;
import com.example.projetotcc.R;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;

import java.util.Timer;
import java.util.TimerTask;

public class AdapterViewPedidos extends PagerAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private int[] mResources;
    private GroupAdapter adapter1, adapter2;
    final Handler handler = new Handler();
    public Timer swipeTimer ;
    private RecyclerView recyclerView;

    public AdapterViewPedidos(Context context, GroupAdapter adapter, GroupAdapter adapter2) {
        mContext = context;
        this.adapter1 = adapter;
        this.adapter2 = adapter2;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.item_pager_pedidos, container, false);

        recyclerView = (RecyclerView) itemView.findViewById(R.id.RecyclerPedidos);
        recyclerView.setLayoutManager(new LinearLayoutManager(PaginaUsuario.getContext));
        Log.i("teste", String.valueOf(position));
        if(position == 0) {
            recyclerView.setAdapter(adapter2);
            try {
                Log.i("teste", "p");
                PaginaUsuario.toolbar.setTitle("Mensagens");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }else if(position == 1)
        {
            recyclerView.setAdapter(adapter1);
            try {
                Log.i("teste", "s");
                PaginaUsuario.toolbar.setTitle("Mensagens");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
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