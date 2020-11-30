package com.example.projetotcc.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.projetotcc.adapterView.AdapterView2;
import com.example.projetotcc.PaginaUsuario;
import com.example.projetotcc.R;

import static com.example.projetotcc.PaginaUsuario.groupAdapter;
import static com.example.projetotcc.PaginaUsuario.rv;

public class HomeFragment extends Fragment {

    private HomeViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_inicio, container, false);
        ViewPager mViewPager = (ViewPager) view.findViewById(R.id.pager);

        int[] mResources = {
                R.drawable.woman
        };

        AdapterView2 adapterView = new AdapterView2(PaginaUsuario.context, mResources);
        mViewPager.setCurrentItem(0);
        mViewPager.setAdapter(adapterView);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
    }
}