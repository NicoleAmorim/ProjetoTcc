package com.example.projetotcc.ui.favoritos;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.projetotcc.PaginaUsuario;
import com.example.projetotcc.R;
import com.example.projetotcc.ui.editarPerfil.EditarPerfilFragment;
import com.example.projetotcc.ui.home.HomeFragment;
import com.example.projetotcc.ui.infoServico.InfoServicoFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
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

import dominio.entidade.Favoritos;
import dominio.entidade.Servico;
import dominio.entidade.Usuario;

public class FavoritosFragment extends Fragment {

    private FavoritosViewModel mViewModel;
    private GroupAdapter adapter;
    private RecyclerView rv;
    public static Servico servico;

    public static FavoritosFragment newInstance() {
        return new FavoritosFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        adapter = new GroupAdapter();

        View view;
        view = inflater.inflate(R.layout.fragment_favoritos, container, false);
        rv = view.findViewById(R.id.favoritosRecycler);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FavoritosViewModel.class);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(PaginaUsuario.context));

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Item item, @NonNull View view) {

                FavoritosFragment.ServicoItem servicoItem = (FavoritosFragment.ServicoItem) item;
                servico = new Servico();
                servico = servicoItem.servico;
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.nav_host_fragment, new InfoServicoFragment()).commit();
            }
        });
        FindServico();
    }
    private void FindServico() {
        FirebaseFirestore.getInstance().collection("favoritos")
                .document(FirebaseAuth.getInstance().getUid())
                .collection("servico")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            if (e != null) {
                                Log.e("Teste", e.getMessage(), e);
                                return;
                            }
                            List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();
                            adapter.clear();
                            for (DocumentSnapshot doc : docs) {
                                final Favoritos favoritos = doc.toObject(Favoritos.class);
                                String uid = FirebaseAuth.getInstance().getUid();
                                FirebaseFirestore.getInstance().collection("/servico")
                                        .document(favoritos.getIdServico())
                                        .get()
                                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(final DocumentSnapshot documentSnapshotServico) {
                                                Servico servicoi = new Servico();
                                                servicoi = documentSnapshotServico.toObject(Servico.class);
                                                final Servico finalServicoi = servicoi;
                                                FirebaseFirestore.getInstance().collection("/users")
                                                        .document(servicoi.getIDUser())
                                                        .get()
                                                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                            @Override
                                                            public void onSuccess(DocumentSnapshot documentSnapshotUser) {
                                                                Usuario usuario = documentSnapshotUser.toObject(Usuario.class);

                                                                adapter.add(new FavoritosFragment.ServicoItem(finalServicoi, usuario, favoritos));
                                                                adapter.notifyDataSetChanged();
                                                            }
                                                        });
                                            }
                                        });
                            }
                        }
                        else
                        {
                            new AlertDialog.Builder(PaginaUsuario.getContext)
                                    .setTitle("Favoritos Vazio")
                                    .setMessage("Nenhum serviço marcado como favorito ")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, new HomeFragment()).commit();

                                        } }).setIcon(R.drawable.ic_arquivo_favorito_50) .show();
                        }
                    }

                });
    }


    public static class ServicoItem extends Item<ViewHolder> {
        private final Servico servico;
        private final Usuario usuario;
        private final Favoritos favoritos;

        public ServicoItem(Servico servico, Usuario usuario, Favoritos favoritos) {
            this.servico = servico;
            this.usuario = usuario;
            this.favoritos = favoritos;
        }

        @Override
        public void bind(@NonNull ViewHolder viewHolder, int position) {
            TextView var3 = (TextView) viewHolder.itemView.findViewById(R.id.tituloServico);
            TextView var4 = (TextView) viewHolder.itemView.findViewById(R.id.nomeServico);
            TextView var5 = (TextView) viewHolder.itemView.findViewById(R.id.tipoServico);
            ImageView imageView = (ImageView) viewHolder.itemView.findViewById(R.id.imageUseNav);
            final Switch mySwitch = viewHolder.itemView.findViewById(R.id.btnFavItem);

            var3.setText(servico.getNome());
            var4.setText(usuario.getNome());
            var5.setText(servico.getTipo());
            Picasso.get().load(servico.getImagemUrl()).into(imageView);
            try {
                if (favoritos.isTRue()) {
                    mySwitch.setChecked(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(mySwitch.isChecked() == true)
                    {
                        Favoritos favoritos = new Favoritos();
                        favoritos.setTRue(true);
                        favoritos.setIdServico(servico.getIDUser());
                        try {
                            FirebaseFirestore.getInstance().collection("favoritos")
                                    .document(FirebaseAuth.getInstance().getUid())
                                    .collection("servico")
                                    .document(servico.getIDUser())
                                    .set(favoritos)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.i("Teste", "foi");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.i("Teste", e.getMessage());
                                        }
                                    });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        try {
                            FirebaseFirestore.getInstance().collection("favoritos")
                                    .document(FirebaseAuth.getInstance().getUid())
                                    .collection("servico")
                                    .document(servico.getIDUser())
                                    .delete()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d("Deletado", "DocumentSnapshot successfully deleted!");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w("FailDeletado", "Error deleting document", e);
                                        }
                                    });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

        @Override
        public int getLayout() {
            return R.layout.item_servico;
        }
    }
}