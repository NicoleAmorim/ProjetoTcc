package com.example.projetotcc.controllers;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.example.projetotcc.ChatUsuario;

import database.DadosOpenHelperDestinatario;
import database.DadosOpenHelperMessage;
import dominio.entidade.Usuario;
import dominio.repositorio.ManterLogadoRepositorio;
import dominio.entidade.Message;

import com.example.projetotcc.MainActivity;
import com.example.projetotcc.PaginaUsuario;
import com.example.projetotcc.models.CallBacks;
import com.example.projetotcc.models.MensagemModel;
import com.example.projetotcc.models.SelecionarUsuarioModel;
import com.example.projetotcc.ui.pedidos.PedidosFragment;

public class Mensagem  extends ChatUsuario {
    private DadosOpenHelperMessage dadosOpenHelper;
    private SQLiteDatabase conexao;
    private ManterLogadoRepositorio manterLogadoRepositorio;
    private MensagemModel mensagemModel;
    private ManterLogadoRepositorio manterLogadoRepositorio1;

    public void SelecionarMensagem(final Usuario usuario, final RequestQueue requestQueue1)
    {
        final String layout = PaginaUsuario.layout;
        mensagemModel = new MensagemModel(requestQueue1);
        final SelecionarUsuarioModel selecionarUsuario = new SelecionarUsuarioModel(requestQueue1);
        criarConexaoInterna();
        mensagemModel.SelecionarMensagem(new CallBacks.VolleyCallbackMensagem() {
            @Override
            public void onSuccess(String result, Message message) {
                if(message.getText()!= "erro" && layout == "chat")
                {
                    manterLogadoRepositorio.inserirMensagem(message);
                    adapter.add(new ChatUsuario.MessageItem(message));
                    SelecionarMensagem(usuario, requestQueue1);
                    Log.i("oi", "né: "+ "chat");
                }
                if(message.getText()!= "erro" && layout == "pagina")
                {
                    manterLogadoRepositorio.inserirMensagem(message);
                    selecionarUsuario.SelecionarUserById(new CallBacks.VolleyCallbackUsuario() {
                        @Override
                        public void onSuccess(String response, Usuario usuario) {
                            try {
                                manterLogadoRepositorio1.inserirUserPedido(usuario);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, message.getDestinatarioID());
                    SelecionarMensagem(usuario, requestQueue1);
                    Log.i("oi", "né: "+ "usuario");
                }
                if(MainActivity.procurar == true)
                {

                }
                else
                {
                    try {
                        selecionarUsuario.SelecionarUserById(new CallBacks.VolleyCallbackUsuario() {
                            @Override
                            public void onSuccess(String response, Usuario usuario) {
                                try {
                                    manterLogadoRepositorio1.inserirUserPedido(usuario);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }, message.getDestinatarioID());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    SelecionarMensagem(usuario, requestQueue1);
                }
            }
        }, String.valueOf(usuario.getCod()));
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
            dadosOpenHelper = new DadosOpenHelperMessage(PaginaUsuario.context);
            DadosOpenHelperDestinatario dadosOpenHelper1 = new DadosOpenHelperDestinatario(PaginaUsuario.context);
            conexao = dadosOpenHelper.getWritableDatabase();
            SQLiteDatabase conexao1 = dadosOpenHelper1.getWritableDatabase();
            manterLogadoRepositorio1 = new ManterLogadoRepositorio(conexao1);
            manterLogadoRepositorio = new ManterLogadoRepositorio(conexao);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

