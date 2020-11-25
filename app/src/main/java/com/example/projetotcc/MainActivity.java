package com.example.projetotcc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.projetotcc.cadastroUsuario.Cadastro1;
import database.DadosOpenHelper;
import dominio.repositorio.ManterLogadoRepositorio;
import com.example.projetotcc.controllers.ValidarLogin;
import com.example.projetotcc.models.ValidarLoginModel;
import com.google.firebase.auth.AuthCredential;

import dominio.entidade.Usuario;

public class MainActivity extends AppCompatActivity {
    public static RequestQueue requestQueue;
    private ValidarLogin validarLogin;
    public static Usuario usuario;
    public static boolean procurar;
    protected SQLiteDatabase conexao;
    protected DadosOpenHelper dadosOpenHelper;
    protected ManterLogadoRepositorio manterLogadoRepositorio;
    protected ValidarLoginModel loginModel;
    public EditText emailm;
    public EditText senham;
    public static Context context;
    public static AuthCredential authCredential;
    public static LoadingDialog loadingDialog;
    protected Intent it = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        procurar = true;
        validarLogin = new ValidarLogin();
        usuario = new Usuario();
        loadingDialog = new LoadingDialog(this);


        emailm = (EditText)this.findViewById(R.id.emailL);
        senham = (EditText)this.findViewById(R.id.senhaL);

        requestQueue = Volley.newRequestQueue(this.getApplicationContext());
    }

    public void Entrar(View view)
    {
        final String Email = this.emailm.getText().toString();
        final String Senha = this.senham.getText().toString();

        loadingDialog.StartActivityLogin();
        validarLogin.LoginFirebase(Email, Senha);
    }
    public void Cadastrar(View view)
    {
        it = new Intent(this, Cadastro1.class);
        this.startActivity(it);
    }
}