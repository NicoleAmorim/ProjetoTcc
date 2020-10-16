package com.example.projetotcc.CadastroUsuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projetotcc.R;
import com.example.projetotcc.Usuario;

public class Cadastro3 extends AppCompatActivity {
    public static Usuario usuario;
    private EditText email;
    private EditText tel;
    private EditText username;
    private  String Email, User;
    private  int Tell;
    Intent it = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_3);

        this.email = (EditText)this.findViewById(R.id.emailUserCadastro);
        this.username = (EditText)this.findViewById(R.id.nomeUsuarioUserCadastro);
        this.tel = (EditText)this.findViewById(R.id.telUserCadastro);
        usuario = Cadastro2.usuario;
    }

    public void Cadastrar(View view) {
        Email = email.getText().toString() + "";
        User = username.getText().toString() + "";
        Tell = Integer.parseInt(tel.getText().toString());
        if (Email == "") {
            Toast.makeText(this, " email está vazio", Toast.LENGTH_SHORT).show();
        } else if (User == "") {
            Toast.makeText(this, " userName está vazio", Toast.LENGTH_SHORT).show();
        } else if (Tell == 0) {
            Toast.makeText(this, " telefone está vazio", Toast.LENGTH_SHORT).show();
        } else {
            usuario.setEmail(Email);
            usuario.setUsername(User);
            usuario.setTel(Tell);
            it = new Intent(this, Cadastro4.class);
            this.startActivity(it);
        }
    }
}