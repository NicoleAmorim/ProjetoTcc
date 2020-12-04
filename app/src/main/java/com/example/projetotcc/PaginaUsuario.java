package com.example.projetotcc;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Application;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.projetotcc.cadastroServico.CadastroServico1;

import dominio.entidade.CEP;
import dominio.entidade.Favoritos;
import dominio.entidade.Message;
import dominio.entidade.Pedido;

import com.example.projetotcc.ui.chatUsuario.ChatUsuarioFragment;
import com.example.projetotcc.ui.editarPerfil.EditarPerfilFragment;
import com.example.projetotcc.ui.editarServico.EditarServicoFragment;
import com.example.projetotcc.ui.endereco.EnderecoFragment;
import com.example.projetotcc.ui.filtrar.FiltrarFragment;
import com.example.projetotcc.ui.infoServico.InfoServicoFragment;
import com.example.projetotcc.ui.listaFragment.ListaCategoriasFragment;
import com.example.projetotcc.ui.editarPortifolio.EditarPortifolioFragment;
import com.example.projetotcc.ui.mudarSenha.MudarSenhaFragment;
import com.example.projetotcc.ui.pedidos.PedidosFragment;
import com.example.projetotcc.ui.perfil.PerfilFragment;
import com.example.projetotcc.ui.pesquisar.HomeFragment;
import com.example.projetotcc.ui.portifolio.PortifolioFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.navigation.NavigationView;
import com.google.common.reflect.Parameter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

import dominio.entidade.Servico;
import dominio.entidade.Usuario;

public class PaginaUsuario extends AppCompatActivity {

    public static GroupAdapter groupAdapter;
    public static Context context, getContext;
    public static Servico servico;
    public static RStar rStar;
    public static Servico servicop;
    public static String tipo;
    public static Usuario usuario;
    public static CEP cep;
    public static int view;
    public static
    int i = 1;
    Intent it = null;
    private AppBarConfiguration mAppBarConfiguration;
    public static String layout;
    private FirebaseFirestore db;
    private TextView email;
    private TextView nome;
    private ImageView imagem;
    private DrawerLayout drawer;

    public static RecyclerView rv;
    public static Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        servicop = new Servico();
        getContext = this;
        context = getApplicationContext();
        usuario = new Usuario();
        cep = new CEP();
        db = FirebaseFirestore.getInstance();
        rStar = new RStar(PaginaUsuario.this);
        setContentView(R.layout.activity_pagina_usuario);
        ChatApplication application = (ChatApplication) getApplication();
        getApplication().registerActivityLifecycleCallbacks(application);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);



        View headerView = navigationView.getHeaderView(0);

        email = (TextView) headerView.findViewById(R.id.ViewEmail);
        nome = (TextView) headerView.findViewById(R.id.ViewNome);
        imagem = (ImageView) headerView.findViewById(R.id.imageUserNav);
        Logado();
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_categoria, R.id.nav_favoritos, R.id.nav_minhaLoja, R.id.nav_pedidos, R.id.nav_perfil, R.id.nav_lista, R.id.nav_editar_perfil, R.id.nav_endereco, R.id.nav_sair ).setDrawerLayout(drawer).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);



        updateToken();
        groupAdapter = new GroupAdapter();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pagina_usuario, menu);
        MenuItem item = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                groupAdapter.clear();
                if(!query.isEmpty()) {
                    getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, new HomeFragment()).commit();
                    FindServicoHome(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }
    private void FindServicoHome(final String message) {
        try{
            Integer.parseInt(message);
            new AlertDialog.Builder(PaginaUsuario.getContext)
                    .setTitle("0 Resultados")
                    .setMessage("Nenhum serviço com o nome " + message + " foi encontrado ")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, new com.example.projetotcc.ui.home.HomeFragment()).commit();

                        }
                    }).setIcon(R.drawable.ic_sair_64).show();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            FirebaseFirestore.getInstance().collection("servico")
                    .whereEqualTo("nome", message)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                            if (!queryDocumentSnapshots.isEmpty()) {
                                if (e != null) {
                                    Log.e("Teste", e.getMessage(), e);
                                    return;
                                }
                                List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();
                                groupAdapter.clear();
                                for (DocumentSnapshot doc : docs) {
                                    final Servico servico = doc.toObject(Servico.class);
                                    String uid = FirebaseAuth.getInstance().getUid();
                                    if (servico.getIDUser().equals(uid))
                                        continue;
                                    FirebaseFirestore.getInstance().collection("/users")
                                            .document(servico.getIDUser())
                                            .get()
                                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                @Override
                                                public void onSuccess(final DocumentSnapshot documentSnapshotUser) {
                                                    FirebaseFirestore.getInstance().collection("/favoritos")
                                                            .document(FirebaseAuth.getInstance().getUid())
                                                            .collection("servico")
                                                            .document(servico.getIDUser())
                                                            .get()
                                                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                @Override
                                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                    Usuario usuario = documentSnapshotUser.toObject(Usuario.class);
                                                                    Favoritos favoritos = documentSnapshot.toObject(Favoritos.class);
                                                                    groupAdapter.add(new ListaCategoriasFragment.ServicoItem(servico, usuario, favoritos));
                                                                    groupAdapter.notifyDataSetChanged();
                                                                }
                                                            });
                                                }
                                            });
                                }
                            } else {
                                new AlertDialog.Builder(PaginaUsuario.getContext)
                                        .setTitle("0 Resultados")
                                        .setMessage("Nenhum serviço com o nome " + message + " foi encontrado ")
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, new com.example.projetotcc.ui.home.HomeFragment()).commit();

                                            }
                                        }).setIcon(R.drawable.ic_sair_64).show();
                            }
                        }
                    });
        }
    }
    //REDIRECIONAMENTOS
    public void Sair(MenuItem menuItem) {
        FirebaseFirestore.getInstance().collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .update("online", false);
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
            FindEndereco();
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
    public void FindEndereco() {
        db.collection("/endereco")
                .document(FirebaseAuth.getInstance().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        cep = documentSnapshot.toObject(CEP.class);
                    }
                });

    }
    public void CadastroProduto(View view) {
        it = new Intent(this, CadastroServico1.class);
        this.startActivity(it);
    }
    public void EditarPerfil(View view) {
        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, new EditarPerfilFragment()).commit(); }
    public void EditarServico(View view) {
        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, new EditarServicoFragment()).commit(); }
    public void Perfil(View view) {
        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, new PerfilFragment()).commit(); }
    public void MudarSenhaPerfil(View view) {
        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, new MudarSenhaFragment()).commit(); }
    public void Endereco(View view) {
        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, new EnderecoFragment()).commit(); }
    public void EditarEndereco(View view) {
        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, new com.example.projetotcc.ui.editarEndereco.EnderecoFragment()).commit(); }
    public void editarPortifolio(View view) {
        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, new EditarPortifolioFragment()).commit(); }
    public void irFiltro(View view) {
        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, new FiltrarFragment()).commit(); }
    public void filtrar(View view) {
        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, new ListaCategoriasFragment()).commit(); }




    private void updateToken() {

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "getInstanceId failed", task.getException());
                            return;
                        }

                        String token = task.getResult().getToken();

                        String uid = FirebaseAuth.getInstance().getUid();

                        if (uid != null) {
                            FirebaseFirestore.getInstance().collection("users")
                                    .document(uid)
                                    .update("token", token);
                        }
                    }
                });
    }
    public  void Portifolio(View view)
    {
        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, new PortifolioFragment()).commit();    }
    @Override
    public void onBackPressed()
    {
        if(drawer.isOpen())
        {
            new AlertDialog.Builder(PaginaUsuario.getContext)
                    .setTitle("Sair")
                    .setMessage("Deseja sair ?")
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            System.exit(0);
                        } }).setNegativeButton("Não", null).setIcon(R.drawable.ic_sair_64).show();
        }else{
            drawer.open();
        }
    }

    public void DisplayTrack(View view){

        try {
            Uri uri = Uri.parse("https://www.google.co.in/maps/dir/" + cep.getCidade()+","+cep.getEstado() + "/" + InfoServicoFragment.cep.getCidade()+","+InfoServicoFragment.cep.getEstado());
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        catch(ActivityNotFoundException e){
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}
