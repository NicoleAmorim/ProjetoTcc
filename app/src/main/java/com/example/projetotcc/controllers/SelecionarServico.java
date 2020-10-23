package com.example.projetotcc.controllers;

import com.example.projetotcc.PaginaUsuario;
import dominio.entidade.Servico;
import com.example.projetotcc.models.CallBacks;
import com.example.projetotcc.models.SelecionarServicoModel;
import com.example.projetotcc.ui.listaFragment.ListaCategoriasFragment;

public class SelecionarServico extends PaginaUsuario {

    public void SelecionarServicoByTipo(final int ultimo, int primeiro, String type) {
    selecionarServicoModel = new SelecionarServicoModel(requestQueue);
    selecionarServicoModel.SelecionarServicoByTipo(new CallBacks.VolleyCallbackProduto() {
            @Override
            public void onSuccess(String result, Servico p) {
                servico = p;
                ListaCategoriasFragment.adapter.add(new ListaCategoriasFragment.ServicoItem(servico, ultimo));
                ListaCategoriasFragment.adapter.notifyDataSetChanged();
            }
        }, String.valueOf(primeiro), type);
    }

    public void IdUltimoServico()
    {
        selecionarServicoModel = new SelecionarServicoModel(requestQueue);
        selecionarServicoModel.idUltimoServico(new CallBacks.VolleyCallback(){
            @Override
            public void onSuccess(final String ultimo) {
                try {
                    SelecionarServicoByTipo(Integer.parseInt(ultimo), 1, PaginaUsuario.tipo);
                }
                catch (NumberFormatException numberFormatException) {
                    numberFormatException.printStackTrace();
                }
            }
        }, PaginaUsuario.tipo);
    }

    public void SelecionarServicoById(String cod) {
        selecionarServicoModel = new SelecionarServicoModel(requestQueue);
        selecionarServicoModel.findServicoById(new CallBacks.VolleyCallbackProduto() {
            @Override
            public void onSuccess(String response, Servico p) {
                servicop = p;
            }
        }, cod);
    }
}
