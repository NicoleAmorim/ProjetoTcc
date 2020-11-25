package com.example.projetotcc.cadastroUsuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.projetotcc.R;
import com.example.projetotcc.controllers.ValidarCadastroUsuario;

public class Cadastro3 extends AppCompatActivity {
    public static Context context;
    private ValidarCadastroUsuario validarCadastroUsuario;
    private EditText email, username, tel;
    private  String Email, User;
    private String Tell;
    Intent it = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_3);

        context = this;
        validarCadastroUsuario = new ValidarCadastroUsuario();

        this.email = (EditText)this.findViewById(R.id.emailUserCadastro);
        this.username = (EditText)this.findViewById(R.id.nomeUsuarioUserCadastro);
        this.tel = (EditText)this.findViewById(R.id.telUserCadastro);

    }

    public void Cadastrar(View view) {
        Email = email.getText().toString() + "";
        User = username.getText().toString() + "";
        Tell = tel.getText().toString();

        if(validarCadastroUsuario.ValidarCadastro3(Email, User, Tell))
        {
            it = new Intent(this, Cadastro4.class);
            this.startActivity(it);
        }
    }
}