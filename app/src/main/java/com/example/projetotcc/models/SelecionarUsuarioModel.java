package com.example.projetotcc.models;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.projetotcc.MainActivity;
import dominio.entidade.Usuario;
import com.example.projetotcc.config.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SelecionarUsuarioModel {
    private RequestQueue requestQueue;
    public SelecionarUsuarioModel(RequestQueue requestQueue)
    {
        this.requestQueue = requestQueue;
    }

    public void SelecionarUserById(final CallBacks.VolleyCallbackUsuario callback, final String id) {
        final Usuario usuario = new Usuario();
        final StringRequest request = new StringRequest(
                Request.Method.POST,
                Constants.SelecionarUserByIdUrl,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        Log.i("Aqui", "Meu Serviço: "+response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            usuario.setNome(jsonObject.getString("nome_usuario"));
                            usuario.setEmail(jsonObject.getString("email_usuario"));
                            usuario.setCod(Integer.parseInt(jsonObject.getString("cod_usuario")));
                            usuario.setUsername(jsonObject.getString("userName_usuario"));
                            usuario.setSenha(jsonObject.getString("senha_usuario"));
                            usuario.setCpf(jsonObject.getString("cpf_usuario"));
                            usuario.setIdade(Integer.parseInt(jsonObject.getString("idade_usuario")));
                            callback.onSuccess(response, usuario);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.context, "Error: "+error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            protected Map getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("cod", id);
                return hashMap;
            }
        };
        requestQueue.add(request);
    }
}
