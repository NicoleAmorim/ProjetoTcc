package com.example.projetotcc;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.example.projetotcc.controllers.ValidarCadastroUsuario;
import com.example.projetotcc.models.ValidarCadastroUsuarioModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import dominio.entidade.Usuario;

public class AlertDialog extends AppCompatActivity {
    public static RequestQueue requestQueue;
    public static Usuario usuario;
    public static Context context;
    private ValidarCadastroUsuario validarCadastroUsuario;
    protected ValidarCadastroUsuarioModel cadastroUsuarioModel;
    protected Intent it = null;

    private Button mShowDialogBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_6);

        mShowDialogBtn = findViewById(R.id.button7);

        mShowDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(AlertDialog.this);
                builder.setTitle("Estamos quase acabando!");
                builder.setMessage("Para finalizar o seu cadastro, precisamos dos seus dados de localização");
                builder.setMessage("Deseja realizá-lo agora?");
                builder.setIcon(R.drawable.ic_location);
                builder.setBackground(getResources().getDrawable(R.drawable.alert_dialog_bg));
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });

    }

}
