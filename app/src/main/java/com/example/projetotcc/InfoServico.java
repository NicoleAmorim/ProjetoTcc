package com.example.projetotcc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.projetotcc.controllers.SelecionarUsuario;
import com.example.projetotcc.models.CallBacks;
import com.example.projetotcc.ui.listaFragment.ListaCategoriasFragment;

import dominio.entidade.Servico;
import dominio.entidade.Usuario;

public class InfoServico extends AppCompatActivity {

    public static Servico servico;
    public static TextView nome, preco, userName, email;
    public static ImageView imageView, imagemServidor;
    private CallBacks callBacks;

    private SelecionarUsuario selecionarUsuario;
    public static RequestQueue requestQueue;
    public static Usuario user;
    public static Context context;
    protected Intent it;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_servico);
        requestQueue = Volley.newRequestQueue(this.getApplicationContext());
        user = new Usuario();
        selecionarUsuario = new SelecionarUsuario();
        servico = ListaCategoriasFragment.servico;
        callBacks = new CallBacks();
        context = this;

        imagemServidor = findViewById(R.id.ImagemServidorInfo);
        userName = findViewById(R.id.userNameInfoServico);
        email = findViewById(R.id.emailUserInfoProduto);

        selecionarUsuario.SelecionarUsuarioId();

        imageView = findViewById(R.id.imageViewInfoServico);
        nome = findViewById(R.id.NomeInfoServico);
        preco = findViewById(R.id.PrecoInfoServico);

        nome.setText(servico.getNome());
        preco.setText(servico.getPreco() + "R$");
        imageView.setImageBitmap(servico.getImagem());
    }
    public  void Solicitar(View view)
    {
        selecionarUsuario.SelecionarUsuarioById();
    }
}