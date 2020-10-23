package com.example.projetotcc.controllers;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.example.projetotcc.ChatUsuario;
import com.example.projetotcc.InfoServico;
import dominio.entidade.Usuario;
import com.example.projetotcc.models.CallBacks;
import com.example.projetotcc.models.SelecionarUsuarioModel;

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
                ByteArrayInputStream stream = new ByteArrayInputStream(Base64.decode(usuario.getImagem().getBytes(), Base64.DEFAULT));
                usuario.setImage(BitmapFactory.decodeStream(stream));
                InfoServico.imagemServidor.setImageBitmap(usuario.getImage());
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
}
