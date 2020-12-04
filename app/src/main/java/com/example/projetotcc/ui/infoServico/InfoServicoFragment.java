package com.example.projetotcc.ui.infoServico;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.RequestQueue;
import com.example.projetotcc.PaginaUsuario;
import com.example.projetotcc.R;
import com.example.projetotcc.RStar;
import com.example.projetotcc.ui.chatUsuario.ChatUsuarioFragment;
import com.example.projetotcc.ui.favoritos.FavoritosFragment;
import com.example.projetotcc.ui.listaFragment.ListaCategoriasFragment;
import com.example.projetotcc.ui.pesquisar.HomeFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.List;

import dominio.entidade.CEP;
import dominio.entidade.Message;
import dominio.entidade.Pedido;
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
    public static CEP cep;
    private RatingBar ratingBar;
    private TextView send;
    private RelativeLayout quantidade;


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
        send = view.findViewById(R.id.solicitarBtn);
        quantidade = view.findViewById(R.id.QntddAvaliacoes);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Solicitar();
            }
        });
    try {
        Log.i("teste", servico.getIDUser());
    } catch (Exception e) {
        servico = FavoritosFragment.servico;
        try {
            Log.i("teste", servico.getIDUser());
        } catch (Exception exception) {
            servico = HomeFragment.servico;
            exception.printStackTrace();
        }

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
        ratingBar = view.findViewById(R.id.estrelas);

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
                                        R(user.getId());
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
    private void R(String id)
    {
        FirebaseFirestore.getInstance().collection("/avaliacao")
                .document(id)
                .collection("R")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        List<DocumentChange> documentChanges = queryDocumentSnapshots.getDocumentChanges();
                        if (documentChanges != null) {
                            int i = 0;
                            int d = 0;
                            for (DocumentChange doc: documentChanges) {
                                if (doc.getType() == DocumentChange.Type.ADDED) {
                                    RStar.Rating rating;
                                    rating = new RStar.Rating();
                                    rating =  doc.getDocument().toObject(RStar.Rating.class);
                                    d+= rating.getRating();
                                    i++;
                                }

                            }
                            try {
                                final int finalI = i;
                                quantidade.setOnLongClickListener(new View.OnLongClickListener() {
                                   @Override
                                   public boolean onLongClick(View v) {
                                       new AlertDialog.Builder(PaginaUsuario.getContext)
                                               .setTitle("NÚMERO DE AVALIAÇÕES")
                                               .setMessage(String.valueOf(finalI))
                                               .show();
                                       return false;
                                   }
                               });

                                ratingBar.setNumStars(d / i);
                            } catch (Exception exception) {
                                exception.printStackTrace();
                                ratingBar.setVisibility(View.INVISIBLE);
                            }
                        }
                    }
                });
    }
    public  void Solicitar()
    {
        final Usuario remetente = PaginaUsuario.usuario;
        final Usuario destinatario = InfoServicoFragment.user;
        final Message message = new Message();
        message.setDestinatarioID(destinatario.getId());
        message.setRemetenteID(remetente.getId());
        message.setTime(System.currentTimeMillis());
        message.setServidor(false);
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
                            pedido.setServidor(false);
                            pedido.setUsername(destinatario.getUsername());
                            pedido.setPhotoUrl(destinatario.getImageUrl());
                            pedido.setTimestamp(message.getTime());
                            pedido.setLastMessage(message.getText());

                            FirebaseFirestore.getInstance().collection("/ultima-mensagem")
                                    .document(remetente.getId())
                                    .collection("pedidos")
                                    .document(destinatario.getId())
                                    .set(pedido);
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
                            pedido.setServidor(true);
                            pedido.setUsername(PaginaUsuario.usuario.getUsername());
                            pedido.setPhotoUrl(PaginaUsuario.usuario.getImageUrl());
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
        try{
            ChatUsuarioFragment.registration.remove();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, new ChatUsuarioFragment()).commit();    }
}