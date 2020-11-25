package com.example.projetotcc.ui.portifolio;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.projetotcc.R;
import com.example.projetotcc.ui.infoServico.InfoServicoFragment;
import com.squareup.picasso.Picasso;

import dominio.entidade.Servico;

public class PortifolioFragment extends Fragment {

    private PortifoliolViewModel mViewModel;
    private int PICK_IMAGE_REQUEST = 1;
    private Servico servico;
    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private ImageView image4;
    private Uri filePath;
    private boolean um = false, dois = false, tres = false, quatro = false;

    public static PortifolioFragment newInstance() {
        return new PortifolioFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_portifolio, container, false);
        servico = InfoServicoFragment.servico;
        image1 = view.findViewById(R.id.imagePortifolio1);
        image2 = view.findViewById(R.id.imagePortifolio2);
        image3 = view.findViewById(R.id.imagePortifolio3);
        image4 = view.findViewById(R.id.imagePortifolio4);

        Picasso.get().load(servico.getImagemUrl()).into(image1);
        try {

                Picasso.get().load(servico.getImagemUrl2()).into(image2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {

            Picasso.get().load(servico.getImagemUrl3()).into(image3);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
                Picasso.get().load(servico.getImagemUrl4()).into(image4);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PortifoliolViewModel.class);
        // TODO: Use the ViewModel
    }
}