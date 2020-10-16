package com.example.projetotcc.CadastroUsuario;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.projetotcc.Controller;
import com.example.projetotcc.ManterLogado.DadosOpenHelper;
import com.example.projetotcc.ManterLogado.ManterLogadoRepositorio;
import com.example.projetotcc.PaginaUsuario;
import com.example.projetotcc.R;
import com.example.projetotcc.Usuario;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

public class Cadastro5 extends AppCompatActivity {
    public static RequestQueue requestQueue;
    public static Usuario usuario;
    private int PICK_IMAGE_REQUEST = 1;
    private Context context;
    private Controller controller;
    private Uri filePath;
    private String image;
    private ImageView imageView;
    private Intent it = null;
    private SQLiteDatabase conexao;
    private DadosOpenHelper dadosOpenHelper;
    private ManterLogadoRepositorio manterLogadoRepositorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_5);
        criarConexaoInterna();
        usuario = Cadastro4.usuario;
        context = this;
        this.controller = new Controller();
        requestQueue = Volley.newRequestQueue(this.getApplicationContext());
        imageView = findViewById(R.id.imageCadUser);
    }

    public void Cadastrar(View var1) {
        controller.CadastrarUser(new Controller.VolleyCallbackUsuario() {
            @Override
            public void onSuccess(String response, Usuario user) {
                if(user == null) {
                    Toast.makeText(context.getApplicationContext(), usuario.getEmail() , Toast.LENGTH_SHORT).show();
                }
                else if(user.getEmail() == usuario.getEmail())
                {
                    it = new Intent(context, PaginaUsuario.class);
                    manterLogadoRepositorio.inserir(usuario);
                    it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                    context.startActivity(it);
                }
            }
        }, usuario);
    }

    public void SelecionarImagem(View view) {
        this.startActivityForResult(new Intent("android.intent.action.PICK", MediaStore.Images.Media.INTERNAL_CONTENT_URI), this.PICK_IMAGE_REQUEST);
    }

    protected void onActivityResult(int n, int n2, Intent intent) {
        super.onActivityResult(n, n2, intent);
        try {
            filePath = intent.getData();
            Bitmap bitmap = MediaStore.Images.Media.getBitmap((ContentResolver)this.getContentResolver(), (Uri)filePath);
            usuario.setImage(bitmap);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, (OutputStream)byteArrayOutputStream);
            usuario.setImagem(Base64.encodeToString((byte[])byteArrayOutputStream.toByteArray(), (int)0));
            this.imageView.setImageBitmap(bitmap);
            return;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void criarConexaoInterna() {
        try {
            dadosOpenHelper = new DadosOpenHelper(this);
            conexao = dadosOpenHelper.getWritableDatabase();
            manterLogadoRepositorio = new ManterLogadoRepositorio(conexao); }
        catch (SQLException ex) {}
    }
}