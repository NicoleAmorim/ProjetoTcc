package com.example.projetotcc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import database.DadosOpenHelperMessage;
import dominio.entidade.Message;
import dominio.repositorio.ManterLogadoRepositorio;
import com.example.projetotcc.controllers.Mensagem;
import com.example.projetotcc.models.CallBacks;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

import dominio.entidade.Usuario;

public class ChatUsuario extends AppCompatActivity {

    public static GroupAdapter adapter;
    private CallBacks callBacks;
    protected static Usuario remetente;

    private Usuario destinatario;
    private EditText editmessage;
    public static Context context;
    private DadosOpenHelperMessage dadosOpenHelper;
    private SQLiteDatabase conexao;
    private Mensagem mensagem;
    private ManterLogadoRepositorio manterLogadoRepositorio;
    public static RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestQueue = Volley.newRequestQueue(this.getApplicationContext());
        setContentView(R.layout.activity_chat_usuario);

        remetente = PaginaUsuario.usuario;
        destinatario = InfoServico.user;
        editmessage = findViewById(R.id.editMsgm);
        Log.i("Aqui", "Meu Serviço: "+ destinatario.getUsername() + remetente.getUsername());
        RecyclerView rv = findViewById(R.id.recyclerChatUser);
        callBacks = new CallBacks();
        mensagem = new Mensagem();
        context = this;
        adapter = new GroupAdapter();
        criarConexaoInterna();

        adapter = manterLogadoRepositorio.buscarUltimaMensagem(String.valueOf(remetente.getCod()), String.valueOf(destinatario.getCod()));
        adapter.notifyDataSetChanged();
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
        ProcurarMensagem();

    }

    public void sendMensagem(View view)
    {
        String txt = editmessage.getText().toString();

        editmessage.setText(null);

        String remetenteId = String.valueOf(remetente.getCod());
        String destinatarioId = String.valueOf(destinatario.getCod());
        long Time = System.currentTimeMillis();

        Message message = new Message();
        message.setDestinatarioID(destinatarioId);
        message.setRemetenteID(remetenteId);
        message.setTime(Time);
        message.setText(txt);

        mensagem.EnviarMensagem(message);

    }
    public void ProcurarMensagem() {
        mensagem.SelecionarMensagem();
    }

    public static class MessageItem extends Item<ViewHolder>{

        private final Message message;

        public MessageItem(Message message) {
            this.message = message;
        }


        @Override
        public void bind(@NonNull ViewHolder viewHolder, int position) {
          TextView txtMsgm = viewHolder.itemView.findViewById(R.id.txt_message);
          ImageView imgMsgm = viewHolder.itemView.findViewById(R.id.imgMessage);

          txtMsgm.setText(message.getText());
        }

        @Override
        public int getLayout() {
            return message.getRemetenteID().equals(String.valueOf(remetente.getCod()))
            ? R.layout.item_chat_message_right : R.layout.item_chat_message_left;
        }
    }
    private void criarConexaoInterna() {
        try {
            dadosOpenHelper = new DadosOpenHelperMessage(this);
            conexao = dadosOpenHelper.getWritableDatabase();
            manterLogadoRepositorio = new ManterLogadoRepositorio(conexao); } catch (Exception e) {
            e.printStackTrace();
        }
    }
}