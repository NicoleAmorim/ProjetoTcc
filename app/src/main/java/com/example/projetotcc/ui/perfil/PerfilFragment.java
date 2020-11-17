package com.example.projetotcc.ui.perfil;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projetotcc.PaginaUsuario;
import com.example.projetotcc.R;
import com.example.projetotcc.config.Constants;
import com.squareup.picasso.Picasso;

import dominio.entidade.Servico;
import dominio.entidade.Usuario;

public class PerfilFragment extends Fragment {

    private PerfilViewModel mViewModel;
    TextView nome, email, telefone, serviço;
    ImageView image;
    Usuario usuario;
    Servico servico;

    public static PerfilFragment newInstance() {
        return new PerfilFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view;

        usuario = PaginaUsuario.usuario;
        view = inflater.inflate(R.layout.fragment_perfil, container, false);
        nome = view.findViewById(R.id.nomePerfilServico);
        email = view.findViewById(R.id.emailPerfil);
        telefone = view.findViewById(R.id.tellPerfil);

        image = view.findViewById(R.id.imagePerfil);
        nome.setText(usuario.getNome());
        email.setText(usuario.getEmail());
        telefone.setText(String.valueOf(usuario.getTel()));
        Picasso.get().load(usuario.getImageUrl()).into(image);

        try{
            servico = PaginaUsuario.servico;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PerfilViewModel.class);
        // TODO: Use the ViewModel
    }

}