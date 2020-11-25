package com.example.projetotcc.controllers;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import dominio.entidade.Usuario;

import com.example.projetotcc.models.CallBacks;
import com.example.projetotcc.models.SelecionarUsuarioModel;
import com.example.projetotcc.ui.infoServico.InfoServicoFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class SelecionarUsuario extends InfoServicoFragment {
    private SelecionarUsuarioModel selecionarUsuarioModel;
    public  void SelecionarUsuarioId()
    {
        selecionarUsuarioModel = new SelecionarUsuarioModel(requestQueue);
        selecionarUsuarioModel.SelecionarUserById(new CallBacks.VolleyCallbackUsuario() {
            @Override
            public void onSuccess(String response,  Usuario usuario) {
                InfoServicoFragment.userName.setText(usuario.getNome());
                InfoServicoFragment.email.setText(usuario.getEmail());
                InfoServicoFragment.user = usuario;
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
