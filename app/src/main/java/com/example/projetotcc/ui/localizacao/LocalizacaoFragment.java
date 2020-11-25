package com.example.projetotcc.ui.localizacao;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.projetotcc.R;
import com.example.projetotcc.ui.endereco.EnderecoFragment;
import com.example.projetotcc.ui.endereco.EnderecoViewModel;

public class LocalizacaoFragment extends Fragment {

    private LocalizacaoViewModel mViewModel;

    public static EnderecoFragment newInstance() {
        return new EnderecoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(LocalizacaoViewModel.class);
        // TODO: Use the ViewModel
    }

}