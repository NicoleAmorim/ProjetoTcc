package com.example.projetotcc;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.projetotcc.CadastroServico.CadastroServico;
import com.example.projetotcc.CadastroUsuario.Cadastro5;
import com.example.projetotcc.ManterLogado.DadosOpenHelper;
import com.example.projetotcc.ManterLogado.ManterLogadoRepositorio;
import com.example.projetotcc.ui.categorias.CategoriasFragment;
import com.example.projetotcc.ui.home.HomeFragment;
import com.example.projetotcc.ui.listaFragment.ListaCategoriasFragment;
import com.google.android.material.navigation.NavigationView;
import com.xwray.groupie.GroupAdapter;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.ListFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayInputStream;

public class PaginaUsuario extends AppCompatActivity {

    public static GroupAdapter adapter;
    public static Context context;
    public static RequestQueue requestQueue;
    public static Servico servico;
    private SQLiteDatabase conexao;
    private DadosOpenHelper dadosOpenHelper;
    private ManterLogadoRepositorio manterLogadoRepositorio;
    public static Servico servicop;
    public static String tipo;
    public static Usuario usuario;
    public static int view;
    private Controller controller;
    int i = 1;
    Intent it = null;
    private MainActivity login;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        criarConexaoInterna();
        super.onCreate(savedInstanceState);
        servicop = new Servico();
        adapter = new GroupAdapter();
        requestQueue = Volley.newRequestQueue(this.getApplicationContext());

        this.login = new MainActivity();
        usuario = manterLogadoRepositorio.buscarUsuario();
        ByteArrayInputStream stream = new ByteArrayInputStream(Base64.decode(usuario.getImagem().getBytes(), Base64.DEFAULT));
        usuario.setImage(BitmapFactory.decodeStream(stream));

        controller = new Controller();
        String cod = String.valueOf(usuario.getCod());

        controller.findServicoById(new Controller.VolleyCallbackProduto()
        {
            @Override
            public void onSuccess(String response, Servico p) {
                servicop = p;
            }
        }, cod);

        setContentView(R.layout.activity_pagina_usuario);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        View headerView = navigationView.getHeaderView(0);

        TextView email = (TextView) headerView.findViewById(R.id.ViewEmail);
        TextView nome = (TextView) headerView.findViewById(R.id.ViewNome);
        ImageView imagem = (ImageView) headerView.findViewById(R.id.imageUserNav);

        imagem.setImageBitmap(usuario.getImage());
        email.setText(this.usuario.getEmail());
        nome.setText(this.usuario.getNome());

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_categoria, R.id.nav_favoritos, R.id.nav_minhaLoja, R.id.nav_pedidos, R.id.nav_perfil, R.id.nav_lista, R.id.nav_sair).setDrawerLayout(drawer).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pagina_usuario, menu);
        return true;
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }


//REDIRECIONAMENTOS
    public void Sair(MenuItem menuItem) {
        manterLogadoRepositorio.excluir(usuario);
        it = new Intent(this, MainActivity.class);
        it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(it);
    }
    public void CadastroProduto(View view) {
        it = new Intent(this, CadastroServico.class);
        this.startActivity(it);
    }



//CATEGORIAS
    public void findbyCategoriaAr(View view) {
        servico = new Servico();
        tipo = "Ar-condicinado";
        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, new ListaCategoriasFragment()).commit(); }

    public void findbyCategoriaBaba(View view) {
        servico = new Servico();
        tipo = "Bab\u00e1";
        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, new ListaCategoriasFragment()).commit();    }

    public void findbyCategoriaCarpinteiro(View view) {
        servico = new Servico();
        tipo = "Carpinteiro";
        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, new ListaCategoriasFragment()).commit();    }

    public void findbyCategoriaEletricista(View view) {
        servico = new Servico();
        tipo = "Eletricista";
        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, new ListaCategoriasFragment()).commit();    }

    public void findbyCategoriaEncanador(View view) {
        servico = new Servico();
        tipo = "Encanador";
        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, new ListaCategoriasFragment()).commit();    }

    public void findbyCategoriaEntregador(View view) {
        servico = new Servico();
        tipo = "Entregador";
        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, new ListaCategoriasFragment()).commit();    }

    public void findbyCategoriaFaxina(View view) {
        servico = new Servico();
        tipo = "Faxineiro";
        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, new ListaCategoriasFragment()).commit();    }

    public void findbyCategoriaPedreiro(View view) {
        servico = new Servico();
        tipo = "Pedreiro";
        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, new ListaCategoriasFragment()).commit();    }

    public void findbyCategoriaPintor(View view) {
        servico = new Servico();
        tipo = "Pintor";
        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, new ListaCategoriasFragment()).commit();    }

    public void findbyCategoriaWashCar(View view) {
        servico = new Servico();
        tipo = "WashCar";
        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, new ListaCategoriasFragment()).commit();    }


    private void criarConexaoInterna(){
        try {
            dadosOpenHelper = new DadosOpenHelper(this);
            conexao = dadosOpenHelper.getWritableDatabase();
            manterLogadoRepositorio = new ManterLogadoRepositorio(conexao);}
        catch(SQLException ex){ }
    }
}
