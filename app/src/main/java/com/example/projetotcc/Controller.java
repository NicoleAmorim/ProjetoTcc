package com.example.projetotcc;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.projetotcc.CadastroServico.CadastroServico;
import com.example.projetotcc.CadastroUsuario.Cadastro5;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Controller extends AppCompatActivity {
   private static RequestQueue requestQueue;
   public static int result;
   JSONObject jsonObject;



   //CADASTROS
   public void CadastrarUser(final VolleyCallbackUsuario callback, final Usuario usuario) {
      requestQueue = Cadastro5.requestQueue;
      StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.CadastrarUsuarioUrl, new Response.Listener<String>() {
         @Override
         public void onResponse(String response) {
            Log.i("Script", "SUCCESS: "+response);
            callback.onSuccess(response, usuario);
         }
      }, new ErrorListener() {
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
   public void CadastrarServico(final VolleyCallback callback, final String nome, final String preco, final String descricao, final String tipo, final Usuario user, final String imagem) {
      requestQueue = CadastroServico.requestQueue;
      final String cod = String.valueOf(user.getCod());
      StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.CadastrarServicoUrl, new Response.Listener<String>() {
         @Override
         public void onResponse(String response) {
            Log.i("Script", "SUCCESS Servico: "+response + cod);
            callback.onSuccess(response);
         }
      }, new ErrorListener() {
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




   //LOGIN
   public void Login(final VolleyCallbackUsuario callback, final String email, final String senha) {
      requestQueue = MainActivity.requestQueue;
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
                       usuario.setTel(Integer.parseInt(jsonObject.getString("telefone_usuario")));
                       usuario.setCpf(jsonObject.getString("cpf_usuario"));
                       usuario.setIdade(Integer.parseInt(jsonObject.getString("idade_usuario")));
                       usuario.setImagem(jsonObject.getString("img").substring(jsonObject.getString("img").indexOf(",")+1));
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




   //LISTAR PRODUTOS
   public void idPrimeiro(final VolleyCallback callback, final String tipo) {
      requestQueue = PaginaUsuario.requestQueue;
      final StringRequest request = new StringRequest(
              Request.Method.POST,
              Constants.SelecionarPrimeiroServicoUrl,

              new Response.Listener<String>(){
                 @Override
                 public void onResponse(String response) {

                    Log.i("Script", "SUCCESS: "+response);

                    //passa o valor para o método callback
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
      request.setTag("tag");
      requestQueue.add(request);
   }
   public void idUltimo(final VolleyCallback callback, final String tipo) {
      requestQueue = PaginaUsuario.requestQueue;
      final StringRequest request = new StringRequest(
              Request.Method.POST,
              Constants.SelecionarUltimoServicoUrl,

              new Response.Listener<String>(){
                 @Override
                 public void onResponse(String response) {

                    Log.i("Script", "SUCCESS: "+response);

                    //passa o valor para o método callback
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
      request.setTag("tag");
      requestQueue.add(request);
   }
   public void Listar(final VolleyCallbackProduto callback, final String id, final String tipo) {
      requestQueue = PaginaUsuario.requestQueue;
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
      request.setTag("tag");
      requestQueue.add(request);
   }


   public void findServicoById(final VolleyCallbackProduto callback, final String id) {
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
      request.setTag("tag");
      requestQueue.add(request);

   }


   //CALLBACKS
   public interface VolleyCallback {
      void onSuccess(String response);
   }

   public interface VolleyCallbackProduto {
      void onSuccess(String response, Servico servico);
   }

   public interface VolleyCallbackUsuario {
      void onSuccess(String response, Usuario usuario);
   }


}
