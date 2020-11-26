package com.example.projetotcc.ui.mudarSenha;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.List;

import dominio.entidade.Servico;
import dominio.entidade.Usuario;

public class MudarSenhaFragment extends Fragment {

    private MudarSenhaViewModel mViewModel;
    private int PICK_IMAGE_REQUEST = 1;
    private ImageView imageView;
    private EditText senha;
    private EditText confSenha;
    private TextView qtdd;
    private RatingBar ratingBar;
    private Usuario usuario;
    public static MudarSenhaFragment newInstance() {
        return new MudarSenhaFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view;

        Servico servico;
        servico = PaginaUsuario.servico;
        usuario = PaginaUsuario.usuario;
        view = inflater.inflate(R.layout.fragment_mudar_senha, container, false);
        imageView = view.findViewById(R.id.ImagemEditarSenhaPerfil);
        senha = view.findViewById(R.id.SenhaEditarPerfil);
        confSenha = view.findViewById(R.id.ConfSenhaEditarPerfil);
        qtdd = view.findViewById(R.id.Navaliacao);
        ratingBar = view.findViewById(R.id.estrelasSenha);
        TextView btnChat = view.findViewById(R.id.mudarSenhabtn);
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editar();
            }
        });
        Picasso.get().load(usuario.getImageUrl()).into(imageView);
        R();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MudarSenhaViewModel.class);
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
    public void Editar() {
        String senhaString = senha.getText().toString();
        String confSenhaString = confSenha.getText().toString();
        if (!(senha.length() < 6)) {
            if (senhaString != confSenhaString) {
                AuthCredential authCredential = EmailAuthProvider.getCredential(usuario.getEmail(), usuario.getSenha());
                Log.i("Teste", String.valueOf(authCredential));
                FirebaseAuth.getInstance().getCurrentUser().reauthenticate(authCredential);
                FirebaseAuth.getInstance().getCurrentUser().updatePassword(senhaString)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.i("Teste", "foi");
                                Intent it = new Intent(PaginaUsuario.context, PaginaUsuario.class);

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
            } else {
                Toast.makeText(PaginaUsuario.context, "As senhas se diferem!", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(PaginaUsuario.context, "A senha tem que ter no minimo 6 caracteries", Toast.LENGTH_SHORT).show();
        }
    }
}