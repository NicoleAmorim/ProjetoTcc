package com.example.projetotcc.cadastroUsuario;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projetotcc.R;
import com.example.projetotcc.androidMask.MaskEditTextChangedListener;
import com.example.projetotcc.controllers.ValidarCadastroUsuario;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cadastro3 extends AppCompatActivity {
    public static Context context;
    private ValidarCadastroUsuario validarCadastroUsuario;
    private EditText email, username, tel;
    private  String Email, User;
    private String Tell;
    private Matcher matcher;
    Intent it = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_3);

        context = this;
        validarCadastroUsuario = new ValidarCadastroUsuario();

        this.email = (EditText)this.findViewById(R.id.emailUserCadastro);
        this.username = (EditText)this.findViewById(R.id.nomeUsuarioUserCadastro);
        this.tel = (EditText)this.findViewById(R.id.telUserCadastro);

        MaskEditTextChangedListener maskTell = new MaskEditTextChangedListener("(##) #####-####", this.tel);

        this.tel.addTextChangedListener(maskTell);


}

    public void Cadastrar(View view) {
        Tell = tel.getText().toString();

        Pattern pattern = Pattern.compile("^\\([1-9]{2}\\)\\s9{0,1}[6-9]{1}[0-9]{3}\\-[0-9]{4}$");
        matcher = pattern.matcher(Tell);
        Email = email.getText().toString() + "";
        User = username.getText().toString() + "";
        if (matcher.find()) {
            validarCadastroUsuario.ValidarCadastro3(Email, User, Tell);
        } else {
            Toast.makeText(this, " Telefone invalido", Toast.LENGTH_SHORT).show();
        }

    }
}