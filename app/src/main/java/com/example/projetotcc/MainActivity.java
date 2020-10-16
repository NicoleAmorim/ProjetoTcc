package com.example.projetotcc;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.projetotcc.CadastroUsuario.Cadastro1;
import com.example.projetotcc.ManterLogado.DadosOpenHelper;
import com.example.projetotcc.ManterLogado.ManterLogadoRepositorio;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public static RequestQueue requestQueue;
    public static Usuario usuario;
    private SQLiteDatabase conexao;
    private DadosOpenHelper dadosOpenHelper;
    private ManterLogadoRepositorio manterLogadoRepositorio;
    private Controller controller;
    public EditText emailm;
    public EditText senham;
    public static Context context;
    public static LoadingDialog loadingDialog;
    Intent it = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        criarConexaoInterna();
        try {
            if(manterLogadoRepositorio.buscarUsuario() != null)
            {
                it = new Intent(context, PaginaUsuario.class);
                it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(it);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_main);
        emailm = (EditText)this.findViewById(R.id.emailL);
        senham = (EditText)this.findViewById(R.id.senhaL);
        usuario = new Usuario();
        controller = new Controller();
        loadingDialog = new LoadingDialog(MainActivity.this);
        requestQueue = Volley.newRequestQueue(this.getApplicationContext());
    }

    public void Entrar(View view) throws Exception
    {
         final String Email = this.emailm.getText().toString();
         final String Senha = this.senham.getText().toString();
        loadingDialog.StartActivityLogin();
        controller.Login(new Controller.VolleyCallbackUsuario(){
            @Override
            public void onSuccess(String result, Usuario user) {

                if (user.getEmail() != null) {
                        usuario = user;
                        loadingDialog.DismissDialog();
                        manterLogadoRepositorio.inserir(usuario);
                        it = new Intent(context, PaginaUsuario.class);
                        it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(it);
                }
                else {
                    loadingDialog.DismissDialog();
                    Toast.makeText(context.getApplicationContext(), "Usuario não encontrado", Toast.LENGTH_SHORT).show();
                }
            }
        }, Email, Senha);

    }
    public void Cadastrar(View view)
    {
        it = new Intent(this, Cadastro1.class);
        this.startActivity(it);
    }

    private void criarConexaoInterna(){
        try {
            dadosOpenHelper = new DadosOpenHelper(this);
            conexao = dadosOpenHelper.getWritableDatabase();
            manterLogadoRepositorio = new ManterLogadoRepositorio(conexao); }
        catch(SQLException ex){}
    }

}