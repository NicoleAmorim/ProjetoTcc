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
import com.example.projetotcc.ui.pedidos.PedidosFragment;
import com.google.firebase.firestore.DocumentChange;
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

import database.DadosOpenHelper;
import dominio.entidade.Message;
import dominio.entidade.Usuario;
import dominio.repositorio.ManterLogadoRepositorio;

public class ChatUsuarioFragment extends Fragment {

    private ChatUsuarioViewModel mViewModel;
    public static GroupAdapter adapter;
    public static Usuario remetente;

    public static Usuario destinatario;
    public static EditText editmessage;
    public static Context context;
    private RecyclerView rv;

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
        if(InfoServicoFragment.validar == true) {
            destinatario = InfoServicoFragment.user;
        } else {
            destinatario = PedidosFragment.usuario;
        }
        PaginaUsuario.toolbar.setTitle(destinatario.getNome());
        adapter = new GroupAdapter();
        rv.setLayoutManager(new LinearLayoutManager(PaginaUsuario.context));
        rv.getRecycledViewPool().clear();
        adapter.clear();
        Procurar();
        rv.setAdapter(adapter);
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
        adapter.clear();
        FirebaseFirestore.getInstance().collection("/conversas")
                .document(fromId)
                .collection(toId)
                .document("mensagem")
                .collection("m")
                .orderBy("time", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        List<DocumentChange> documentChanges = queryDocumentSnapshots.getDocumentChanges();

                            for (DocumentChange doc: documentChanges) {
                                    if (doc.getType() == DocumentChange.Type.ADDED) {
                                        Message message = doc.getDocument().toObject(Message.class);
                                        adapter.add(new ChatUsuarioFragment.MessageItem(message));
                                        rv.smoothScrollToPosition(adapter.getItemCount());
                                        Log.i("teste", String.valueOf(-adapter.getItemCount()));

                                    }
                            }
                        }
                });
    }
}