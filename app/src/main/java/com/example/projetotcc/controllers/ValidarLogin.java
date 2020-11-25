package com.example.projetotcc.controllers;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.projetotcc.MainActivity;
import database.DadosOpenHelper;
import dominio.repositorio.ManterLogadoRepositorio;
import com.example.projetotcc.PaginaUsuario;
import dominio.entidade.Usuario;
import com.example.projetotcc.models.CallBacks;
import com.example.projetotcc.models.ValidarLoginModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

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
                            procurar = false;
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
    public void LoginFirebase(String email, String senha) {
        try {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            try {
                                Log.i("Teste", task.getResult().getUser().getUid());
                                authCredential= task.getResult().getCredential();

                                Intent intent = new Intent(MainActivity.context, PaginaUsuario.class);

                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                                context.startActivity(intent);
                            } catch (Exception e) {
                                loadingDialog.DismissDialog();
                                e.printStackTrace();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.i("Teste", e.getMessage());
                            loadingDialog.DismissDialog();
                            Toast.makeText(MainActivity.context, "Verifique seu email ou senha", Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            loadingDialog.DismissDialog();
            Toast.makeText(MainActivity.context, "Falha ao validar email e senha", Toast.LENGTH_SHORT).show();
        }
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
            }else{
                try {
                    manterLogadoRepositorio.excluir();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        } catch (Exception e) {
            try {
                manterLogadoRepositorio.excluir();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            e.printStackTrace();
        }
    }
    }