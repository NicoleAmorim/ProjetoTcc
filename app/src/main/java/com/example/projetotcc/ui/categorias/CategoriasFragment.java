package com.example.projetotcc.ui.categorias;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.projetotcc.PaginaUsuario;
import com.example.projetotcc.R;
import com.example.projetotcc.ui.chatUsuario.ChatUsuarioFragment;
import com.example.projetotcc.ui.filtrar.FiltrarFragment;
import com.example.projetotcc.ui.listaFragment.ListaCategoriasFragment;

import dominio.entidade.Servico;

public class CategoriasFragment extends Fragment {
    private CategoriasViewModel mViewModel;
    public static String tipo;
    private CardView eletricista, encanador, pedreiro, pintor, carpinteiro, entregador, faxineira, baba, wash, ar;

    public static CategoriasFragment newInstance() {
        return new CategoriasFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categorias, container, false);
        FiltrarFragment.UF = "";
        eletricista = view.findViewById(R.id.CEletricista);
        encanador = view.findViewById(R.id.CEncanador);
        pedreiro = view.findViewById(R.id.CPedreiro);
        pintor = view.findViewById(R.id.CPintor);
        carpinteiro = view.findViewById(R.id.CCarpinteiro);
        entregador = view.findViewById(R.id.CEntregador);
        faxineira = view.findViewById(R.id.CFaxineira);
        baba = view.findViewById(R.id.CBaba);
        wash = view.findViewById(R.id.CWashCar);
        ar = view.findViewById(R.id.CAr);

        eletricista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findbyCategoriaEletricista();
            }
        });
        encanador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findbyCategoriaEncanador();
            }
        });
        pedreiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findbyCategoriaPedreiro();
            }
        });
        pintor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findbyCategoriaPintor();
            }
        });
        carpinteiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findbyCategoriaCarpinteiro();
            }
        });
        entregador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findbyCategoriaEntregador();
            }
        });
        faxineira.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findbyCategoriaFaxina();
            }
        });
        baba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findbyCategoriaBaba();
            }
        });
        wash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findbyCategoriaWashCar();
            }
        });
        ar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findbyCategoriaAr();
            }
        });
        try{
        ChatUsuarioFragment.registration2.remove();} catch (Exception exception) {
            exception.printStackTrace();
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CategoriasViewModel.class);
    }
    //CATEGORIAS
    public void findbyCategoriaAr() {
        tipo = "Ar-condicinado";
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, new ListaCategoriasFragment()).commit(); }

    public void findbyCategoriaBaba() {
        tipo = "Bab\u00e1";
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, new ListaCategoriasFragment()).commit();    }

    public void findbyCategoriaCarpinteiro() {
        tipo = "Carpinteiro";
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, new ListaCategoriasFragment()).commit();    }

    public void findbyCategoriaEletricista() {
        tipo = "Eletricista";
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, new ListaCategoriasFragment()).commit();    }

    public void findbyCategoriaEncanador() {
        tipo = "Encanador";
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, new ListaCategoriasFragment()).commit();    }

    public void findbyCategoriaEntregador() {
        tipo = "Entregador";
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, new ListaCategoriasFragment()).commit();    }

    public void findbyCategoriaFaxina() {
        tipo = "Faxineiro";
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, new ListaCategoriasFragment()).commit();    }

    public void findbyCategoriaPedreiro() {
        tipo = "Pedreiro";
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, new ListaCategoriasFragment()).commit();    }

    public void findbyCategoriaPintor() {
        tipo = "Pintor";
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, new ListaCategoriasFragment()).commit();    }

    public void findbyCategoriaWashCar() {
        tipo = "WashCar";
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, new ListaCategoriasFragment()).commit();    }
}