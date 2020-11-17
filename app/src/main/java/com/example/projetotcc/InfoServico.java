package com.example.projetotcc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.projetotcc.config.Constants;
import com.example.projetotcc.controllers.SelecionarUsuario;
import com.example.projetotcc.models.CallBacks;
import com.example.projetotcc.ui.listaFragment.ListaCategoriasFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import dominio.entidade.Servico;
import dominio.entidade.Usuario;

public class InfoServico extends AppCompatActivity {

    public static Servico servico;
    public static TextView  userName, email,estado, descricao, tell, tipo, cidade;
    public static ImageView imageView;
    private CallBacks callBacks;

    private SelecionarUsuario selecionarUsuario;
    public static RequestQueue requestQueue;
    public static Usuario user;
    public static Context context;
    public static boolean validar;
    protected Intent it;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_perfil_prestador);
        requestQueue = Volley.newRequestQueue(this.getApplicationContext());
        user = new Usuario();
        servico = getIntent().getExtras().getParcelable("servico");

        Log.i("teste", servico.getIDUser());
        SelecionarUserFireBase(servico.getIDUser());
        context = this;
        validar = true;

        imageView = findViewById(R.id.imgPerfilServico);
        userName = findViewById(R.id.nomePerfilServico);
        tipo = findViewById(R.id.ServicoPerfilServico);
        estado = findViewById(R.id.EstadoPerfilServico);
        cidade = findViewById(R.id.CidadePerfilServico);
        tell = findViewById(R.id.tellPerfilServico);
        email = findViewById(R.id.EmailPerfilServico);
        descricao = findViewById(R.id.DescricaoPerfilServico);

        FirebaseFirestore.getInstance().collection("/users")
                .document(servico.getIDUser())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        user = documentSnapshot.toObject(Usuario.class);
                        userName.setText(user.getNome());
                        tipo.setText(servico.getTipo());
                        estado.setText(user.getNome());
                        cidade.setText(servico.getTipo());
                        tell.setText(String.valueOf(user.getTel()));
                        email.setText(user.getEmail());
                        descricao.setText(servico.getDescricao());
                        Picasso.get().load(user.getImageUrl()).into(imageView);
                    }
                });
    }
    public  void Solicitar(View view)
    {
        it = new Intent(InfoServico.context, ChatUsuario.class);
        context.startActivity(it);
    }
    public  void Portifolio(View view)
    {
    }

    private void SelecionarUserFireBase(String id)
    {
        FirebaseFirestore.getInstance().collection("/users")
                .document(servico.getIDUser())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        user = documentSnapshot.toObject(Usuario.class);
                    }
                });
    }
}