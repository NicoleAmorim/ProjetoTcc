package com.example.projetotcc.ui.endereco;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.projetotcc.PaginaUsuario;
import com.example.projetotcc.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import dominio.entidade.CEP;
import dominio.entidade.Servico;
import dominio.entidade.Usuario;

public class EnderecoFragment extends Fragment {

    private EnderecoViewModel mViewModel;
    private TextView nome, servico, rua, cidade, estado, bairro, numero, complemento, btn;
    private ImageView image;
    private Usuario usuario;
    private Servico servico2;
    private CEP cep;

    public static EnderecoFragment newInstance() {
        return new EnderecoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_endereco, container, false);
        usuario = PaginaUsuario.usuario;
        cep = PaginaUsuario.cep;
        servico2 = PaginaUsuario.servicop;
        nome = view.findViewById(R.id.nomePerfilEndereco);
        servico = view.findViewById(R.id.ServicoPerfilEndereco);
        rua = view.findViewById(R.id.RuaPerfil);
        cidade = view.findViewById(R.id.CidadePerfil);
        estado = view.findViewById(R.id.EstadoPerfil);
        bairro = view.findViewById(R.id.BairroPerfil);
        numero = view.findViewById(R.id.NumeroPerfil);
        complemento = view.findViewById(R.id.ComplementoPerfil);
        image = view.findViewById(R.id.imageEndereco);

        nome.setText(usuario.getNome());
        try {
            servico.setText(servico2.getTipo());
        } catch (Exception e) {
            servico.setText("Usuário comum");
            e.printStackTrace();
        }
        rua.setText(cep.getRua());
        cidade.setText(cep.getCidade());
        estado.setText(cep.getEstado());
        bairro.setText(cep.getBairro());
        numero.setText(cep.getNumero());
        complemento.setText(cep.getComplemento());

        Picasso.get().load(usuario.getImageUrl()).into(image);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(EnderecoViewModel.class);
        // TODO: Use the ViewModel
    }

}