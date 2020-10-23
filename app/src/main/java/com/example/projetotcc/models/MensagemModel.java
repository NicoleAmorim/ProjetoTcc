package com.example.projetotcc.models;

import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.projetotcc.ChatUsuario;
import com.example.projetotcc.MainActivity;
import dominio.entidade.Message;
import com.example.projetotcc.config.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MensagemModel {
    private RequestQueue requestQueue;

    public MensagemModel(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
    }

    public void SelecionarMensagem(final CallBacks.VolleyCallbackMensagem callback, final String meuid) {
        final Message message = new Message();
        final StringRequest request = new StringRequest(
                Request.Method.POST,
                Constants.SelecionarMensagemUrl,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            message.setTime(jsonObject.getLong("hora"));
                            message.setText(jsonObject.getString("texto"));
                            message.setRemetenteID(jsonObject.getString("remetenteID"));
                            message.setDestinatarioID(jsonObject.getString("destinatarioID"));
                            message.setID(jsonObject.getString("id_mensage"));

                        } catch (JSONException e) {
                            message.setText("erro");
                            e.printStackTrace();
                        }
                        callback.onSuccess(response, message);
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
                hashMap.put("destinatario", meuid);
                return hashMap;
            }

        };
        requestQueue.add(request);
    }
    public void EnviarMensagem(final Message message) {
        requestQueue = ChatUsuario.requestQueue;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.EnviarMensagemUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        }) {
            protected Map getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("txt", message.getText());
                hashMap.put("hora", String.valueOf(message.getTime()));
                hashMap.put("remetente", message.getRemetenteID());
                hashMap.put("destinatario", message.getDestinatarioID());
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}
