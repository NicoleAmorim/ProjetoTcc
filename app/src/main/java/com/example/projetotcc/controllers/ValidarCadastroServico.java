package com.example.projetotcc.controllers;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.projetotcc.cadastroServico.CadastroServico1;
import com.example.projetotcc.cadastroUsuario.Cadastro1;
import com.example.projetotcc.cadastroUsuario.Cadastro2;
import com.example.projetotcc.PaginaUsuario;

import dominio.entidade.Servico;
import dominio.entidade.Usuario;
import com.example.projetotcc.models.CadastroServicoModel;
import com.example.projetotcc.models.CallBacks;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class ValidarCadastroServico extends CadastroServico1 {

    public void ValidarCadastroServico(String nome, String tipo, String preco, String descricao, Uri imagem, Usuario usuario) {
        cadastroServicoModel = new CadastroServicoModel();
        if (tipo != "Tipo") {
            if (!nome.isEmpty()) {
                if (!preco.isEmpty()) {
                    if (!descricao.isEmpty()) {
                        if (imagem != null) {
                            Servico servico  = new Servico();
                            servico.setDescricao(descricao);
                            servico.setNome(nome);
                            servico.setPreco(preco);
                            servico.setTipo(tipo);
                            ServicoFireBase(servico, imagem);

                        } else {
                            Toast.makeText(Cadastro2.context, "Por favor escolha uma foto para seu perfil", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Cadastro1.context, "Descrição está vazio", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Cadastro1.context, "Preço está vazio", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(Cadastro1.context, " Nome está vazio", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(Cadastro1.context, " Tipo está vazio", Toast.LENGTH_SHORT).show();
        }
    }
    private void ServicoFireBase(final Servico servico, Uri imagem) {
        String filename = UUID.randomUUID().toString();
        final StorageReference ref = FirebaseStorage.getInstance().getReference("/images/servico/" + filename);
        ref.putFile(imagem)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                servico.setImagemUrl(String.valueOf(uri));
                                servico.setIDUser(FirebaseAuth.getInstance().getUid());
                                FirebaseFirestore.getInstance().collection("servico")
                                        .document(servico.getIDUser())
                                        .set(servico)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                FirebaseFirestore.getInstance().collection("servicoGlobal")
                                                        .document(servico.getIDUser())
                                                        .set(servico)
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Intent it = new Intent(context, PaginaUsuario.class);

                                                                it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                                                                context.startActivity(it);
                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Log.i("Teste", e.getMessage());
                                                                loadingDialog.DismissDialog();
                                                            }
                                                        });
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                loadingDialog.DismissDialog();
                                                Log.i("Teste", e.getMessage());
                                            }
                                        });
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loadingDialog.DismissDialog();
                        Log.e("Teste", e.getMessage(), e);
                    }
                });
    }
    private void Servico(String nome, String preco, String descricao, String tipo, Usuario usuario, String imagem) {
        cadastroServicoModel.CadastrarServico(new CallBacks.VolleyCallback() {
            @Override
            public void onSuccess(String result) {

                Intent it = new Intent(CadastroServico1.context, PaginaUsuario.class);

                it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(it);
            }
        }, nome, preco, descricao, tipo, usuario, imagem);
    }
}
