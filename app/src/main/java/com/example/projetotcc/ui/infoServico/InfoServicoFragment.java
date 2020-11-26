package com.example.projetotcc.ui.infoServico;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.RequestQueue;
import com.example.projetotcc.R;
import com.example.projetotcc.ui.favoritos.FavoritosFragment;
import com.example.projetotcc.ui.listaFragment.ListaCategoriasFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import dominio.entidade.CEP;
import dominio.entidade.Servico;
import dominio.entidade.Usuario;

public class InfoServicoFragment extends Fragment {

    private InfoServicoViewModel mViewModel;
    public static Servico servico;
    public static TextView  userName, email,estado, descricao, tell, tipo, cidade;
    public static ImageView imageView;
    public static Usuario user;
    public static boolean validar;
    protected Intent it;
    private CEP cep;


    public static InfoServicoFragment newInstance() {
        return new InfoServicoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil_prestador, container, false);
        user = new Usuario();
        cep = new CEP();
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
                        FirebaseFirestore.getInstance().collection("/endereco")
                                .document(servico.getIDUser())
                                .get()
                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        cep = documentSnapshot.toObject(CEP.class);
                                        userName.setText(user.getNome());
                                        tipo.setText(servico.getTipo());
                                        estado.setText(cep.getEstado());
                                        cidade.setText(cep.getCidade());
                                        tell.setText(String.valueOf(user.getTel()));
                                        email.setText(user.getEmail());
                                        descricao.setText(servico.getDescricao());
                                        Picasso.get().load(user.getImageUrl()).into(imageView);
                                    }
                                });
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