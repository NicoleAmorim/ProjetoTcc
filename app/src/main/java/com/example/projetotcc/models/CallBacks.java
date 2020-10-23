package com.example.projetotcc.models;

import dominio.entidade.Message;

import dominio.entidade.Servico;
import dominio.entidade.Usuario;

public class CallBacks {

   public interface VolleyCallback {
      void onSuccess(String response);
   }
   public interface VolleyCallbackMensagem {
      void onSuccess(String response, Message message);
   }
   public interface VolleyCallbackProduto {
      void onSuccess(String response, Servico servico);
   }
   public interface VolleyCallbackUsuario {
      void onSuccess(String response, Usuario usuario);
   }
}
