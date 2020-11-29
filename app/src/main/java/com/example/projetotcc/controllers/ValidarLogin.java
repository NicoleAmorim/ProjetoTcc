package com.example.projetotcc.controllers;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.projetotcc.MainActivity;
import com.example.projetotcc.PaginaUsuario;
import dominio.entidade.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ValidarLogin extends MainActivity {

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
}