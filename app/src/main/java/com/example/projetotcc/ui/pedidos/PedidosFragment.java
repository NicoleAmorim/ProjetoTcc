package com.example.projetotcc.ui.pedidos;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetotcc.ChatUsuario;
import com.example.projetotcc.InfoServico;
import com.example.projetotcc.PaginaUsuario;
import com.example.projetotcc.R;
import com.example.projetotcc.ui.listaFragment.ListaCategoriasFragment;
import com.example.projetotcc.ui.perfil.PerfilFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;
import com.xwray.groupie.ViewHolder;

import java.util.List;

import database.DadosOpenHelperDestinatario;
import database.DadosOpenHelperMessage;
import dominio.entidade.Message;
import dominio.entidade.Pedido;
import dominio.entidade.Servico;
import dominio.entidade.Usuario;
import dominio.repositorio.ManterLogadoRepositorio;

public class PedidosFragment extends Fragment {

    private PedidosViewModel mViewModel;
    public static GroupAdapter adapter;
    private ManterLogadoRepositorio manterLogadoRepositorio;
    private DadosOpenHelperDestinatario dadosOpenHelper;
    private SQLiteDatabase conexao;
    private RecyclerView recyclerView;
    private Pedido pedido;
    public static Usuario usuario;
    private Servico servico;
    private Intent intent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_pedidos, container, false);

        adapter = new GroupAdapter();

        recyclerView = (RecyclerView) view.findViewById(R.id.RecyclerPedidos);
        recyclerView.setLayoutManager(new LinearLayoutManager(PaginaUsuario.context));
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        InfoServico.validar = false;
        ProcurarMensagem();
        mViewModel = ViewModelProviders.of(this).get(PedidosViewModel.class);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Item item, @NonNull View view) {
                PedidosFragment.PedidoItem pedidoItem = (PedidosFragment.PedidoItem) item;
                pedido = new Pedido();
                pedido = pedidoItem.pedido;
                FirebaseFirestore.getInstance().collection("/servico")
                        .document(pedido.getUuid())
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                servico = new Servico();
                                servico = documentSnapshot.toObject(Servico.class);
                                intent = new Intent(PaginaUsuario.context, InfoServico.class);
                                Log.i("teste", servico.getIDUser());
                                intent.putExtra("servico", servico);

                                startActivity(intent);
                            }
                        });
            }
        });
        // TODO: Use the ViewModel
    }

    private void criarConexaoInterna() {
        try {
            dadosOpenHelper = new DadosOpenHelperDestinatario(PaginaUsuario.context);
            conexao = dadosOpenHelper.getWritableDatabase();
            manterLogadoRepositorio = new ManterLogadoRepositorio(conexao); } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void ProcurarMensagem() {

        FirebaseFirestore.getInstance().collection("/ultima-mensagem")
                .document(FirebaseAuth.getInstance().getUid())
                .collection("pedidos")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        List<DocumentChange> documentChanges = queryDocumentSnapshots.getDocumentChanges();

                        if (documentChanges != null) {
                            for (DocumentChange doc: documentChanges) {
                                if (doc.getType() == DocumentChange.Type.ADDED) {
                                    Pedido pedido = doc.getDocument().toObject(Pedido.class);
                                    Log.e("Teste", pedido.getLastMessage());
                                    adapter.add(new PedidoItem(pedido));
                                }
                            }
                        }
                    }
                });
    }
    private class PedidoItem extends Item<ViewHolder> {

        private final Pedido pedido;

        private PedidoItem(Pedido pedido) {
            this.pedido = pedido;
        }

        @Override
        public void bind(@NonNull ViewHolder viewHolder, int position) {
            TextView username = viewHolder.itemView.findViewById(R.id.Nomeusuariopedido);
            TextView message = viewHolder.itemView.findViewById(R.id.Ultimotextopedido);
            ImageView imgPhoto = viewHolder.itemView.findViewById(R.id.imageUsuarioPedido);

            username.setText(pedido.getUsername());
            message.setText(pedido.getLastMessage());
            Picasso.get()
                    .load(pedido.getPhotoUrl())
                    .into(imgPhoto);
        }

        @Override
        public int getLayout() {
            return R.layout.item_pedidos;
        }
    }
}