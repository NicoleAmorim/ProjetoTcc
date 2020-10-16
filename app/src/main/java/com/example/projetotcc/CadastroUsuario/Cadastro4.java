package com.example.projetotcc.CadastroUsuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projetotcc.R;
import com.example.projetotcc.Usuario;

public class Cadastro4 extends AppCompatActivity {
    private static String senhaget;
    public static Usuario usuario;
    private EditText senha;
    private EditText senhaC;
    Intent it = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_4);

        usuario = Cadastro3.usuario;
        this.senha = (EditText)this.findViewById(R.id.senhaC);
        this.senhaC = (EditText)this.findViewById(R.id.senhacC);
    }

    public void Cadastrar(View view) {

        senhaget = senha.getText().toString() + "";
        if (senhaget == "") {
            Toast.makeText(this, " senha ou confirmar senha está vazio", Toast.LENGTH_SHORT).show();
        } else if (this.senha.getText().toString().equals(this.senhaC.getText().toString())) {
            usuario.setSenha(this.senha.getText().toString());
            it = new Intent(this, Cadastro5.class);
            this.startActivity(it);
        } else {
            Toast.makeText(this, "As senhas não correspondem!", Toast.LENGTH_SHORT).show();
        }
    }
}