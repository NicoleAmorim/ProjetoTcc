package com.example.projetotcc.ui.listaFragment;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projetotcc.config.Constants;
import com.example.projetotcc.controllers.SelecionarServico;
import com.example.projetotcc.models.CallBacks;
import com.example.projetotcc.InfoServico;
import com.example.projetotcc.PaginaUsuario;
import com.example.projetotcc.R;
import dominio.entidade.Servico;
import dominio.entidade.Usuario;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;
import com.xwray.groupie.ViewHolder;

import java.util.List;

public class ListaCategoriasFragment extends Fragment {

    public static GroupAdapter adapter;
    public static RecyclerView rv;
    public static Servico servico;
    private static CallBacks callBacks;
    private static SelecionarServico selecionarServico;
    int i;
    private MainViewModel mViewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        callBacks = new CallBacks();
        selecionarServico = new SelecionarServico();
        adapter = new GroupAdapter();

        View view;
        view = inflater.inflate(R.layout.fragment_lista, container, false);
        rv = view.findViewById(R.id.recyclerListaPrestador);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(PaginaUsuario.context));

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Item item, @NonNull View view) {
                Intent intent = new Intent(PaginaUsuario.context, InfoServico.class);

                ServicoItem servicoItem = (ServicoItem) item;
                servico = new Servico();
                servico = servicoItem.servico;
                intent.putExtra("servico", servico);

                startActivity(intent);
            }
        });
        FindServico();
    }

    private void FindServico() {
        FirebaseFirestore.getInstance().collection("servicoGlobal")
                .whereEqualTo("tipo", PaginaUsuario.tipo)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.e("Teste", e.getMessage(), e);
                            return;
                        }
                        List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();
                        adapter.clear();
                        for (DocumentSnapshot doc: docs) {
                            Servico servico = doc.toObject(Servico.class);
                            String uid = FirebaseAuth.getInstance().getUid();
                            if (servico.getIDUser().equals(uid))
                                continue;
                            adapter.add(new ServicoItem(servico));
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }


    public static class ServicoItem extends Item<ViewHolder> {
        private final Servico servico;

        public ServicoItem(Servico servico) {
            this.servico = servico;
        }

        @Override
        public void bind(@NonNull ViewHolder viewHolder, int position) {
            TextView var3 = (TextView) viewHolder.itemView.findViewById(R.id.descricaoServico);
            TextView var4 = (TextView) viewHolder.itemView.findViewById(R.id.precoServico);
            ImageView imageView = (ImageView) viewHolder.itemView.findViewById(R.id.imageUseNav);

            var3.setText(servico.getNome());
            Picasso.get().load(servico.getImagemUrl()).into(imageView);
        }

        @Override
        public int getLayout() {
            return R.layout.item_servico;
        }
    }
}