package com.example.projetotcc.ui.chatUsuario;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.example.projetotcc.PaginaUsuario;
import com.example.projetotcc.R;
import com.example.projetotcc.ui.infoServico.InfoServicoFragment;
import com.example.projetotcc.ui.listaFragment.ListaCategoriasFragment;
import com.example.projetotcc.ui.pedidos.PedidosFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

import java.util.List;

import dominio.entidade.Message;
import dominio.entidade.Pedido;
import dominio.entidade.Usuario;

public class ChatUsuarioFragment extends Fragment {

    private ChatUsuarioViewModel mViewModel;
    public static GroupAdapter adapter;
    public static Usuario remetente;

    public static Usuario destinatario;
    public static EditText editmessage;
    public static ListenerRegistration registration;
    public static Context context;
    private RecyclerView rv;
    private Button send;

    public static ChatUsuarioFragment newInstance() {
        return new ChatUsuarioFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_usuario, container, false);
        editmessage = view.findViewById(R.id.editMsgm);
        rv = view.findViewById(R.id.recyclerChatUser);
        remetente = PaginaUsuario.usuario;
        send = view.findViewById(R.id.btnSendMessage);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMensagem();
            }
        });
        if(InfoServicoFragment.validar == true) {
            destinatario = InfoServicoFragment.user;
        } else {
            destinatario = PedidosFragment.usuario;
        }
        PaginaUsuario.toolbar.setTitle(destinatario.getNome());
        adapter = new GroupAdapter();
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(PaginaUsuario.context));
        Procurar();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ChatUsuarioViewModel.class);

        // TODO: Use the ViewModel
    }
    public static class MessageItem extends Item<ViewHolder> {

        private final Message message;

        public MessageItem(Message message) {
            this.message = message;
        }


        @Override
        public void bind(@NonNull ViewHolder viewHolder, int position) {
            TextView txtMsgm = viewHolder.itemView.findViewById(R.id.txt_message);
            ImageView imgMsgm = viewHolder.itemView.findViewById(R.id.imgMessage);

            txtMsgm.setText(message.getText());
            if(message.getRemetenteID().equals(String.valueOf(remetente.getId())))
            {
                Picasso.get().load(remetente.getImageUrl()).into(imgMsgm);
            }
            else
            {
                Picasso.get().load(destinatario.getImageUrl()).into(imgMsgm);
            }
        }

        @Override
        public int getLayout() {
            return message.getRemetenteID().equals(String.valueOf(remetente.getId()))
                    ? R.layout.item_chat_message_right : R.layout.item_chat_message_left;
        }
    }

        private void Procurar() {
        String fromId = PaginaUsuario.usuario.getId();
        String toId = destinatario.getId();
            Query query = FirebaseFirestore.getInstance().collection("/conversas").document(fromId).collection(toId).document("mensagem").collection("m").orderBy("time", Query.Direction.ASCENDING);
            registration = query.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        List<DocumentChange> documentChanges = queryDocumentSnapshots.getDocumentChanges();

                            for (DocumentChange doc: documentChanges) {
                                    if (doc.getType() == DocumentChange.Type.ADDED) {
                                        Message message = doc.getDocument().toObject(Message.class);
                                        adapter.add(new ChatUsuarioFragment.MessageItem(message));
                                        adapter.notifyDataSetChanged();
                                        rv.smoothScrollToPosition(adapter.getItemCount());
                                        Log.i("teste", String.valueOf(adapter.getItemCount()));

                                    }
                            }
                        }
                });
    }

    public void sendMensagem() {
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
                    .document("mensagem")
                    .collection("m")
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
                    .document("mensagem")
                    .collection("m")
                    .add(message)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("Teste", documentReference.getId());

                            Pedido pedido = new Pedido();
                            pedido.setUuid(idRementente);
                            pedido.setUsername(PaginaUsuario.usuario.getUsername());
                            pedido.setPhotoUrl(PaginaUsuario.usuario.getImageUrl());
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
}