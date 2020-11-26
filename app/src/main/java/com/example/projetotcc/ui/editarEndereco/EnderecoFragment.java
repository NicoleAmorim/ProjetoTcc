package com.example.projetotcc.ui.editarEndereco;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import dominio.entidade.CEP;
import dominio.entidade.Servico;
import dominio.entidade.Usuario;

public class EnderecoFragment extends Fragment {

    private EnderecoViewModel mViewModel;
    private EditText nome, servico, rua, cidade, estado, bairro, numero, complemento;
    private ImageView image;
    private Intent it;
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
        view = inflater.inflate(R.layout.fragment_editar_endereco, container, false);
        usuario = PaginaUsuario.usuario;
        cep = PaginaUsuario.cep;
        servico2 = PaginaUsuario.servicop;
        nome = view.findViewById(R.id.nomePerfilEditEndereco);
        servico = view.findViewById(R.id.ServicoPerfilEditEndereco);
        rua = view.findViewById(R.id.editRuaEndereco);
        cidade = view.findViewById(R.id.editCidade);
        estado = view.findViewById(R.id.editEstado);
        bairro = view.findViewById(R.id.editBairroEndereco);
        numero = view.findViewById(R.id.editNumeroEndereco);
        complemento = view.findViewById(R.id.editComplementoEndereco);
        image = view.findViewById(R.id.imageEnderecoEdit);
        TextView btnChat = view.findViewById(R.id.btnEditarLocal);

        nome.setText(usuario.getNome());
        servico.setText(servico2.getTipo());
        rua.setText(cep.getRua());
        cidade.setText(cep.getCidade());
        estado.setText(cep.getEstado());
        bairro.setText(cep.getBairro());
        numero.setText(cep.getNumero());
        complemento.setText(cep.getComplemento());
        Picasso.get().load(usuario.getImageUrl()).into(image);
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editar();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(EnderecoViewModel.class);
        // TODO: Use the ViewModel
    }
    public void Editar()
    {
        cep.setRua(rua.getText().toString());
        cep.setCidade(cidade.getText().toString());
        cep.setEstado(estado.getText().toString());
        cep.setComplemento(complemento.getText().toString());
        cep.setBairro(bairro.getText().toString());
        cep.setNumero(numero.getText().toString());
        FirebaseFirestore.getInstance().collection("endereco")
                .document(usuario.getId())
                .set(cep)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        it = new Intent(PaginaUsuario.context, PaginaUsuario.class);

                        it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                        PaginaUsuario.context.startActivity(it);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("Teste", e.getMessage());
                    }
                });
    }
}