package com.example.projetotcc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import database.DadosOpenHelperMessage;
import dominio.entidade.Message;
import dominio.entidade.Pedido;
import dominio.repositorio.ManterLogadoRepositorio;
import com.example.projetotcc.controllers.Mensagem;
import com.example.projetotcc.models.CallBacks;
import com.example.projetotcc.ui.pedidos.PedidosFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

import java.util.List;

import javax.annotation.Nullable;

import dominio.entidade.Usuario;

public class ChatUsuario extends AppCompatActivity {

    public static GroupAdapter adapter;
    private CallBacks callBacks;
    protected static Usuario remetente;

    protected static Usuario destinatario;
    private EditText editmessage;
    public static Context context;
    private DadosOpenHelperMessage dadosOpenHelper;
    private SQLiteDatabase conexao;
    private Mensagem mensagem;
    private ManterLogadoRepositorio manterLogadoRepositorio;
    public static RequestQueue requestQueue;
    private RecyclerView rv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestQueue = Volley.newRequestQueue(this.getApplicationContext());
        setContentView(R.layout.activity_chat_usuario);

        remetente = PaginaUsuario.usuario;
        if(InfoServico.validar == true) {
            destinatario = InfoServico.user;
        } else {
            destinatario = PedidosFragment.usuario;
        }
        editmessage = findViewById(R.id.editMsgm);
        rv = findViewById(R.id.recyclerChatUser);
        callBacks = new CallBacks();
        mensagem = new Mensagem();
        adapter = new GroupAdapter();
        criarConexaoInterna();

        Procurar();
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

    }

    public void sendMensage(View view)
    {
        String txt = editmessage.getText().toString();

        editmessage.setText(null);

        String remetenteId = String.valueOf(remetente.getCod());
        String destinatarioId = String.valueOf(destinatario.getCod());
        long Time = System.currentTimeMillis();

        Message message = new Message();
        message.setDestinatarioID(destinatarioId);
        message.setRemetenteID(remetenteId);
        message.setTime(Time);
        message.setText(txt);

        mensagem.EnviarMensagem(message);

    }


    public static class MessageItem extends Item<ViewHolder>{

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
    private void criarConexaoInterna() {
        try {
            dadosOpenHelper = new DadosOpenHelperMessage(this);
            conexao = dadosOpenHelper.getWritableDatabase();
            manterLogadoRepositorio = new ManterLogadoRepositorio(conexao); } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void Procurar() {

            String fromId = remetente.getId();
            String toId = destinatario.getId();

            FirebaseFirestore.getInstance().collection("/conversas")
                    .document(fromId)
                    .collection(toId)
                    .orderBy("time", Query.Direction.ASCENDING)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                            List<DocumentChange> documentChanges = queryDocumentSnapshots.getDocumentChanges();

                            if (documentChanges != null) {
                                for (DocumentChange doc: documentChanges) {
                                    if (doc.getType() == DocumentChange.Type.ADDED) {
                                        Message message = doc.getDocument().toObject(Message.class);
                                        adapter.add(new MessageItem(message));
                                        rv.smoothScrollToPosition(adapter.getItemCount());
                                        Log.i("teste", String.valueOf(-adapter.getItemCount()));
                                    }
                                }
                            }
                        }
                    });
    }
    public void sendMensagem(View view) {
        String txt = editmessage.getText().toString();

        editmessage.setText(null);

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
                            pedido.setUuid(idDestino);
                            pedido.setUsername(remetente.getUsername());
                            pedido.setPhotoUrl(remetente.getImageUrl());
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