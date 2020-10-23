package com.example.projetotcc.controllers;

import android.content.Intent;
import android.widget.Toast;

import com.example.projetotcc.MainActivity;
import database.DadosOpenHelper;
import dominio.repositorio.ManterLogadoRepositorio;
import com.example.projetotcc.PaginaUsuario;
import dominio.entidade.Usuario;
import com.example.projetotcc.models.CallBacks;
import com.example.projetotcc.models.ValidarLoginModel;

public class ValidarLogin extends MainActivity {

    public void Login(String email, String senha) {
        loginModel = new ValidarLoginModel();
        if(!email.isEmpty()) {
            if(!senha.isEmpty()) {
                loginModel.Login(new CallBacks.VolleyCallbackUsuario() {
                    @Override
                    public void onSuccess(String result, Usuario user) {
                        if (user.getEmail() != null) {
                            usuario = user;
                            loadingDialog.DismissDialog();
                            criarConexaoInterna();
                            manterLogadoRepositorio.inserir(usuario);
                            it = new Intent(context, PaginaUsuario.class);
                            it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(it);
                        } else {
                            loadingDialog.DismissDialog();
                            Toast.makeText(context.getApplicationContext(), "Usuario não encontrado", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, email, senha);
            }else{loadingDialog.DismissDialog();Toast.makeText(context.getApplicationContext(), "Campo senha está vazio", Toast.LENGTH_SHORT).show();}
        }else{loadingDialog.DismissDialog();Toast.makeText(context.getApplicationContext(), "Campo email está vazio", Toast.LENGTH_SHORT).show();}

}
    private void criarConexaoInterna(){
        try {
            dadosOpenHelper = new DadosOpenHelper(MainActivity.context);
            conexao = dadosOpenHelper.getWritableDatabase();
            manterLogadoRepositorio = new ManterLogadoRepositorio(conexao);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void LoginOff() {
        criarConexaoInterna();
        try {
            if(manterLogadoRepositorio.buscarUsuario() != null)
            {
                it = new Intent(context, PaginaUsuario.class);
                it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(it);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    }