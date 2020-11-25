package com.example.projetotcc.cadastroUsuario;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.projetotcc.LoadingDialog;
import com.example.projetotcc.R;
import com.example.projetotcc.controllers.ValidarCadastroUsuario;
import com.example.projetotcc.models.CallBacks;
import com.example.projetotcc.models.ValidarCadastroUsuarioModel;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import database.DadosOpenHelper;
import dominio.entidade.Usuario;
import dominio.repositorio.ManterLogadoRepositorio;

public class Cadastro6 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_6);
    }

    public void Cadastrar(View var1) {
    }

}