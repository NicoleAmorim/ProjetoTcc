package com.example.projetotcc.ui.minhaLoja;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetotcc.PaginaUsuario;
import dominio.entidade.Servico;
import dominio.entidade.Usuario;

import com.example.projetotcc.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class MinhaLojaFragment extends Fragment {

    private MinhaLojaViewModel mViewModel;
    public static RecyclerView rv;
    Servico servico;
    Usuario user;
    TextView email, tipo, descricao, userName, estado, cidade, tell;
    ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        servico = PaginaUsuario.servicop;
        user = new Usuario();
        View view;
try {
    if (servico.getNome().isEmpty()) {
        view = inflater.inflate(R.layout.fragment_meu_servico_vazio, container, false);
    } else {
        view = inflater.inflate(R.layout.fragment_meu_servico, container, false);
    }
    try {

        imageView = view.findViewById(R.id.imgPerfilServicoU);
        userName = view.findViewById(R.id.nomePerfilServicoU);
        tipo = view.findViewById(R.id.ServicoPerfilServicoU);
        estado = view.findViewById(R.id.EstadoPerfilServicoU);
        cidade = view.findViewById(R.id.CidadePerfilServicoU);
        tell = view.findViewById(R.id.tellPerfilServicoU);
        email = view.findViewById(R.id.EmailPerfilServicoU);
        descricao = view.findViewById(R.id.DescricaoPerfilServicoU);

        FirebaseFirestore.getInstance().collection("/users")
                .document(servico.getIDUser())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        user = documentSnapshot.toObject(Usuario.class);
                        userName.setText(user.getNome());
                        tipo.setText(servico.getTipo());
                        estado.setText(user.getNome());
                        cidade.setText(servico.getTipo());
                        tell.setText(String.valueOf(user.getTel()));
                        email.setText(user.getEmail());
                        descricao.setText(servico.getDescricao());
                        Picasso.get().load(user.getImageUrl()).into(imageView);
                    }
                });
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