package com.example.projetotcc.controllers;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.projetotcc.cadastroUsuario.Cadastro1;
import com.example.projetotcc.cadastroUsuario.Cadastro2;
import com.example.projetotcc.cadastroUsuario.Cadastro3;
import com.example.projetotcc.cadastroUsuario.Cadastro5;
import database.DadosOpenHelper;
import dominio.repositorio.ManterLogadoRepositorio;
import com.example.projetotcc.PaginaUsuario;
import dominio.entidade.Usuario;
import com.example.projetotcc.models.ValidarCadastroUsuarioModel;
import com.example.projetotcc.models.CallBacks;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.InputMismatchException;
import java.util.UUID;

public class ValidarCadastroUsuario extends Cadastro5 {
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

                    if ((dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10)))
                         cpf = imprimeCPF(cpf);
                         usuario.setNome(nome + " " + sobrenome);
                         usuario.setCpf(cpf);
                            return true;

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

    public boolean ValidarCadastro3(String email, String user, String tell) {
        if (!email.isEmpty()) {
            if (!user.isEmpty()) {
                if (tell != null || tell.isEmpty()) {
                    email+= "@gmail.com";
                    usuario.setEmail(email);
                    usuario.setUsername(user);
                    usuario.setTel(tell);
                    return true;
                } else {
                    Toast.makeText(Cadastro3.context, " Telefone está vazio", Toast.LENGTH_SHORT).show();
                    return false;
                }
            } else {
                Toast.makeText(Cadastro3.context, " email está vazio", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(Cadastro3.context, " Email está vazio", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    public boolean ValidarCadastro4(String senha, String senhaC) {
        if (!senha.isEmpty()) {
            if (!senhaC.isEmpty()) {
                if (!(senha.length() <= 6))
                {

                    if (senha.equals(senhaC)) {
                        usuario.setSenha(senha);
                        return true;
                    } else {
                        Toast.makeText(Cadastro3.context, "As senhas se diferem!", Toast.LENGTH_SHORT).show();
                        return false;
                    }
            }
                else {
                    Toast.makeText(Cadastro3.context, "A senha tem que ter no minimo 6 caracteries", Toast.LENGTH_SHORT).show();
                    return false;
                }
            } else {
                Toast.makeText(Cadastro3.context, "Confirmação de senha está vazio", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(Cadastro3.context, "Senha está vazio", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    public void ValidarCadastro5(String imagem) {
        try {
            cadastroUsuarioModel = new ValidarCadastroUsuarioModel();
            if (imagem != null) {
                cadastroUsuarioModel.CadastrarUser(new CallBacks.VolleyCallbackUsuario() {
                    @Override
                    public void onSuccess(String response, Usuario user) {
                        if (user == null) {
                        } else if (user.getEmail() == usuario.getEmail()) {

                            manterLogadoRepositorio.inserir(user);
                            FirebaseAuth.getInstance().createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if(task.isSuccessful())
                                                Log.i("teste", task.getResult().getUser().getUid());
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.i("teste", e.getMessage());
                                }
                            });
                            it = new Intent(context, PaginaUsuario.class);

                            it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                            context.startActivity(it);
                        }
                    }
                }, usuario, imagem);
            } else {
                Toast.makeText(Cadastro2.context, "Por favor escolha uma foto para seu perfil", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
    public void ValidarCadastro5FireBase(final Uri imagem) {
        try {
            cadastroUsuarioModel = new ValidarCadastroUsuarioModel();
            if (imagem != null) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                    Log.i("teste", task.getResult().getUser().getUid());
                                saveUserInFirebase(imagem);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("teste", e.getMessage());
                    }
                });

            } else {
                Toast.makeText(Cadastro2.context, "Por favor escolha uma foto para seu perfil", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void saveUserInFirebase(Uri imagem) {
        String filename = UUID.randomUUID().toString();
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

    private void criarConexaoInterna() {
        try {
            dadosOpenHelper = new DadosOpenHelper(Cadastro5.context);
            conexao = dadosOpenHelper.getWritableDatabase();
            manterLogadoRepositorio = new ManterLogadoRepositorio(conexao); } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

