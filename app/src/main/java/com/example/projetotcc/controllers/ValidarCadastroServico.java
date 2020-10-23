package com.example.projetotcc.controllers;

import android.content.Intent;
import android.widget.Toast;

import com.example.projetotcc.cadastroServico.CadastroServico1;
import com.example.projetotcc.cadastroUsuario.Cadastro1;
import com.example.projetotcc.cadastroUsuario.Cadastro2;
import com.example.projetotcc.PaginaUsuario;
import dominio.entidade.Usuario;
import com.example.projetotcc.models.CadastroServicoModel;
import com.example.projetotcc.models.CallBacks;

public class ValidarCadastroServico extends CadastroServico1 {
    public void ValidarCadastroServico(String nome, String tipo, String preco, String descricao, String imagem, Usuario usuario) {
        cadastroServicoModel = new CadastroServicoModel();
        if (tipo != "Tipo") {
            if (!nome.isEmpty()) {
                if (!preco.isEmpty()) {
                    if (!descricao.isEmpty()) {
                        if (imagem != null) {
                            cadastroServicoModel.CadastrarServico(new CallBacks.VolleyCallback() {
                                @Override
                                public void onSuccess(String result) {

                                    Intent it = new Intent(CadastroServico1.context, PaginaUsuario.class);

                                    it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                                    context.startActivity(it);
                                }
                            }, nome, preco, descricao, tipo, usuario, imagem);
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
}
