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
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.projetotcc.PaginaUsuario;
import com.example.projetotcc.R;
import com.example.projetotcc.RStar;
import com.example.projetotcc.ui.chatUsuario.ChatUsuarioFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.List;

import dominio.entidade.Servico;
import dominio.entidade.Usuario;

public class PerfilFragment extends Fragment {

    private PerfilViewModel mViewModel;
    TextView nome, email, telefone, serviço;
    ImageView image;
    Usuario usuario;
    Servico servico;
    RatingBar ratingBar;
    TextView avaliacao;

    public static PerfilFragment newInstance() {
        return new PerfilFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view;

        usuario = PaginaUsuario.usuario;
        servico = PaginaUsuario.servicop;
        try {
            ChatUsuarioFragment.registration2.remove();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        view = inflater.inflate(R.layout.fragment_perfil, container, false);
        nome = view.findViewById(R.id.nomePerfilServico);
        email = view.findViewById(R.id.emailPerfil);
        telefone = view.findViewById(R.id.tellPerfil);
        serviço = view.findViewById(R.id.ServicoPerfilServico);
        ratingBar = view.findViewById(R.id.estrelas);
        avaliacao = view.findViewById(R.id.qntddA);

        image = view.findViewById(R.id.imagePerfil);
        nome.setText(usuario.getNome());
        email.setText(usuario.getEmail());
        telefone.setText(String.valueOf(usuario.getTel()));
        try {
            serviço.setText(String.valueOf(servico.getTipo()));
        } catch (Exception e) {
            e.printStackTrace();
            serviço.setText("Usuário Comum");
        }

        Picasso.get().load(usuario.getImageUrl()).into(image);

        try{
            servico = PaginaUsuario.servico;
        } catch (Exception e) {
            e.printStackTrace();
        }
        R();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PerfilViewModel.class);
        // TODO: Use the ViewModel
    }

    private void R()
    {
        FirebaseFirestore.getInstance().collection("/avaliacao")
                .document(FirebaseAuth.getInstance().getUid())
                .collection("R")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        List<DocumentChange> documentChanges = queryDocumentSnapshots.getDocumentChanges();
                        if (documentChanges != null) {
                            int i = 0;
                            int d = 0;
                            for (DocumentChange doc: documentChanges) {
                                if (doc.getType() == DocumentChange.Type.ADDED) {
                                    RStar.Rating rating = new RStar.Rating();
                                    rating =  doc.getDocument().toObject(RStar.Rating.class);
                                    d+= rating.getRating();
                                    i++;
                                }

                            }
                            try {
                                ratingBar.setNumStars(d / i);
                                avaliacao.setText(String.valueOf(i));
                            } catch (Exception exception) {
                                exception.printStackTrace();
                                ratingBar.setVisibility(View.INVISIBLE);
                                avaliacao.setText("0");
                            }
                        }
                    }
                });
    }
}