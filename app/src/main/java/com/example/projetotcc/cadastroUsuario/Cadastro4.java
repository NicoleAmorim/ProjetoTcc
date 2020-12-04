package com.example.projetotcc.cadastroUsuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.projetotcc.R;
import com.example.projetotcc.controllers.ValidarCadastroUsuario;

public class Cadastro4 extends AppCompatActivity {
    private ValidarCadastroUsuario validarCadastroUsuario;
    private String senhaString, confsenhaDString;
    private EditText senha, senhaC;
    public static Context context;
    Intent it = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_4);
        context = this;
        validarCadastroUsuario = new ValidarCadastroUsuario();

        this.senha = (EditText)this.findViewById(R.id.senhaC);
        this.senhaC = (EditText)this.findViewById(R.id.senhacC);
    }

    public void Cadastrar(View view) {

        senhaString = senha.getText().toString()+"";
        confsenhaDString = senhaC.getText().toString()+"";
        if(validarCadastroUsuario.ValidarCadastro4(senhaString, confsenhaDString))
        {
            it = new Intent(this, Cadastro5.class);
            this.startActivity(it);
        }
    }
}