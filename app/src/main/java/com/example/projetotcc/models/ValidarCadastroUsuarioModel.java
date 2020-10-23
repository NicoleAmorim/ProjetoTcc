package com.example.projetotcc.models;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import dominio.entidade.Usuario;
import com.example.projetotcc.config.Constants;
import com.example.projetotcc.controllers.ValidarCadastroUsuario;

import java.util.HashMap;
import java.util.Map;

public class ValidarCadastroUsuarioModel extends ValidarCadastroUsuario {

    public void CadastrarUser(final CallBacks.VolleyCallbackUsuario callback, final Usuario usuario) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.CadastrarUsuarioUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Script", "SUCCESS: "+response);
                callback.onSuccess(response, usuario);
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        }) {
            protected Map getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("nome", usuario.getNome());
                hashMap.put("email", usuario.getEmail());
                hashMap.put("username", usuario.getUsername());
                hashMap.put("cpf", usuario.getCpf());
                hashMap.put("img", usuario.getImagem());
                hashMap.put("senha", usuario.getSenha());
                hashMap.put("tel", String.valueOf(usuario.getTel()));
                hashMap.put("idade", String.valueOf(usuario.getIdade()));
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}
