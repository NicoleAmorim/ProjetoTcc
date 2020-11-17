package com.example.projetotcc.cadastroServico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.projetotcc.LoadingDialog;
import com.example.projetotcc.controllers.ValidarCadastroServico;
import com.example.projetotcc.models.CadastroServicoModel;
import com.example.projetotcc.PaginaUsuario;
import com.example.projetotcc.R;
import dominio.entidade.Usuario;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class CadastroServico1 extends AppCompatActivity {
    public static Uri filePath;
    public static RequestQueue requestQueue;
    private int PICK_IMAGE_REQUEST = 1;
    public static Context context;
    protected CadastroServicoModel cadastroServicoModel;
    private ValidarCadastroServico validarCadastroServico;
    private EditText descricao;
    private String image;
    private ImageView imageView;
    private EditText nome;
    private EditText preco;
    private String tipo;
    private Usuario usuario;
    protected LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_servico);
        loadingDialog = new LoadingDialog(this);
        this.nome = (EditText)this.findViewById(R.id.nomeServico);
        this.preco = (EditText)this.findViewById(R.id.precoServico);
        this.descricao = (EditText)this.findViewById(R.id.descricaoServico);
        this.imageView = (ImageView)this.findViewById(R.id.imgProduto);

        context = this;
        usuario = PaginaUsuario.usuario;
        validarCadastroServico = new ValidarCadastroServico();
        Spinner spinner = (Spinner)this.findViewById(R.id.tipoProduto);

        String[] StringTipo = new String[]{
                "Tipo", "Eletricista", "Encanador", "Pedreiro", "Pintor", "Carpinteiro", "Entregador", "Faxineiro", "Babá", "WashCar", "Ar-condicinado"
        };

        ArrayAdapter arrayAdapterTipo = new ArrayAdapter(this, R.layout.spinner_item, new ArrayList(Arrays.asList(StringTipo))) {
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView)view;
                if (position == 0) { tv.setTextColor(Color.GRAY);return view; } else { tv.setTextColor(Color.BLACK);return view; }
            }
            public boolean isEnabled(int position) { return position != 0; }
        };
        arrayAdapterTipo.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(arrayAdapterTipo);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView parent, View view, int position, long id) {
                tipo = (String)parent.getItemAtPosition(position);
            }
            public void onNothingSelected(AdapterView var1) { }
        });

        this.requestQueue = Volley.newRequestQueue(this.getApplicationContext());
    }

    public void CadastrarServico(View view) {
        final String nome = this.nome.getText().toString();
        final String preco = this.preco.getText().toString();
        final String descricao = this.descricao.getText().toString();
        loadingDialog.StartActivityLogin();
        validarCadastroServico.ValidarCadastroServico(nome, tipo, preco, descricao,filePath, usuario);
    }

    public void SelecionarImagem(View view) {
        this.startActivityForResult(new Intent("android.intent.action.PICK", MediaStore.Images.Media.INTERNAL_CONTENT_URI), this.PICK_IMAGE_REQUEST);
    }

    protected void onActivityResult(int n, int n2, Intent intent) {
        super.onActivityResult(n, n2, intent);
        try {
            filePath = intent.getData();
            Bitmap bitmap = MediaStore.Images.Media.getBitmap((ContentResolver)this.getContentResolver(), (Uri)filePath);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, (OutputStream)byteArrayOutputStream);
            this.image = Base64.encodeToString((byte[])byteArrayOutputStream.toByteArray(), (int)0);
            this.imageView.setImageBitmap(bitmap);
            return;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}