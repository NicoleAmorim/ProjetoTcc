package com.example.projetotcc.ui.minhaLoja;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetotcc.Controller;
import com.example.projetotcc.PaginaUsuario;
import com.example.projetotcc.Servico;
import com.example.projetotcc.R;
import com.example.projetotcc.ui.perfil.PerfilFragment;

public class MinhaLojaFragment extends Fragment {

    private MinhaLojaViewModel mViewModel;
    public static RecyclerView rv;
    Servico servico;
    Controller controller;
    TextView nome, tipo, descricao;

    public static PerfilFragment newInstance() {
        return new PerfilFragment();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        controller = new Controller();
        servico = PaginaUsuario.servicop;
        View view;

        if( servico.getNome() == null) {
            view = inflater.inflate(R.layout.fragment_meu_servico_vazio, container, false);
        }else
        {
            view = inflater.inflate(R.layout.fragment_meu_servico, container, false);
        }
        try {

            nome = view.findViewById(R.id.nomeServicoItem);
            tipo = view.findViewById(R.id.tipoServicoItem);
            descricao = view.findViewById(R.id.descricaoServicoItem);
            nome.setText(servico.getNome());
            tipo.setText(servico.getTipo());
            descricao.setText(servico.getDescricao());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MinhaLojaViewModel.class);
        // TODO: Use the ViewModel
    }
}