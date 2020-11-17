package com.example.projetotcc.cadastroUsuario;

import androidx.appcompat.app.AppCompatActivity;

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

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.projetotcc.LoadingDialog;
import com.example.projetotcc.controllers.ValidarCadastroUsuario;
import com.example.projetotcc.models.ValidarCadastroUsuarioModel;
import com.example.projetotcc.models.CallBacks;
import database.DadosOpenHelper;
import dominio.repositorio.ManterLogadoRepositorio;
import com.example.projetotcc.R;
import dominio.entidade.Usuario;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

public class Cadastro5 extends AppCompatActivity {
    public static RequestQueue requestQueue;
    public static Usuario usuario;
    private int PICK_IMAGE_REQUEST = 1;
    protected CallBacks callBacks;
    public static Context context;
    public static LoadingDialog loadingDialog;
    private ValidarCadastroUsuario validarCadastroUsuario;
    protected ValidarCadastroUsuarioModel cadastroUsuarioModel;
    private Uri filePath;
    private String image;
    private ImageView imageView;
    protected Intent it = null;
    protected SQLiteDatabase conexao;
    protected DadosOpenHelper dadosOpenHelper;
    protected ManterLogadoRepositorio manterLogadoRepositorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_5);
        validarCadastroUsuario = new ValidarCadastroUsuario();
        loadingDialog = new LoadingDialog(this);
        context = this;
        requestQueue = Volley.newRequestQueue(this.getApplicationContext());
        imageView = findViewById(R.id.imageCadUser);
    }

    public void Cadastrar(View var1) {
        loadingDialog.StartActivityLogin();
        validarCadastroUsuario.ValidarCadastro5FireBase(filePath);
    }

    public void SelecionarImagem(View view) {
        this.startActivityForResult(new Intent("android.intent.action.PICK", MediaStore.Images.Media.INTERNAL_CONTENT_URI), this.PICK_IMAGE_REQUEST);
    }

    protected void onActivityResult(int n, int n2, Intent intent) {
        super.onActivityResult(n, n2, intent);
        if(intent != null) {
            try {
                filePath = intent.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap((ContentResolver) this.getContentResolver(), (Uri) filePath);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, (OutputStream) byteArrayOutputStream);
                image = Base64.encodeToString((byte[]) byteArrayOutputStream.toByteArray(), (int) 0);
                this.imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}