package com.example.projetotcc.models;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.projetotcc.MainActivity;
import dominio.entidade.Usuario;
import com.example.projetotcc.config.Constants;
import com.example.projetotcc.controllers.ValidarLogin;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ValidarLoginModel extends ValidarLogin {
    public void Login(final CallBacks.VolleyCallbackUsuario callback, final String email, final String senha) {
        final Usuario usuario = new Usuario();
        final StringRequest request = new StringRequest(
                Request.Method.POST,
                Constants.LoginUsuarioUrl,

                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {

                        Log.i("Script", "SUCCESS: "+ response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            usuario.setNome(jsonObject.getString("nome_usuario"));
                            usuario.setEmail(jsonObject.getString("email_usuario"));
                            usuario.setCod(Integer.parseInt(jsonObject.getString("cod_usuario")));
                            usuario.setUsername(jsonObject.getString("userName_usuario"));
                            usuario.setSenha(jsonObject.getString("senha_usuario"));
                            usuario.setCpf(jsonObject.getString("cpf_usuario"));
                            usuario.setIdade(Integer.parseInt(jsonObject.getString("idade_usuario")));
                            Log.i("Cod", "Int: "+ usuario.getCod());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        callback.onSuccess(response, usuario);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.context, "Error: "+error.getMessage(), Toast.LENGTH_LONG).show();
                        MainActivity.loadingDialog.DismissDialog();
                    }
                }){
            protected Map getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("email", email);
                hashMap.put("senha", senha);
                return hashMap;
            }

        };

        request.setTag("tag");
        requestQueue.add(request);

    }
}
