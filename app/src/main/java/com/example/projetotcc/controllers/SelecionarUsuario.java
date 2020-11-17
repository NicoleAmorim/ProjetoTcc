package com.example.projetotcc.controllers;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.projetotcc.ChatUsuario;
import com.example.projetotcc.InfoServico;
import dominio.entidade.Usuario;

import com.example.projetotcc.config.Constants;
import com.example.projetotcc.models.CallBacks;
import com.example.projetotcc.models.SelecionarUsuarioModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;

public class SelecionarUsuario extends InfoServico {
    private SelecionarUsuarioModel selecionarUsuarioModel;
    public  void SelecionarUsuarioId()
    {
        selecionarUsuarioModel = new SelecionarUsuarioModel(requestQueue);
        selecionarUsuarioModel.SelecionarUserById(new CallBacks.VolleyCallbackUsuario() {
            @Override
            public void onSuccess(String response,  Usuario usuario) {
                InfoServico.userName.setText(usuario.getNome());
                InfoServico.email.setText(usuario.getEmail());
                InfoServico.user = usuario;
            }
        }, String.valueOf(servico.getIDUser()));
    }
    public  void SelecionarUsuarioById()
    {
        selecionarUsuarioModel = new SelecionarUsuarioModel(requestQueue);
        selecionarUsuarioModel.SelecionarUserById(new CallBacks.VolleyCallbackUsuario() {
            @Override
            public void onSuccess(String response,  Usuario usuario) {
                user = usuario;
                Log.i("Aqui", "Meu Serviço: "+user.getCpf());
                it = new Intent(InfoServico.context, ChatUsuario.class);
                context.startActivity(it);
            }
        }, String.valueOf(servico.getIDUser()));
    }
    private void SelecionarUserFireBase(String id)
    {
        FirebaseFirestore.getInstance().collection("users").document(id).collection("dados")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Usuario usuario = new Usuario();
                                Log.d("TAG", document.getId() + " => " + document.getData());
                                usuario = document.toObject(Usuario.class);

                                Log.d("TAG", document.getId() + " => " + usuario.getUsername());
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
