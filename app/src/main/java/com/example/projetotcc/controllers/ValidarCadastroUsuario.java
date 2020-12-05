package com.example.projetotcc.controllers;

import android.content.Context;
import android.content.Intent;
import android.media.MediaDrm;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.projetotcc.cadastroUsuario.Cadastro1;
import com.example.projetotcc.cadastroUsuario.Cadastro2;
import com.example.projetotcc.cadastroUsuario.Cadastro3;
import com.example.projetotcc.cadastroUsuario.Cadastro4;
import com.example.projetotcc.cadastroUsuario.Cadastro5;
import dominio.entidade.CEP;

import com.example.projetotcc.PaginaUsuario;

import dominio.entidade.Servico;
import dominio.entidade.Usuario;

import com.example.projetotcc.cadastroUsuario.Cadastro6;
import com.example.projetotcc.ui.editarPerfil.EditarPerfilFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.InputMismatchException;
import java.util.List;
import java.util.UUID;

public class ValidarCadastroUsuario extends Cadastro6 {
    String CPF;
    public boolean ValidarCadastro1(String nome, String sobrenome, String cpf) {
        usuario = new Usuario();
        if (!nome.isEmpty() && !sobrenome.isEmpty()) {
            if (!cpf.isEmpty()) {
                if (cpf.equals("00000000000") ||
                        cpf.equals("11111111111") ||
                        cpf.equals("22222222222") || cpf.equals("33333333333") ||
                        cpf.equals("44444444444") || cpf.equals("55555555555") ||
                        cpf.equals("66666666666") || cpf.equals("77777777777") ||
                        cpf.equals("88888888888") || cpf.equals("99999999999") ||
                        (cpf.length() != 11)) {
                    Toast.makeText(Cadastro1.context, " CPF invalido", Toast.LENGTH_SHORT).show();
                    return (false);
                }
                char dig10, dig11;
                int sm, i, r, num, peso;

                try {
                    sm = 0;
                    peso = 10;
                    for (i = 0; i < 9; i++) {
                        num = (int) (cpf.charAt(i) - 48);
                        sm = sm + (num * peso);
                        peso = peso - 1;
                    }

                    r = 11 - (sm % 11);
                    if ((r == 10) || (r == 11))
                        dig10 = '0';
                    else dig10 = (char) (r + 48);
                    sm = 0;
                    peso = 11;
                    for (i = 0; i < 10; i++) {
                        num = (int) (cpf.charAt(i) - 48);
                        sm = sm + (num * peso);
                        peso = peso - 1;
                    }

                    r = 11 - (sm % 11);
                    if ((r == 10) || (r == 11))
                        dig11 = '0';
                    else dig11 = (char) (r + 48);

                        if ((dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10))) {
                            cpf = imprimeCPF(cpf);
                            FindCPF(cpf);
                                usuario.setNome(nome + " " + sobrenome);
                                usuario.setCpf(cpf);
                                return true;

                        } else {
                            Toast.makeText(Cadastro1.context, " CPF invalido", Toast.LENGTH_SHORT).show();
                        }
                } catch (InputMismatchException erro) {
                    Toast.makeText(Cadastro1.context, " CPF invalido", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(Cadastro1.context, " CPF está vazio", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(Cadastro1.context, " Nome ou sobrenome está vazio", Toast.LENGTH_SHORT).show();
            return false;
        }
        return false;
    }
    public static String imprimeCPF(String cpf) {
        return(cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." +
                cpf.substring(6, 9) + "-" + cpf.substring(9, 11));
    }
    public boolean ValidarCadastro2(String ano, String mes, String dia, String sexo) {
        if (ano != "Ano" || mes != "Mês" || dia != "Dia") {
            if (sexo != "SEXO") {
                usuario.setSexo(sexo);
                usuario.setIdade(Integer.parseInt(dia + mes + ano));
                return true;
            } else {
                Toast.makeText(Cadastro2.context, " Sexo não selecionado", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(Cadastro2.context, " Data não selecionada ou incompleta", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void ValidarCadastro3(String email, String user, String tell) {
        if (!email.isEmpty()) {
            if (!user.isEmpty()) {
                if (tell != null || tell.isEmpty()) {
                    usuario.setEmail(email);
                    usuario.setUsername(user);
                    usuario.setTel(tell);
                    FindEmail(email, tell, Cadastro3.context, true);
                } else {
                    Toast.makeText(Cadastro3.context, " Telefone está vazio", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(Cadastro3.context, " email está vazio", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(Cadastro3.context, " Email está vazio", Toast.LENGTH_SHORT).show();
        }
    }
    public boolean ValidarCadastro4(String senha, String senhaC) {
        if (!senha.isEmpty()) {
            if (!senhaC.isEmpty()) {
                if (!(senha.length() < 6))
                {

                    if (senha.equals(senhaC)) {
                        usuario.setSenha(senha);
                        return true;
                    } else {
                        Toast.makeText(Cadastro4.context, "As senhas se diferem!", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
                else {
                    Toast.makeText(Cadastro4.context, "A senha tem que ter no minimo 6 caracteries", Toast.LENGTH_SHORT).show();
                    return false;
                }
            } else {
                Toast.makeText(Cadastro4.context, "Confirmação de senha está vazio", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(Cadastro4.context, "Senha está vazio", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void ValidarCadastro5FireBase(final Uri imagem) {
        try {
            if (imagem != null) {

                it = new Intent(Cadastro5.context, Cadastro6.class);
                Cadastro5.context.startActivity(it);
            } else {
                Toast.makeText(Cadastro2.context, "Por favor escolha uma foto para seu perfil", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
    public void ValidarCadastro6FireBase(final CEP cep, final Uri imagem) {
        try {
            if (!cep.getCEP().isEmpty()) {
                if (!cep.getBairro().isEmpty()) {
                    if (!cep.getComplemento().isEmpty()) {
                        if (!cep.getEstado().isEmpty()) {
                            if (!cep.getRua().isEmpty()) {
                                if (!cep.getCidade().isEmpty()) {
                                    if (!cep.getNumero().isEmpty()) {
                                        FirebaseAuth.getInstance().createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                        if (task.isSuccessful())
                                                            Log.i("teste", task.getResult().getUser().getUid());
                                                        saveUserInFirebase(imagem, cep);
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.i("teste", e.getMessage());
                                            }
                                        });
                                    } else {
                                        Toast.makeText(Cadastro2.context, "número está vazio", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(Cadastro2.context, "cidade está vazio", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(Cadastro2.context, "rua está vazio", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Cadastro2.context, "UF está vazio", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Cadastro2.context, "complemento está vazio", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Cadastro2.context, "bairro está vazio", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(Cadastro2.context, "CEP está vazio", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveUserInFirebase(Uri imagem, final CEP cep) {
        String filename = FirebaseAuth.getInstance().getUid();
        final StorageReference ref = FirebaseStorage.getInstance().getReference("/images/users/" + filename);
        ref.putFile(imagem)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                usuario.setImageUrl(String.valueOf(uri));
                                usuario.setId(FirebaseAuth.getInstance().getUid());
                                FirebaseFirestore.getInstance().collection("users")
                                        .document(usuario.getId())
                                        .set(usuario)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                FirebaseFirestore.getInstance().collection("endereco")
                                                        .document(usuario.getId())
                                                        .set(cep)
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                loadingDialog.DismissDialog();
                                                                it = new Intent(context, PaginaUsuario.class);

                                                                it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                                                                context.startActivity(it);
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
    public void FindCPF(String cpf) {
        FirebaseFirestore.getInstance().collection("users")
                .whereEqualTo("cpf", cpf)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.isEmpty())
                        {
                            Log.e("CPF", "Vazio");
                            it = new Intent(Cadastro1.context, Cadastro2.class);
                            Cadastro1.context.startActivity(it);
                        }else
                        {
                            Toast.makeText(Cadastro1.context, " CPF já cadastrado", Toast.LENGTH_SHORT).show();
                            Log.e("CPF", "tem");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("CPF: ", e.getMessage());

            }
        });
    }

    public void FindEmail(String email, final String tell, final Context context, final boolean i) {
        FirebaseFirestore.getInstance().collection("users")
                .whereEqualTo("email", email)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.isEmpty())
                        {
                            FindTell(tell, context, i);
                        }else
                        {
                            Toast.makeText(context, " Email já cadastrado", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("ERRO", e.getMessage());

            }
        });
    }
    public void FindTell(String tell, final Context context, final boolean i) {
        final boolean[] cpfBoolean = new boolean[1];
        FirebaseFirestore.getInstance().collection("users")
                .whereEqualTo("tel", tell)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.isEmpty())
                        {
                            if(i) {
                                it = new Intent(context, Cadastro4.class);
                                context.startActivity(it);
                            }else{
                                EditarPerfilFragment.Validar = true;
                            }
                        }else
                        {
                            Toast.makeText(context, " Telefone já cadastrado", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("ERRO", e.getMessage());

            }
        });
    }
}

