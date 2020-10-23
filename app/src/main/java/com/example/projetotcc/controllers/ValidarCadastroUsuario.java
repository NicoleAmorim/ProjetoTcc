package com.example.projetotcc.controllers;

import android.content.Intent;
import android.widget.Toast;

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

public class ValidarCadastroUsuario extends Cadastro5 {

    public boolean ValidarCadastro1(String nome, String sobrenome, String cpf) {
        usuario = new Usuario();
        if (!nome.isEmpty() && !sobrenome.isEmpty()) {
            if (!cpf.isEmpty()) {
                usuario.setNome(nome + " " + sobrenome);
                usuario.setCpf(cpf);
                return true;
            } else {
                Toast.makeText(Cadastro1.context, " CPF está vazio", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(Cadastro1.context, " Nome ou sobrenome está vazio", Toast.LENGTH_SHORT).show();
            return false;
        }
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

    public boolean ValidarCadastro3(String email, String user, int tell) {
        if (!email.isEmpty()) {
            if (!user.isEmpty()) {
                if (tell != 0) {
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
                if(senha.equals(senhaC))
                {
                    usuario.setSenha(senha);
                    return true;
                } else {
                    Toast.makeText(Cadastro3.context, "As senhas se diferem!", Toast.LENGTH_SHORT).show();
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
                usuario.setImagem(imagem);
                cadastroUsuarioModel.CadastrarUser(new CallBacks.VolleyCallbackUsuario() {
                    @Override
                    public void onSuccess(String response, Usuario user) {
                        if (user == null) {
                        } else if (user.getEmail() == usuario.getEmail()) {
                            criarConexaoInterna();
                            manterLogadoRepositorio.inserir(user);
                            it = new Intent(context, PaginaUsuario.class);

                            it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                            context.startActivity(it);
                        }
                    }
                }, usuario);
            } else {
                Toast.makeText(Cadastro2.context, "Por favor escolha uma foto para seu perfil", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) { e.printStackTrace(); }
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

