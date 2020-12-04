package com.example.projetotcc.ui.pesquisar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.projetotcc.PaginaUsuario;
import com.example.projetotcc.R;
import com.example.projetotcc.ui.chatUsuario.ChatUsuarioFragment;
import com.example.projetotcc.ui.home.HomeViewModel;
import com.example.projetotcc.ui.infoServico.InfoServicoFragment;
import com.example.projetotcc.ui.listaFragment.ListaCategoriasFragment;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;

import dominio.entidade.Servico;

import static com.example.projetotcc.PaginaUsuario.groupAdapter;
import static com.example.projetotcc.PaginaUsuario.rv;

public class HomeFragment extends Fragment {

    private HomeViewModel mViewModel;
    private RecyclerView recyclerView;
    public  static Servico servico;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.RecyclerHome);

        recyclerView.setAdapter(groupAdapter);
        try {
            ChatUsuarioFragment.registration2.remove();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(PaginaUsuario.context));

        groupAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Item item, @NonNull View view) {

                ListaCategoriasFragment.ServicoItem servicoItem = (ListaCategoriasFragment.ServicoItem) item;
                servico = new Servico();
                servico = servicoItem.servico;
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.nav_host_fragment, new InfoServicoFragment()).commit();
            }
        });
        return view;
    }

        @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
    }
}