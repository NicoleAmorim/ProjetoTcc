package com.example.projetotcc.controllers;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.projetotcc.ChatUsuario;
import database.DadosOpenHelperMessage;
import dominio.repositorio.ManterLogadoRepositorio;
import dominio.entidade.Message;
import com.example.projetotcc.models.CallBacks;
import com.example.projetotcc.models.MensagemModel;

public class Mensagem  extends ChatUsuario{
    private DadosOpenHelperMessage dadosOpenHelper;
    private SQLiteDatabase conexao;
    private ManterLogadoRepositorio manterLogadoRepositorio;
    private MensagemModel mensagemModel;

    public void SelecionarMensagem()
    {
        mensagemModel = new MensagemModel(requestQueue);
        criarConexaoInterna();
        mensagemModel.SelecionarMensagem(new CallBacks.VolleyCallbackMensagem() {
            @Override
            public void onSuccess(String result, Message message) {
                if(message.getText()!= "erro")
                {
                    manterLogadoRepositorio.inserirMensagem(message);
                    adapter.add(new ChatUsuario.MessageItem(message));
                }
                SelecionarMensagem();
            }
        }, String.valueOf(remetente.getCod()));
    }

    public void EnviarMensagem(Message message)
    {
        mensagemModel = new MensagemModel(requestQueue);
        criarConexaoInterna();
        if(!message.getText().isEmpty())
        {
            mensagemModel.EnviarMensagem(message);
            Log.i("Script", "SUCCESS: "+ message.getText());
            manterLogadoRepositorio.inserirMensagem(message);
            adapter.add(new MessageItem(message));
        }
    }
    private void criarConexaoInterna() {
        try {
            dadosOpenHelper = new DadosOpenHelperMessage(ChatUsuario.context);
            conexao = dadosOpenHelper.getWritableDatabase();
            manterLogadoRepositorio = new ManterLogadoRepositorio(conexao);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
