package com.example.projetotcc.cadastroUsuario;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projetotcc.R;
import com.example.projetotcc.androidMask.MaskEditTextChangedListener;
import com.example.projetotcc.cep.CEP;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cadastro6 extends AppCompatActivity implements Button.OnClickListener {
    private Button btnBuscar;

    private EditText txtCEP;
    private EditText txtLogradouro;
    private EditText txtComplemento;
    private EditText txtBairro;
    private EditText txtEstado;

    private CEP vCEP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_6);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        // define
        //this.vCEP = null;

        // referência
        this.btnBuscar = (Button) findViewById(R.id.btnBuscar);
        this.txtCEP = (EditText) findViewById(R.id.txtCEP);
        this.txtLogradouro = (EditText) findViewById(R.id.txtLogradouro);
        this.txtComplemento = (EditText) findViewById(R.id.txtComplemento);
        this.txtBairro = (EditText) findViewById(R.id.txtBairro);
        this.txtEstado = (EditText) findViewById(R.id.txtEstado);

        // cria a máscara
        MaskEditTextChangedListener maskCEP = new MaskEditTextChangedListener("#####-###", this.txtCEP);

        // adiciona a máscara no objeto
        this.txtCEP.addTextChangedListener(maskCEP);

        // define o evento de clique
        this.btnBuscar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // evento para buscar um cep
        if (view == this.btnBuscar) {
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                // limpa
                this.txtBairro.setText("");
                this.txtComplemento.setText("");
                this.txtEstado.setText("");
                this.txtLogradouro.setText("");

                // cep
                String cep = this.txtCEP.getText().toString();

                // verifica se o CEP é válido
                Pattern pattern = Pattern.compile("^[0-9]{5}-[0-9]{3}$");
                Matcher matcher = pattern.matcher(cep);

                if (matcher.find()) {
                    new Cadastro6.DownloadCEPTask().execute(cep);
                } else {
                    //JOptionPane.showMessageDialog(null, "Favor informar um CEP válido!", "Aviso!", JOptionPane.WARNING_MESSAGE);
                    new AlertDialog.Builder(this)
                            .setTitle("Aviso!")
                            .setMessage("Favor informar um CEP válido!")
                            .setPositiveButton(R.string.msgOk, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // nada
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("Sem Internet!")
                        .setMessage("Não tem nenhuma conexão de rede disponível!")
                        .setPositiveButton(R.string.msgOk, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // nada
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        }
    }

    private class DownloadCEPTask extends AsyncTask<String, Void, CEP> {
        @Override
        protected CEP doInBackground(String ... ceps) {
            CEP vCep = null;

            try {
                vCep = new CEP(ceps[0]);
            } finally {
                return vCep;
            }
        }

        @Override
        protected void onPostExecute(CEP result) {
            if (result != null) {
                txtBairro.setText(result.getBairro());
                txtComplemento.setText(result.getComplemento());
                txtEstado.setText(result.getEstado());
                txtLogradouro.setText(result.getLogradouro());
            }
        }
    }
}
