package com.example.projetotcc.ui.editarServico;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.projetotcc.PaginaUsuario;
import com.example.projetotcc.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

public class EditarServicoFragment extends Fragment {

    private EditarServicoViewModel mViewModel;
    private int PICK_IMAGE_REQUEST = 1;
    private ImageView imageView;
    private EditText titulo;
    private EditText descricao;
    private TextView qtdd;
    private RatingBar ratingBar;
    private Usuario usuario;
    private Servico servico;
    private Intent it;

    public static EditarServicoFragment newInstance() {
        return new EditarServicoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view;
        servico = PaginaUsuario.servicop;
        usuario = PaginaUsuario.usuario;
        view = inflater.inflate(R.layout.fragment_editar_servico, container, false);
        imageView = view.findViewById(R.id.ImagemEditarServico);
        titulo = view.findViewById(R.id.TituloEditarServico);
        descricao = view.findViewById(R.id.DescricaoEditarServico);
        qtdd = view.findViewById(R.id.QtddA);
        ratingBar = view.findViewById(R.id.estrelasA);
        TextView btnChat = view.findViewById(R.id.btnEditarServico);

        Picasso.get().load(usuario.getImageUrl()).into(imageView);
        titulo.setText(servico.getNome());
        descricao.setText(servico.getDescricao());
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editar();
            }
        });
        R();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(EditarServicoViewModel.class);
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
                                    PaginaUsuario.Rating rating = new PaginaUsuario.Rating();
                                    rating =  doc.getDocument().toObject(PaginaUsuario.Rating.class);
                                    d+= rating.getRating();
                                    i++;
                                }

                            }
                            try {
                                ratingBar.setNumStars(d / i);
                                qtdd.setText(String.valueOf(i));
                            } catch (Exception exception) {
                                exception.printStackTrace();
                                ratingBar.setNumStars(0);
                                qtdd.setText("0");
                            }
                        }
                    }
                });
    }
    public void Editar()
    {
        servico.setNome(titulo.getText().toString());
        servico.setDescricao(descricao.getText().toString());
        FirebaseFirestore.getInstance().collection("servico")
                .document(usuario.getId())
                .set(servico)
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