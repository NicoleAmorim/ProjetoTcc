package com.example.projetotcc.models;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.projetotcc.cadastroServico.CadastroServico1;
import dominio.entidade.Usuario;
import com.example.projetotcc.config.Constants;

import java.util.HashMap;
import java.util.Map;

public class CadastroServicoModel extends CadastroServico1 {

    public void CadastrarServico(final CallBacks.VolleyCallback callback, final String nome, final String preco, final String descricao, final String tipo, final Usuario user, final String imagem) {
        final String cod = String.valueOf(user.getCod());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.CadastrarServicoUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Script", "SUCCESS Servico: "+response + cod);
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        }) {
            protected Map getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("nome", nome);
                hashMap.put("preco", preco);
                hashMap.put("img", imagem);
                hashMap.put("descricao", descricao);
                hashMap.put("tipo", tipo);
                hashMap.put("cod", cod);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

}
