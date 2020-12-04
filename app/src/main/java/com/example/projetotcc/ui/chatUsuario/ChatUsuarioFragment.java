package com.example.projetotcc.ui.chatUsuario;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetotcc.PaginaUsuario;
import com.example.projetotcc.R;
import com.example.projetotcc.ui.infoServico.InfoServicoFragment;
import com.example.projetotcc.ui.pedidos.PedidosFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;
import com.xwray.groupie.OnItemLongClickListener;
import com.xwray.groupie.ViewHolder;

import java.util.List;

import dominio.entidade.CEP;
import dominio.entidade.Message;
import dominio.entidade.Notification;
import dominio.entidade.Pedido;
import dominio.entidade.Usuario;

public class ChatUsuarioFragment extends Fragment {

    private ChatUsuarioViewModel mViewModel;
    public static GroupAdapter adapter;
    public static Usuario remetente;

    public static Usuario destinatario;
    public static EditText editmessage;
    public static ListenerRegistration registration, registration2;
    public static Context context;
    private RecyclerView rv;
    private Button send;
    private ImageView local;

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
        local = view.findViewById(R.id.btnLocolizção);
        if(PedidosFragment.pedido.isServidor()) {
            local.setVisibility(View.INVISIBLE);
        }
        local.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(PaginaUsuario.getContext)
                        .setTitle("Enviar Localização")
                        .setMessage("Deseja mesmo enviar sua localização ?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                sendLocal();
                            } })
                        .setNegativeButton( "Não", null).setIcon(R.drawable.ic_location) .show();
            }
        });
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
    public static class MessageItemLocal extends Item<ViewHolder> {

        private final Message message;
        private final CEP cep;

        public MessageItemLocal(Message message, CEP cep) {
            this.message = message;
            this.cep = cep;
        }


        @Override
        public void bind(@NonNull ViewHolder viewHolder, int position) {
            ImageView imgMsgm = viewHolder.itemView.findViewById(R.id.imgMessage);
          try {
              if (message.getRemetenteID().equals(String.valueOf(remetente.getId()))) {
                  Picasso.get().load(remetente.getImageUrl()).into(imgMsgm);
              } else {
                  Picasso.get().load(destinatario.getImageUrl()).into(imgMsgm);
              }
          } catch (Exception exception) {
              exception.printStackTrace();
          }
        }

        @Override
        public int getLayout() {
            return message.getRemetenteID().equals(String.valueOf(remetente.getId()))
                    ? R.layout.item_chat_message_local_right : R.layout.item_chat_message_local_left;
        }
    }

        private void Procurar() {
        final String fromId = PaginaUsuario.usuario.getId();
        final String toId = destinatario.getId();
            Query query = FirebaseFirestore.getInstance().collection("/conversas").document(fromId).collection(toId).orderBy("time", Query.Direction.ASCENDING);
            registration = query.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                        List<DocumentChange> documentChanges = queryDocumentSnapshots.getDocumentChanges();
                            for (DocumentChange doc: documentChanges) {
                                    if (doc.getType() == DocumentChange.Type.ADDED) {
                                            Message message = doc.getDocument().toObject(Message.class);
                                            if(message.getText().equals(message.getDestinatarioID()) || message.getText().equals(message.getRemetenteID()))
                                            {
                                                Log.i("texto", message.getText());
                                                final Message finalMessage = message;
                                                FirebaseFirestore.getInstance().collection("/endereco")
                                                        .document(message.getText())
                                                        .get()
                                                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                            @Override
                                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                final CEP cep = documentSnapshot.toObject(CEP.class);
                                                                if (cep.getCEP() != null) {
                                                                    Log.i("CEP", cep.getCEP());
                                                                    adapter.add(new MessageItemLocal(finalMessage, cep));
                                                                    adapter.setOnItemClickListener(new OnItemClickListener() {
                                                                                                       @Override
                                                                                                       public void onItemClick(@NonNull Item item, @NonNull View view) {
                                                                                                           try {
                                                                                                               Uri uri = Uri.parse("https://www.google.co.in/maps/dir/" +PaginaUsuario.cep.getCEP()+ "/" + cep.getCEP());
                                                                                                               Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                                                                                               intent.setPackage("com.google.android.apps.maps");
                                                                                                               intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                                                                               startActivity(intent);
                                                                                                           } catch (ActivityNotFoundException e) {
                                                                                                               Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
                                                                                                               Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                                                                                               Log.i("ggg", "GG");
                                                                                                               intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                                                                               startActivity(intent);
                                                                                                           }
                                                                                                       }
                                                                                                   }
                                                                    );
                                                                    adapter.setOnItemLongClickListener(new OnItemLongClickListener() {
                                                                                                           @Override
                                                                                                           public boolean onItemLongClick(@NonNull Item item, @NonNull View view) {
                                                                                                               new AlertDialog.Builder(PaginaUsuario.getContext)
                                                                                                                       .setTitle("LOCALIZAÇÃO")
                                                                                                                       .setMessage("Bairro: " + cep.getBairro()+"\nRua: " + cep.getRua() + ", " + cep.getNumero())
                                                                                                                       .setIcon(R.drawable.ic_location)
                                                                                                                       .setNeutralButton("OK", null).show();
                                                                                                               return false;
                                                                                                           }
                                                                                                       }
                                                                    );
                                                                }
                                                            }
                                                        });
                                            }else {
                                                adapter.add(new ChatUsuarioFragment.MessageItem(message));
                                            }
                                            }
                                        adapter.notifyDataSetChanged();
                                        rv.smoothScrollToPosition(adapter.getItemCount());
                                        Log.i("teste", String.valueOf(adapter.getItemCount()));

                                    }
                            }
                });
            Query quer = FirebaseFirestore.getInstance().collection("/noti").document(fromId).collection(toId);
            registration2 = quer.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshotsNoti, @Nullable FirebaseFirestoreException e) {
                    List<DocumentChange> documentChange = queryDocumentSnapshotsNoti.getDocumentChanges();
                    for (DocumentChange doc: documentChange) {
                        FirebaseFirestore.getInstance()
                                .collection("/noti")
                                .document(fromId)
                                .collection(toId)
                                .document(doc.getDocument().getId())
                                .delete();
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
        final Pedido pedidos = new Pedido();;
        final Message message = new Message();
        message.setDestinatarioID(idDestino);
        message.setRemetenteID(idRementente);
        message.setServidor(true);
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
                            message.setID(documentReference.getId());
                            FirebaseFirestore.getInstance().collection("/conversas")
                                    .document(idRementente)
                                    .collection(idDestino)
                                    .document(documentReference.getId())
                                    .set(message);
                            Pedido pedido = new Pedido();
                            pedido.setUuid(idDestino);
                            try {
                                pedidos.setServidor(!PedidosFragment.pedido.isServidor());
                                pedido.setServidor(PedidosFragment.pedido.isServidor());
                            } catch (Exception exception) {
                                pedido.setServidor(false);
                                pedidos.setServidor(true);
                                exception.printStackTrace();
                            }
                            pedido.setUsername(destinatario.getUsername());
                            pedido.setPhotoUrl(destinatario.getImageUrl());
                            pedido.setTimestamp(message.getTime());
                            pedido.setLastMessage("você: "+message.getText());

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
                    .add(message)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {

                            Log.d("Teste", documentReference.getId());
                            message.setID(documentReference.getId());
                            FirebaseFirestore.getInstance().collection("/conversas")
                                    .document(idDestino)
                                    .collection(idRementente)
                                    .document(documentReference.getId())
                                    .set(message);


                            pedidos.setUuid(idRementente);
                            pedidos.setUsername(PaginaUsuario.usuario.getUsername());
                            pedidos.setPhotoUrl(PaginaUsuario.usuario.getImageUrl());
                            pedidos.setTimestamp(message.getTime());
                            pedidos.setLastMessage(message.getText());

                            FirebaseFirestore.getInstance().collection("/ultima-mensagem")
                                    .document(idDestino)
                                    .collection("pedidos")
                                    .document(idRementente)
                                    .set(pedidos);
                            Notification notification = new Notification();
                            notification.setFromName(destinatario.getId());
                            FirebaseFirestore.getInstance().collection("/noti")
                                    .document(idDestino)
                                    .collection(idRementente)
                                    .add(notification);
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
    public void sendLocal() {

        ChatUsuarioFragment.editmessage.setText(null);
        final String idRementente = FirebaseAuth.getInstance().getUid();
        final String idDestino = destinatario.getId();
        final long timestamp = System.currentTimeMillis();
        final Pedido pedidos = new Pedido();
        ;
        final Message message = new Message();
        message.setDestinatarioID(idDestino);
        message.setRemetenteID(idRementente);
        message.setServidor(true);
        message.setTime(timestamp);
        message.setText(FirebaseAuth.getInstance().getUid());
        if (!message.getText().isEmpty()) {
            FirebaseFirestore.getInstance().collection("/conversas")
                    .document(idRementente)
                    .collection(idDestino)
                    .add(message)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {

                            Pedido pedido = new Pedido();
                            pedido.setUuid(idDestino);
                            try {
                                pedidos.setServidor(!PedidosFragment.pedido.isServidor());
                                pedido.setServidor(PedidosFragment.pedido.isServidor());
                            } catch (Exception exception) {
                                pedido.setServidor(false);
                                pedidos.setServidor(true);
                                exception.printStackTrace();
                            }
                            pedido.setUsername(destinatario.getUsername());
                            pedido.setPhotoUrl(destinatario.getImageUrl());
                            pedido.setTimestamp(timestamp);
                            pedido.setLastMessage("você: " + "Local Enviado");

                            FirebaseFirestore.getInstance().collection("/ultima-mensagem")
                                    .document(idRementente)
                                    .collection("pedidos")
                                    .document(idDestino)
                                    .set(pedido);
                            Notification notification = new Notification();
                            notification.setFromName(destinatario.getId());
                            FirebaseFirestore.getInstance().collection("/noti")
                                    .document(idDestino)
                                    .collection(idRementente)
                                    .add(notification);
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

                            pedidos.setUuid(idRementente);
                            pedidos.setUsername(PaginaUsuario.usuario.getUsername());
                            pedidos.setPhotoUrl(PaginaUsuario.usuario.getImageUrl());
                            pedidos.setTimestamp(timestamp);
                            pedidos.setLastMessage("Local Enviado");

                            FirebaseFirestore.getInstance().collection("/ultima-mensagem")
                                    .document(idDestino)
                                    .collection("pedidos")
                                    .document(idRementente)
                                    .set(pedidos);
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