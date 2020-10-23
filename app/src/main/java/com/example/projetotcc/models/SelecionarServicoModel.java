package com.example.projetotcc.models;

import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.projetotcc.MainActivity;
import com.example.projetotcc.PaginaUsuario;
import dominio.entidade.Servico;
import com.example.projetotcc.config.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

public class SelecionarServicoModel {

    private RequestQueue requestQueue;

    public SelecionarServicoModel(RequestQueue requestQueue)
    {
        this.requestQueue = requestQueue;
    }

    public void idUltimoServico(final CallBacks.VolleyCallback callback, final String tipo) {
        final StringRequest request = new StringRequest(
                Request.Method.POST,
                Constants.SelecionarUltimoServicoUrl,

                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {

                        Log.i("Script", "SUCCESS: "+response);
                        callback.onSuccess(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.context, "Error: "+error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            protected Map getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put((Object) "tipo", tipo);
                return hashMap;
            }
        };
        requestQueue.add(request);
    }
    public void SelecionarServicoByTipo(final CallBacks.VolleyCallbackProduto callback, final String id, final String tipo) {
        final Servico servico = new Servico();
        final StringRequest request = new StringRequest(
                Request.Method.POST,
                Constants.SelecionarServicoUrl,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {

                        Log.i("Script", "SUCCESS: "+response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            servico.setNome(jsonObject.getString("nome_servico"));
                            servico.setTipo(jsonObject.getString("tipo_servico"));
                            servico.setPreco(jsonObject.getString("preco_servico"));
                            servico.setDescricao(jsonObject.getString("descricao_servico"));
                            servico.setID(jsonObject.getInt("cod_servico"));
                            servico.setIDUser(jsonObject.getInt("cod_usuario"));
                            String imageDataBytes = jsonObject.getString("img").substring(jsonObject.getString("img").indexOf(",")+1);
                            ByteArrayInputStream stream = new ByteArrayInputStream(Base64.decode(imageDataBytes.getBytes(), Base64.DEFAULT));
                            servico.setImagem(BitmapFactory.decodeStream(stream));
                            callback.onSuccess(response, servico);
                            Log.v("LogLogin", servico.getNome());
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
                hashMap.put("tipo", tipo);
                return hashMap;
            }
        };
        requestQueue.add(request);
    }
    public void findServicoById(final CallBacks.VolleyCallbackProduto callback, final String id) {
        requestQueue = PaginaUsuario.requestQueue;
        final Servico servico = new Servico();
        final StringRequest request = new StringRequest(
                Request.Method.POST,
                Constants.SelecionarUsuarioServicoUrl,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {

                        Log.i("Aqui", "Meu Serviço: "+response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            servico.setNome(jsonObject.getString("nome_servico"));
                            servico.setDescricao(jsonObject.getString("descricao_servico"));
                            servico.setPreco(jsonObject.getString("preco_servico"));
                            servico.setTipo(jsonObject.getString("tipo_servico"));
                            servico.setID(jsonObject.getInt("cod_servico"));
                            callback.onSuccess(response, servico);
                            Log.v("LogLogin", servico.getNome());
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
