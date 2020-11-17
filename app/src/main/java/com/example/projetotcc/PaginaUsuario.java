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
import com.example.projetotcc.cadastroServico.CadastroServico1;
import database.DadosOpenHelper;
import dominio.entidade.Pedido;
import dominio.repositorio.ManterLogadoRepositorio;

import com.example.projetotcc.cadastroUsuario.Cadastro5;
import com.example.projetotcc.config.Constants;
import com.example.projetotcc.controllers.Mensagem;
import com.example.projetotcc.controllers.SelecionarServico;
import com.example.projetotcc.models.CallBacks;
import com.example.projetotcc.models.SelecionarServicoModel;
import com.example.projetotcc.ui.editarPerfil.EditarPerfilFragment;
import com.example.projetotcc.ui.endereco.EnderecoFragment;
import com.example.projetotcc.ui.listaFragment.ListaCategoriasFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.ByteArrayInputStream;
import java.util.List;

import javax.annotation.Nullable;

import dominio.entidade.Servico;
import dominio.entidade.Usuario;

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
    private CallBacks callBacks;
    int i = 1;
    Intent it = null;
    private MainActivity login;
    private AppBarConfiguration mAppBarConfiguration;
    private Mensagem mensagem;
    public static String layout;
    private FirebaseFirestore db;
    private TextView email;
    private TextView nome;
    private ImageView imagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        criarConexaoInterna();
        servicop = new Servico();
        usuario = new Usuario();
        adapter = new GroupAdapter();
        mensagem = new Mensagem();
        db = FirebaseFirestore.getInstance();

        Logado();
        this.login = new MainActivity();
        context = this;


        callBacks = new CallBacks();
        mensagem = new Mensagem();


        setContentView(R.layout.activity_pagina_usuario);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        View headerView = navigationView.getHeaderView(0);

         email = (TextView) headerView.findViewById(R.id.ViewEmail);
         nome = (TextView) headerView.findViewById(R.id.ViewNome);
         imagem = (ImageView) headerView.findViewById(R.id.imageUserNav);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_categoria, R.id.nav_favoritos, R.id.nav_minhaLoja, R.id.nav_pedidos, R.id.nav_perfil, R.id.nav_lista, R.id.nav_editar_perfil, R.id.nav_endereco, R.id.nav_sair).setDrawerLayout(drawer).build();
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
        manterLogadoRepositorio.excluir();
        FirebaseAuth.getInstance().signOut();
        Logado();
    }

    private void Logado() {
        if (FirebaseAuth.getInstance().getUid() == null) {
            Intent intent = new Intent(PaginaUsuario.this, MainActivity.class);

            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);
        }else
        {
            FindServico();
            FindUser();
        }
    }

    public void FindUser() {
        db.collection("/users")
                .document(FirebaseAuth.getInstance().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        usuario = documentSnapshot.toObject(Usuario.class);
                        email.setText(usuario.getEmail());
                        nome.setText(usuario.getNome());
                        Picasso.get()
                                .load(usuario.getImageUrl()).into(imagem);
                    }
                });
    }
    public void FindServico() {
        db.collection("/servico")
                .document(FirebaseAuth.getInstance().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        servicop = documentSnapshot.toObject(Servico.class);
                    }
                });

    }
    public void CadastroProduto(View view) {
        it = new Intent(this, CadastroServico1.class);
        this.startActivity(it);
    }
    public void EditarPerfil(View view) {
        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, new EditarPerfilFragment()).commit(); }

    public void Endereco(View view) {
        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, new EnderecoFragment()).commit(); }


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
    public void ProcurarPedido() {
        String uid = FirebaseAuth.getInstance().getUid();
        if (uid == null) return;


        FirebaseFirestore.getInstance().collection("/last-messages")
                .document(uid)
                .collection("contacts")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        List<DocumentChange> documentChanges = queryDocumentSnapshots.getDocumentChanges();

                        if (documentChanges != null) {
                            for (DocumentChange doc: documentChanges) {
                                if (doc.getType() == DocumentChange.Type.ADDED) {
                                    Pedido pedido = doc.getDocument().toObject(Pedido.class);
                                }
                            }
                        }
                    }
                });
    }
}
