package com.example.projetotcc.cadastroUsuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.projetotcc.R;
import com.example.projetotcc.controllers.ValidarCadastroUsuario;

public class Cadastro1 extends AppCompatActivity {
    private static String Nome, Sobrenome, CPF;
    private EditText cpf, nome, sobrenome;
    private ValidarCadastroUsuario validarCadastroUsuario;
    public static Context context;
    private Intent it;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_1);
        validarCadastroUsuario = new ValidarCadastroUsuario();

        context = this;

        this.nome = (EditText)this.findViewById(R.id.nomeUserCadastro);
        this.sobrenome = (EditText)this.findViewById(R.id.sobrenomeUserCadastro);
        this.cpf = (EditText)this.findViewById(R.id.cpfUserCadastro);
    }

    public void Cadastrar(View view) {
        Nome = nome.getText().toString() + "";
        Sobrenome = sobrenome.getText().toString() + "";
        CPF = cpf.getText().toString() + "";

        validarCadastroUsuario.ValidarCadastro1(Nome, Sobrenome, CPF);
    }
}