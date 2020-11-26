package com.example.projetotcc.ui.portifolio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.projetotcc.adapterView.AdapterView;
import com.example.projetotcc.PaginaUsuario;
import com.example.projetotcc.R;
import com.example.projetotcc.ui.infoServico.InfoServicoFragment;

import dominio.entidade.Servico;

public class PortifolioFragment extends Fragment {

    private PortifoliolViewModel mViewModel;
    private int PICK_IMAGE_REQUEST = 1;
    private Servico servico;
    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private ImageView image4;

    public static PortifolioFragment newInstance() {
        return new PortifolioFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_editar_portifolio, container, false);
        servico = InfoServicoFragment.servico;

        ViewPager mViewPager = (ViewPager) view.findViewById(R.id.pagerPortifolio);

        String[] mResources = {
                servico.getImagemUrl(),
                servico.getImagemUrl2(),
                servico.getImagemUrl3(),
                servico.getImagemUrl4()
        };

        AdapterView adapterView = new AdapterView(PaginaUsuario.context, mResources);
        mViewPager.setCurrentItem(0);
        adapterView.setTimer(mViewPager,7, 4, 2);
        mViewPager.setAdapter(adapterView);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PortifoliolViewModel.class);
        // TODO: Use the ViewModel
    }
}