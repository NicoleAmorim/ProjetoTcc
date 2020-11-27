package com.example.projetotcc;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
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
import com.example.projetotcc.ui.home.HomeFragment;
import com.example.projetotcc.ui.infoServico.InfoServicoFragment;
import com.example.projetotcc.ui.listaFragment.ListaCategoriasFragment;
import com.example.projetotcc.ui.editarPortifolio.EditarPortifolioFragment;
import com.example.projetotcc.ui.mudarSenha.MudarSenhaFragment;
import com.example.projetotcc.ui.pedidos.PedidosFragment;
import com.example.projetotcc.ui.perfil.PerfilFragment;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;

import androidx.annotation.NonNull;
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
    public static Context context;
    public static RequestQueue requestQueue;
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
    private Usuario destinatario;

    public static RecyclerView rv;
    public static Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        servicop = new Servico();
        context = getApplicationContext();
        usuario = new Usuario();
        cep = new CEP();
        db = FirebaseFirestore.getInstance();
        rStar = new RStar(this);
        setContentView(R.layout.activity_pagina_usuario);
        ChatApplication application = (ChatApplication) getApplication();
        getApplication().registerActivityLifecycleCallbacks(application);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
                groupAdapter.clear();
                rv.getRecycledViewPool().clear();
                rv.setAdapter(groupAdapter);
                if(!newText.isEmpty()) {
                    FindServicoHome(newText);
                }
                else
                {
                    groupAdapter.clear();
                    rv.getRecycledViewPool().clear();
                }
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
    private void FindServicoHome(String message) {
        FirebaseFirestore.getInstance().collection("servico")
                .whereGreaterThan("nome", message)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();
                        groupAdapter.clear();
                        for (DocumentSnapshot doc: docs) {
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
                    }
                });
        FirebaseFirestore.getInstance().collection("servico")
                .whereGreaterThan("descricao", message)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();
                        groupAdapter.clear();
                        for (DocumentSnapshot doc: docs) {
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
                    }
                });
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

    public void sendMensagem(View view) {
        String txt = ChatUsuarioFragment.editmessage.getText().toString();

        ChatUsuarioFragment.editmessage.setText(null);
        destinatario = ChatUsuarioFragment.destinatario;
        final String idRementente = FirebaseAuth.getInstance().getUid();
        final String idDestino = destinatario.getId();
        long timestamp = System.currentTimeMillis();

        final Message message = new Message();
        message.setDestinatarioID(idDestino);
        message.setRemetenteID(idRementente);
        message.setTime(timestamp);
        message.setText(txt);
        if (!message.getText().isEmpty()) {
            FirebaseFirestore.getInstance().collection("/conversas")
                    .document(idRementente)
                    .collection(idDestino)
                    .add(message)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("Teste", documentReference.getId());

                            Pedido pedido = new Pedido();
                            pedido.setUuid(idDestino);
                            pedido.setUsername(destinatario.getUsername());
                            pedido.setPhotoUrl(destinatario.getImageUrl());
                            pedido.setTimestamp(message.getTime());
                            pedido.setLastMessage(message.getText());

                            FirebaseFirestore.getInstance().collection("/ultima-mensagem")
                                    .document(idRementente)
                                    .collection("pedidos")
                                    .document(idDestino)
                                    .set(pedido);

                            if (!destinatario.isOnline()) {
                                Notification notification = new Notification();
                                notification.setDestinatarioID(message.getDestinatarioID());
                                notification.setRemetenteID(message.getRemetenteID());
                                notification.setTime(message.getTime());
                                notification.setText(message.getText());
                                notification.setFromName(usuario.getUsername());

                                FirebaseFirestore.getInstance().collection("/notifications")
                                        .document(destinatario.getToken())
                                        .set(notification);
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("Teste", e.getMessage(), e);
                        }
                    });

            FirebaseFirestore.getInstance().collection("/conversas")
                    .document(idDestino)
                    .collection(idRementente)
                    .add(message)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("Teste", documentReference.getId());

                            Pedido pedido = new Pedido();
                            pedido.setUuid(idRementente);
                            pedido.setUsername(usuario.getUsername());
                            pedido.setPhotoUrl(usuario.getImageUrl());
                            pedido.setTimestamp(message.getTime());
                            pedido.setLastMessage(message.getText());

                            FirebaseFirestore.getInstance().collection("/ultima-mensagem")
                                    .document(idDestino)
                                    .collection("pedidos")
                                    .document(idRementente)
                                    .set(pedido);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("Teste", e.getMessage(), e);
                        }
                    });
        }
    }
    public  void Solicitar(View view)
    {
        final Usuario remetente = usuario;
        final Usuario destinatario = InfoServicoFragment.user;
        final Message message = new Message();
        message.setDestinatarioID(destinatario.getId());
        message.setRemetenteID(remetente.getId());
        message.setTime(System.currentTimeMillis());
        message.setText("Gostaria de solicitar seu serviço de "+ InfoServicoFragment.servico.getTipo());
        if (!message.getText().isEmpty()) {
            FirebaseFirestore.getInstance().collection("/conversas")
                    .document(remetente.getId())
                    .collection(destinatario.getId())
                    .add(message)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("Teste", documentReference.getId());

                            Pedido pedido = new Pedido();
                            pedido.setUuid(destinatario.getId());
                            pedido.setUsername(destinatario.getUsername());
                            pedido.setPhotoUrl(destinatario.getImageUrl());
                            pedido.setTimestamp(message.getTime());
                            pedido.setLastMessage(message.getText());

                            FirebaseFirestore.getInstance().collection("/ultima-mensagem")
                                    .document(remetente.getId())
                                    .collection("pedidos")
                                    .document(destinatario.getId())
                                    .set(pedido);

                            if (!destinatario.isOnline()) {
                                Notification notification = new Notification();
                                notification.setDestinatarioID(message.getDestinatarioID());
                                notification.setRemetenteID(message.getRemetenteID());
                                notification.setTime(message.getTime());
                                notification.setText(message.getText());
                                notification.setFromName(usuario.getUsername());

                                FirebaseFirestore.getInstance().collection("/notifications")
                                        .document(destinatario.getToken())
                                        .set(notification);
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("Teste", e.getMessage(), e);
                        }
                    });

            FirebaseFirestore.getInstance().collection("/conversas")
                    .document(destinatario.getId())
                    .collection(remetente.getId())
                    .add(message)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("Teste", documentReference.getId());

                            Pedido pedido = new Pedido();
                            pedido.setUuid(remetente.getId());
                            pedido.setUsername(usuario.getUsername());
                            pedido.setPhotoUrl(usuario.getImageUrl());
                            pedido.setTimestamp(message.getTime());
                            pedido.setLastMessage(message.getText());

                            FirebaseFirestore.getInstance().collection("/ultima-mensagem")
                                    .document(destinatario.getId())
                                    .collection("pedidos")
                                    .document(remetente.getId())
                                    .set(pedido);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("Teste", e.getMessage(), e);
                        }
                    });
        }
        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, new ChatUsuarioFragment()).commit();    }


    public  void Portifolio(View view)
    {
        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, new PortifolioFragment()).commit();    }
    @Override
    public void onBackPressed()
    {
        new AlertDialog.Builder(PaginaUsuario.context)
                .setTitle("Sair")
                .setMessage("Tem certeza que deseja sair?")
                .setPositiveButton("sim", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finishAffinity();

                    } }).setNegativeButton("não", null) .show();


    }
    public static class Rating
    {
        private int rating;

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }
    }
    public  void R1(View view) {
        Rating rating = new Rating();
        rating.setRating(1);
        FirebaseFirestore.getInstance().collection("avaliacao")
                .document(PedidosFragment.pedido.getUuid())
                .collection("R")
                .document(usuario.getId())
                .set(rating)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        rStar.DismissDialog();
                    }
                });
    }
    public  void R2(View view) {
        Rating rating = new Rating();
        rating.setRating(2);
        FirebaseFirestore.getInstance().collection("avaliacao")
                .document(PedidosFragment.pedido.getUuid())
                .collection("R")
                .document(usuario.getId())
                .set(rating)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        rStar.DismissDialog();
                    }
                });
    }
    public  void R3(View view) {
        Rating rating = new Rating();
        rating.setRating(3);
        FirebaseFirestore.getInstance().collection("avaliacao")
                .document(PedidosFragment.pedido.getUuid())
                .collection("R")
                .document(usuario.getId())
                .set(rating)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        rStar.DismissDialog();
                    }
                });
    }
    public  void R4(View view) {
        Rating rating = new Rating();
        rating.setRating(4);
        FirebaseFirestore.getInstance().collection("avaliacao")
                .document(PedidosFragment.pedido.getUuid())
                .collection("R")
                .document(usuario.getId())
                .set(rating)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        rStar.DismissDialog();
                    }
                });
    }
    public  void R5(View view) {
        Rating rating = new Rating();
        rating.setRating(5);
        FirebaseFirestore.getInstance().collection("avaliacao")
                .document(PedidosFragment.pedido.getUuid())
                .collection("R")
                .document(usuario.getId())
                .set(rating)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        FirebaseFirestore.getInstance().collection("conversas")
                                .document(FirebaseAuth.getInstance().getUid())
                                .delete();
                        FirebaseFirestore.getInstance().collection("conversas")
                                .document(PedidosFragment.pedido.getUuid())
                                .collection(FirebaseAuth.getInstance().getUid())
                                .document()
                                .delete();
                        rStar.DismissDialog();
                    }
                });
    }
}
