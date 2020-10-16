package com.example.projetotcc.CadastroUsuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projetotcc.PaginaUsuario;
import com.example.projetotcc.R;
import com.example.projetotcc.Usuario;

public class Cadastro1 extends AppCompatActivity {
    private static String Nome;
    private static String Sobrenome;
    private static String CPF;
    public static Usuario usuario;
    private EditText cpf;
    private EditText nome;
    private EditText sobrenome;
    Intent it = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_1);
        usuario = new Usuario();
        this.nome = (EditText)this.findViewById(R.id.nomeUserCadastro);
        this.sobrenome = (EditText)this.findViewById(R.id.sobrenomeUserCadastro);
        this.cpf = (EditText)this.findViewById(R.id.cpfUserCadastro);
    }

    public void Cadastrar(View view)
    {
        Nome = nome.getText().toString() + "";
        Sobrenome = sobrenome.getText().toString() + "";
        CPF = cpf.getText().toString() + "";

        if (Nome != "" && Sobrenome != "")
        {
            if (CPF == "")
            {
                Toast.makeText(this, " cpf esta vazio", Toast.LENGTH_SHORT).show();
            }
            else
            {
                usuario.setNome(Nome + " " + Sobrenome);
                usuario.setCpf(CPF);
                it = new Intent(this, Cadastro2.class);
                this.startActivity(it);
            }
        }
        else
        {
            Toast.makeText(this, " nome ou sobrenome está vazio", Toast.LENGTH_SHORT).show();
        }
    }
}