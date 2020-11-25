package com.example.projetotcc.ui.pedidos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetotcc.LoadingDialog;
import com.example.projetotcc.Notification;
import com.example.projetotcc.PaginaUsuario;
import com.example.projetotcc.R;
import com.example.projetotcc.RStar;
import com.example.projetotcc.ui.chatUsuario.ChatUsuarioFragment;
import com.example.projetotcc.ui.home.HomeFragment;
import com.example.projetotcc.ui.infoServico.InfoServicoFragment;
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
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;
import com.xwray.groupie.OnItemLongClickListener;
import com.xwray.groupie.ViewHolder;

import java.util.List;

import database.DadosOpenHelperDestinatario;
import dominio.entidade.Message;
import dominio.entidade.Pedido;
import dominio.entidade.Usuario;
import dominio.repositorio.ManterLogadoRepositorio;

public class PedidosFragment extends Fragment {

    private PedidosViewModel mViewModel;
    public static GroupAdapter adapter;
    private ManterLogadoRepositorio manterLogadoRepositorio;
    private DadosOpenHelperDestinatario dadosOpenHelper;
    private SQLiteDatabase conexao;
    private Drawable drawablegreen, drawablered;
    private RecyclerView recyclerView;
    public static Pedido pedido;
    public static Usuario usuario;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_pedidos, container, false);
        drawablered = getResources().getDrawable(R.color.red);
        drawablegreen = getResources().getDrawable(R.color.green);
        adapter = new GroupAdapter();


        recyclerView = (RecyclerView) view.findViewById(R.id.RecyclerPedidos);
        recyclerView.setLayoutManager(new LinearLayoutManager(PaginaUsuario.context));
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        InfoServicoFragment.validar = false;
        ProcurarMensagem();
        mViewModel = ViewModelProviders.of(this).get(PedidosViewModel.class);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Item item, @NonNull View view) {
                PedidosFragment.PedidoItem pedidoItem = (PedidosFragment.PedidoItem) item;
                pedido = new Pedido();
                pedido = pedidoItem.pedido;
                FirebaseFirestore.getInstance().collection("/users")
                        .document(pedido.getUuid())
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                usuario = new Usuario();
                                usuario = documentSnapshot.toObject(Usuario.class);
                                Log.i("teste", usuario.getId());
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                                fragmentTransaction.replace(R.id.nav_host_fragment, new ChatUsuarioFragment()).commit();
                            }
                        });
            }
        });
        adapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(@NonNull Item item, @NonNull View view) {
                PedidosFragment.PedidoItem pedidoItem = (PedidosFragment.PedidoItem) item;
                pedido = new Pedido();
                pedido = pedidoItem.pedido;
                    new AlertDialog.Builder(PaginaUsuario.context)
                            .setTitle("Finalizar Servico")
                            .setMessage("Tem certeza que deseja finalizar o serviço?")
                            .setPositiveButton("sim", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    PaginaUsuario.rStar.StartRat();
                                }
                            }).setNegativeButton("não", null).show();
                return false;
            }
        });
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
                                    final Pedido pedido = doc.getDocument().toObject(Pedido.class);
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
            final ImageView online = viewHolder.itemView.findViewById(R.id.onlinePedido);
            ImageView imgPhoto = viewHolder.itemView.findViewById(R.id.imageUsuarioPedido);

            username.setText(pedido.getUsername());
            message.setText(pedido.getLastMessage());
            Picasso.get()
                    .load(pedido.getPhotoUrl())
                    .into(imgPhoto);
            FirebaseFirestore.getInstance().collection("/users")
                                            .document(pedido.getUuid())
                                            .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                                @Override
                                                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                                    usuario = new Usuario();
                                                    usuario = value.toObject(Usuario.class);
                                                    if (usuario.isOnline()) {
                                                        Log.e("Teste", usuario.getNome());

                                                        online.setImageDrawable(drawablegreen);
                                                    } else {
                                                        Log.e("Teste", usuario.getNome());

                                                        online.setImageDrawable(drawablered);
                                                    }
                                                }
                                            });
        }

        @Override
        public int getLayout() {
            return R.layout.item_pedidos;
        }
    }
}