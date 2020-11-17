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

import com.example.projetotcc.models.CallBacks;
import com.example.projetotcc.PaginaUsuario;
import dominio.entidade.Servico;
import com.example.projetotcc.R;

public class MinhaLojaFragment extends Fragment {

    private MinhaLojaViewModel mViewModel;
    public static RecyclerView rv;
    Servico servico;
    CallBacks callBacks;
    TextView nome, tipo, descricao;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        callBacks = new CallBacks();
        servico = PaginaUsuario.servicop;
        View view;
try {
    if (servico.getNome().isEmpty()) {
        view = inflater.inflate(R.layout.fragment_meu_servico_vazio, container, false);
    } else {
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
} catch (Exception e) {
    e.printStackTrace();
    view = inflater.inflate(R.layout.fragment_meu_servico_vazio, container, false);
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