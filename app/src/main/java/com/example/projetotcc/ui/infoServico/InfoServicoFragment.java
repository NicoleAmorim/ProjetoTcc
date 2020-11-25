package com.example.projetotcc.ui.infoServico;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.example.projetotcc.PaginaUsuario;
import com.example.projetotcc.R;
import com.example.projetotcc.controllers.Mensagem;
import com.example.projetotcc.controllers.SelecionarUsuario;
import com.example.projetotcc.models.CallBacks;
import com.example.projetotcc.ui.favoritos.FavoritosFragment;
import com.example.projetotcc.ui.listaFragment.ListaCategoriasFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

import database.DadosOpenHelperMessage;
import dominio.entidade.Message;
import dominio.entidade.Servico;
import dominio.entidade.Usuario;
import dominio.repositorio.ManterLogadoRepositorio;

public class InfoServicoFragment extends Fragment {

    private InfoServicoViewModel mViewModel;
    public static Servico servico;
    public static TextView  userName, email,estado, descricao, tell, tipo, cidade;
    public static ImageView imageView;
    private CallBacks callBacks;

    private SelecionarUsuario selecionarUsuario;
    public static RequestQueue requestQueue;
    public static Usuario user;
    public static boolean validar;
    protected Intent it;


    public static InfoServicoFragment newInstance() {
        return new InfoServicoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil_prestador, container, false);
        user = new Usuario();
        servico = ListaCategoriasFragment.servico;
    try {
        Log.i("teste", servico.getIDUser());
    } catch (Exception e) {
     servico = FavoritosFragment.servico;
     e.printStackTrace();
    }

        SelecionarUserFireBase(servico.getIDUser());
        validar = true;

        imageView = view.findViewById(R.id.imgPerfilServico);
        userName = view.findViewById(R.id.nomePerfilServico);
        tipo = view.findViewById(R.id.ServicoPerfilServico);
        estado = view.findViewById(R.id.EstadoPerfilServico);
        cidade = view.findViewById(R.id.CidadePerfilServico);
        tell = view.findViewById(R.id.tellPerfilServico);
        email = view.findViewById(R.id.EmailPerfilServico);
        descricao = view.findViewById(R.id.DescricaoPerfilServico);

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

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(InfoServicoViewModel.class);

        // TODO: Use the ViewModel
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