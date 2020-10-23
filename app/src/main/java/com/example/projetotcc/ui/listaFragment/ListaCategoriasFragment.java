package com.example.projetotcc.ui.listaFragment;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projetotcc.controllers.SelecionarServico;
import com.example.projetotcc.models.CallBacks;
import com.example.projetotcc.InfoServico;
import com.example.projetotcc.PaginaUsuario;
import com.example.projetotcc.R;
import dominio.entidade.Servico;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;
import com.xwray.groupie.ViewHolder;

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
        selecionarServico.IdUltimoServico();

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

                startActivity(intent);
            }
        });
    }

    public static class ServicoItem extends Item<ViewHolder> {
        private final Servico servico;
        private final int ultimo;

        public ServicoItem(Servico servico, int ultimo) {
            this.servico = servico;
            this.ultimo = ultimo;

            if(servico.getID() != ultimo)
            {
                selecionarServico.SelecionarServicoByTipo(ultimo,servico.getID()+1, servico.getTipo());
            }
        }

        @Override
        public void bind(@NonNull ViewHolder viewHolder, int position) {
            TextView var3 = (TextView) viewHolder.itemView.findViewById(R.id.descricaoServico);
            TextView var4 = (TextView) viewHolder.itemView.findViewById(R.id.precoServico);
            ImageView imageView = (ImageView) viewHolder.itemView.findViewById(R.id.imageUseNav);

            var3.setText(servico.getNome());
            var4.setText(servico.getPreco() + " " + servico.getID());
            imageView.setImageBitmap(servico.getImagem());
        }

        @Override
        public int getLayout() {
            return R.layout.item_servico;
        }
    }
}